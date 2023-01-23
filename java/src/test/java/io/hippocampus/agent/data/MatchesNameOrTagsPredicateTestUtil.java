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
package io.hippocampus.agent.data;

import io.hippocampus.agent.model.Hippo;
import java.util.List;

/**
 * Utility class for MatchesNameOrTagsPredicate tests
 *
 * @author Patrick-4488
 */
public class MatchesNameOrTagsPredicateTestUtil {
    
    public static final String NEEDLE_MATCHING = "mock";
    public static final String NAME_MATCHING = "mock";
    public static final String NAME_NOT_MATCHING = "not";
    public static final String TAG_MATCHING = "mock";
    public static final String TAGS_MATCHING = "not a mock";
    public static final String TAG_NOT_MATCHING = "not";
    public static final String TAGS_NOT_MATCHING = "does not match";

    private MatchesNameOrTagsPredicateTestUtil() {
    }

    public static Hippo getHippoHaving(final String name, final String tags) {
        Hippo hippo = new Hippo();
        hippo.setHippo(name);
        hippo.setTags((tags != null ? List.of(tags.split(" ")) : null));

        return hippo;
    }
}
