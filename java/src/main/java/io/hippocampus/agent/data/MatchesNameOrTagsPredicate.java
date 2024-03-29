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
import java.util.function.Predicate;

/**
 * Predicate for finding hippos matching input on tags and names<br>
 * - This reads more easy then lambdas since this being slightly more
 * complicated then where id == something<br>
 * - Can be used on any subset of hippos, do not require to keep going to the
 * service
 *
 * @author Patrick-4488
 */
public class MatchesNameOrTagsPredicate implements Predicate<Hippo> {

    private String[] needles;

    private static final String SPLIT = " ";

    /**
     * What to search for<br>
     * Split on spaces to enable e.g. birthday dad and recipe with carrot
     *
     * @param needle string to finds
     * @return this fluent
     */
    public MatchesNameOrTagsPredicate forNeedle(final String needle) {
        this.needles = (needle != null ? needle.split(SPLIT) : new String[0]);

        return this;
    }

    /**
     * The test
     *
     * @param hippo the hippo to test
     * @return whether matches given criteria
     */
    @Override
    public boolean test(final Hippo hippo) {
        boolean match = false;
        for (String needle : needles) {
            if (canMatch(needle, hippo)) {
                match = matchesHippo(needle, hippo) || matchesAnyTag(needle, hippo);
            }
        }

        return match;
    }

    private boolean matchesHippo(final String needle, final Hippo hippo) {
        if (hippo.getHippo() != null) {
            String haystack = hippo.getHippo().toLowerCase().trim();
            return haystack.contains(needle);
        } else {
            return false;
        }
    }

    private boolean matchesAnyTag(final String needle, final Hippo hippo) {
        if (hippo.getTags() != null) {
            return hippo.getTags()
                    .stream()
                    .anyMatch(t -> t.toLowerCase().trim().contains(needle));
        } else {
            return false;
        }
    }

    private boolean canMatch(final String needle, final Hippo hippo) {
        return needle != null && !needle.isEmpty() && hippo != null && (hippo.getHippo() != null || hippo.getTags() != null);
    }
}
