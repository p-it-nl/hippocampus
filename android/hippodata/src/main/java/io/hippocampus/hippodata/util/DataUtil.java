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
package io.hippocampus.hippodata.util;

/**
 * Utility for changing queries based on input
 *
 * @author Patrick-4488
 */
public class DataUtil {

    private static final String wildcard = "%";

    private DataUtil() {
    }

    /**
     * Generate like query for needle
     *
     * @param needle the needle
     * @return the like query
     */
    public static String needleToLikeQuery(final String needle) {
        return wildcard + needle + wildcard;
    }
}
