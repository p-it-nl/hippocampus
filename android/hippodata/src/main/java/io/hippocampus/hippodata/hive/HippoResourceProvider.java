/**
 * Copyright (c) p-it
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.hippocampus.hippodata.hive;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import io.hippocampus.hippodata.HippoDatabase;
import io.hippocampus.hippodata.entity.Hippo;
import io.hippocampus.hippodata.predicate.IsNotInMapPredicate;
import io.hippocampus.hippodata.predicate.MatchesIdAndVersionPredicate;
import io.hippocampus.hippodata.service.HippoService;
import io.hivemind.synchronizer.HiveResource;
import io.hivemind.synchronizer.ResourceProvider;

/**
 * Provider for hippo essence data
 *
 * @author Patrick-4488
 * @see ResourceProvider
 */
public class HippoResourceProvider implements ResourceProvider {

    private final HippoService hippoService;
    private final Hivemind hivemind;

    public HippoResourceProvider(final Hivemind hivemind) {
        this.hivemind = hivemind;

        hippoService = new HippoService(HippoDatabase.getInstance().getDatabase());
    }

    @Override
    public List<HiveResource> provideAllResources() {
        List<Hippo> hippos = hippoService.getHippos(null);

        return List.copyOf(hippos);
    }

    @Override
    public List<HiveResource> provideResources(final List<HiveResource> resources) {
        MatchesIdAndVersionPredicate predicate = new MatchesIdAndVersionPredicate();
        for (HiveResource r : resources) {
            predicate.addToMap(Long.parseLong(r.getIdString()), Long.parseLong(r.getVersionString()));
        }
        List<Hippo> requestedHippos = new ArrayList<>();
        hippoService.getHippos(predicate);

        return List.copyOf(requestedHippos);
    }

    @Override
    public void deleteAllResourcesExcept(final List<HiveResource> resources) {
        IsNotInMapPredicate predicate = new IsNotInMapPredicate();
        for (HiveResource r : resources) {
            predicate.addToMap(Long.parseLong(r.getIdString()), Long.parseLong(r.getVersionString()));
        }

        List<Hippo> hipposToRemove = hippoService.getHippos(predicate);
        hippoService.deleteHippos((Hippo[]) hipposToRemove.toArray());

        hivemind.propagateUIUpdate();
    }

    @Override
    public void saveData(final byte[] bytes) {
        List<Hippo> receivedHippos = null;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes); ObjectInputStream ois = new ObjectInputStream(bais);) {
            receivedHippos = (List<Hippo>) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        if (receivedHippos != null && !receivedHippos.isEmpty()) {
            hippoService.saveHippos(receivedHippos);

            hivemind.propagateUIUpdate();
        } else {
            /**
             * Save data was called but either no data was provided or the byte
             * array conversion did not result in any objects
             */
        }
    }
}

