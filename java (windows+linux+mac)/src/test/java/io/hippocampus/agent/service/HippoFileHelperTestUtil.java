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
package io.hippocampus.agent.service;

import io.hippocampus.agent.data.HippoData;
import io.hippocampus.agent.model.Hippo;
import io.hippocampus.agent.util.ApplicationData;
import java.util.List;

/**
 * Utility class for hippo file helper tests
 *
 * @author Patrick-4488
 */
public class HippoFileHelperTestUtil {

    public static final String HIPPO = "MOCK";
    public static final String TAG = "MOCK";
    public static final String MOCKED_SAVE_LOCATION
            = ApplicationData.getAppDataLocation() + "/mock";

    private HippoFileHelperTestUtil() {
    }

    public static HippoData getHippoDataWith(final int amountOfHippos) {
        HippoData data = new HippoData();
        for (int i = 0; i < amountOfHippos; i++) {
            data.addHippo(getHippo(i));
        }

        return data;
    }

    private static Hippo getHippo(final int i) {
        Hippo hippo = new Hippo();
        hippo.setId(i);
        hippo.setHippo(HIPPO);
        hippo.setTags(List.of(TAG));

        return hippo;
    }
}
