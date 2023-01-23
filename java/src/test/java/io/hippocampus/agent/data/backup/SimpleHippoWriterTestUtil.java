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
package io.hippocampus.agent.data.backup;

import io.hippocampus.agent.model.Hippo;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Utility class for simple hippo writer tests
 *
 * @author Patrick-4488
 */
public class SimpleHippoWriterTestUtil {

    public static final String ID_DEFAULT = "0";
    public static final long ID = 48;
    public static final String HIPPO = "do not mock the hippo, it might get angry";
    public static final String TAG_1 = "BEWARE";
    public static final String TAG_2 = "OFF";
    public static final String TAG_3 = "HIPPO";
    public static final String SEPARATOR = ";";

    private static final List<String> TAGS = List.of(TAG_1, TAG_2, TAG_3);

    private SimpleHippoWriterTestUtil() {
    }

    public static Hippo getHippoWith(
            final boolean withId,
            final boolean withHippo,
            final boolean withCreationDate,
            final boolean withTags) {
        return new Hippo(
                (withId ? ID : 0),
                1,
                (withHippo ? HIPPO : null),
                (withCreationDate ? LocalDateTime.now() : null),
                (withTags ? TAGS : null));
    }
}
