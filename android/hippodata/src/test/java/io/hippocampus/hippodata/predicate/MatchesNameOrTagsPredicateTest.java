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
package io.hippocampus.hippodata.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.hippocampus.hippodata.entity.Hippo;

/**
 * Tests for matches name or tags predicate
 *
 * @author Patrick-4488
 */
public class MatchesNameOrTagsPredicateTest {

    private static final String NEEDLE_MATCHING = "mock";
    private static final String NAME_MATCHING = "mock";
    private static final String NAME_NOT_MATCHING = "not";
    private static final String TAG_MATCHING = "mock";
    private static final String TAGS_MATCHING = "not a mock";
    private static final String TAG_NOT_MATCHING = "not";
    private static final String TAGS_NOT_MATCHING = "does not match";

    @Test
    public void testWithoutNeedle() {
        boolean expected = false;
        String needle = null;
        Hippo hippo = getHippoHaving(NAME_MATCHING, TAG_MATCHING);

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithEmtpyNeedle() {
        boolean expected = false;
        String needle = "";
        Hippo hippo = getHippoHaving(NAME_MATCHING, TAG_MATCHING);

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithBlankNeedle() {
        boolean expected = false;
        String needle = " ";
        Hippo hippo = getHippoHaving(NAME_MATCHING, TAG_MATCHING);

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleMatchingName() {
        boolean expected = true;
        String needle = NEEDLE_MATCHING;
        Hippo hippo = getHippoHaving(NAME_MATCHING, TAG_NOT_MATCHING);

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleNotMatchingNameNorTag() {
        boolean expected = false;
        String needle = NEEDLE_MATCHING;
        Hippo hippo = getHippoHaving(NAME_NOT_MATCHING, TAG_NOT_MATCHING);

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleMatchingTag() {
        boolean expected = true;
        String needle = NEEDLE_MATCHING;
        Hippo hippo = getHippoHaving(NAME_NOT_MATCHING, TAG_MATCHING);

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleMatchingOneOfMultipleTags() {
        boolean expected = true;
        String needle = NEEDLE_MATCHING;
        Hippo hippo = getHippoHaving(NAME_NOT_MATCHING, TAGS_MATCHING);

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleMatchingNoneOfMultipleTags() {
        boolean expected = false;
        String needle = NEEDLE_MATCHING;
        Hippo hippo = getHippoHaving(NAME_NOT_MATCHING, TAGS_NOT_MATCHING);

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleMatchingNameAndTag() {
        boolean expected = true;
        String needle = NEEDLE_MATCHING;
        Hippo hippo = getHippoHaving(NAME_MATCHING, TAG_MATCHING);

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleMatchingNameAndTags() {
        boolean expected = true;
        String needle = NEEDLE_MATCHING;
        Hippo hippo = getHippoHaving(NAME_MATCHING, TAGS_MATCHING);

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleButNoHippo() {
        boolean expected = false;
        String needle = NEEDLE_MATCHING;
        Hippo hippo = null;

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleAndHippoNotHavingName() {
        boolean expected = true;
        String needle = NEEDLE_MATCHING;
        Hippo hippo = getHippoHaving(null, TAGS_MATCHING);

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleAndHippoNotHavingTags() {
        boolean expected = true;
        String needle = NEEDLE_MATCHING;
        Hippo hippo = getHippoHaving(NAME_MATCHING, null);

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleAndHippoNotHavingNameNorTags() {
        boolean expected = false;
        String needle = NEEDLE_MATCHING;
        Hippo hippo = getHippoHaving(null, null);

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleAndHippoHavingNameAndTagsButNoMatch() {
        boolean expected = false;
        String needle = NEEDLE_MATCHING;
        Hippo hippo = getHippoHaving(NAME_NOT_MATCHING, TAGS_NOT_MATCHING);

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    private Hippo getHippoHaving(final String name, final String tags) {
        Hippo hippo = new Hippo();
        hippo.hippo = name;
        hippo.tags = tags;

        return hippo;
    }
}
