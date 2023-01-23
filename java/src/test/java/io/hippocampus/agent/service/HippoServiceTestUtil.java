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

import io.hippocampus.agent.model.Hippo;
import java.util.ArrayList;
import java.util.List;

/**
 * Test utility class for the hippo service
 *
 * @author Patrick-4488
 */
public class HippoServiceTestUtil {

    public static final String MOCKED_HIPPO = "mock";
    public static final String MOCKED_HIPPO_UPDATED = "mock updated";
    public static final String MOCKED_WEIRD_HIPPO = "�笴������ ????a ??";
    public static final String MOCKED_TAG = "mock";
    public static final String MOCKED_MULTIPLE_TAGS = "mock1 mock2";
    public static final String MOCKED_MANY_TAGS = "mock1 mock2 mock3 mock4 mock5 mock6";
    public static final String MOCKED_MULTIPLE_WEIRD_TAGS = "??????????? ???????";
    public static final String MOCKED_BACKUP_FILE = "yea-this-does-not-exist.hippos";

    private HippoServiceTestUtil() {
    }

    public static Hippo getHippo(
            final boolean withHippo,
            final int withThisAmountOfTags) {
        Hippo hippo = new Hippo();
        hippo.setHippo((withHippo ? MOCKED_HIPPO : null));

        if (0 != withThisAmountOfTags) {
            List<String> tags = new ArrayList<>();
            for (int i = 0; i < withThisAmountOfTags; i++) {
                tags.add(MOCKED_TAG);
            }
            hippo.setTags(tags);
        } else {
            // Yea not needing tags...
        }

        return hippo;
    }
}
