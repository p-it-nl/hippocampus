/**
 * Copyright (c) p-it
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.hippocampus.agent.hive;

import io.hippocampus.agent.data.MatchesIdAndVersionPredicate;
import io.hippocampus.agent.data.IsNotInMapPredicate;
import io.hippocampus.agent.model.Hippo;
import io.hippocampus.agent.service.HippoService;
import io.hivemind.synchronizer.HiveResource;
import io.hivemind.synchronizer.ResourceProvider;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.WARNING;

/**
 * Provider for hippo essence data
 *
 * @see ResourceProvider
 * @author Patrick-4488
 */
public class HippoResourceProvider implements ResourceProvider {

    private final HippoService hippoService;
    private final HippoService.Synchronizer synchronizer;

    private static final System.Logger LOGGER = System.getLogger(HippoResourceProvider.class.getName());

    public HippoResourceProvider(final HippoService hippoService) {
        this.hippoService = hippoService;
        this.synchronizer = hippoService.new Synchronizer();
    }

    @Override
    public List<HiveResource> provideAllResources() {
        List<Hippo> hippos = hippoService.getHippos();

        return List.copyOf(hippos);
    }

    @Override
    public List<HiveResource> provideResources(final List<HiveResource> resources) {
        MatchesIdAndVersionPredicate predicate = new MatchesIdAndVersionPredicate();
        for (HiveResource r : resources) {
            predicate.addToMap(Long.parseLong(r.getIdString()), Long.parseLong(r.getVersionString()));
        }
        List<Hippo> requestedHippos = synchronizer.getHippos(predicate);
        return List.copyOf(requestedHippos);
    }

    @Override
    public void deleteAllResourcesExcept(final List<HiveResource> resources) {
        IsNotInMapPredicate predicate = new IsNotInMapPredicate();
        for (HiveResource r : resources) {
            predicate.addToMap(Long.parseLong(r.getIdString()), Long.parseLong(r.getVersionString()));
        }

        List<Hippo> hipposToRemove = synchronizer.getHippos(predicate);
        synchronizer.deleteHippos(hipposToRemove);
    }

    @Override
    public void saveData(final byte[] bytes) {
        List<Hippo> receivedHippos = null;
        try ( ByteArrayInputStream bais = new ByteArrayInputStream(bytes);  ObjectInputStream ois = new ObjectInputStream(bais);) {
            receivedHippos = (List<Hippo>) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            LOGGER.log(ERROR, "Failed to convert byte array to object", ex);
        }

        if (receivedHippos != null && !receivedHippos.isEmpty()) {
            synchronizer.addHippos(receivedHippos);
        } else {
            LOGGER.log(WARNING, """
                Save data was called but either no data was provided or the byte 
                array conversion did not result in any objects""");
        }
    }
}
