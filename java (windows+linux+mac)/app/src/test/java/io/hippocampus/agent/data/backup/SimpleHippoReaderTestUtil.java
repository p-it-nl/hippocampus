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

/**
 * Utility class for simple hippo reader tests
 *
 * @author Patrick-4488
 */
public class SimpleHippoReaderTestUtil {

    public static final String HIPPO_STRING_INVALID = "mock something that is not right";
    public static final String HIPPO_STRING_INVALID_WITH_SEPARATOR = "mock something; that is not right";
    public static final String HIPPO_STRING_INVALID_WITHOUT_ID = "mock; something; that is not; right";
    public static final String HIPPO_ID = "48";
    public static final String HIPPO_VERSION = "1";
    public static final String HIPPO_HIPPO = "mock";
    public static final String HIPPO_TAGS = "that is not, right";
    public static final String HIPPO_DATE = "1615491325043";
    public static final String HIPPO_DATE_INCORRECT = "mock";

    private static final String SEPARATOR = ";";

    private SimpleHippoReaderTestUtil() {
    }

    public static String getHippoString(final boolean withIncorrectDate) {
        StringBuilder sb = new StringBuilder();
        sb.append(HIPPO_ID);
        sb.append(SEPARATOR);
        sb.append(HIPPO_VERSION);
        sb.append(SEPARATOR);
        sb.append(HIPPO_HIPPO);
        sb.append(SEPARATOR);
        sb.append(HIPPO_TAGS);
        sb.append(SEPARATOR);
        sb.append((withIncorrectDate ? HIPPO_DATE_INCORRECT : HIPPO_DATE));

        return sb.toString();
    }
}
