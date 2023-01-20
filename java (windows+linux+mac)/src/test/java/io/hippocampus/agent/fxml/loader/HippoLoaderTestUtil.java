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
package io.hippocampus.agent.fxml.loader;

import io.hippocampus.agent.model.Hippo;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for hippo loader tests
 *
 * @author Patrick-4488
 */
public class HippoLoaderTestUtil {

    private static final String MOCKED_TAG = "mock";
    private static final String MOCKED_HIPPO = "mock";

    private HippoLoaderTestUtil() {
    }

    public static List<Hippo> getHippos(
            final int amountOfHippos) {
        List<Hippo> hippos = new ArrayList<>();
        for (int i = 0; i < amountOfHippos; i++) {
            Hippo hippo = new Hippo();
            hippo.setId(i);
            hippo.setHippo(MOCKED_HIPPO);
            hippo.setTags(List.of(MOCKED_TAG));

            hippos.add(hippo);
        }

        return hippos;
    }
}
