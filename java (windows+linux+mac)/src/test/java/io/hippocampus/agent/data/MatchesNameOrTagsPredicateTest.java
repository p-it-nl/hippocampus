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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for matches name or tags predicate
 *
 * @author Patrick-4488
 */
public class MatchesNameOrTagsPredicateTest {

    @Test
    public void testWithoutNeedle() {
        boolean expected = false;
        String needle = null;
        Hippo hippo = MatchesNameOrTagsPredicateTestUtil.getHippoHaving(
                MatchesNameOrTagsPredicateTestUtil.NAME_MATCHING,
                MatchesNameOrTagsPredicateTestUtil.TAG_MATCHING
        );

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithEmtpyNeedle() {
        boolean expected = false;
        String needle = "";
        Hippo hippo = MatchesNameOrTagsPredicateTestUtil.getHippoHaving(
                MatchesNameOrTagsPredicateTestUtil.NAME_MATCHING,
                MatchesNameOrTagsPredicateTestUtil.TAG_MATCHING
        );

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithBlankNeedle() {
        boolean expected = false;
        String needle = " ";
        Hippo hippo = MatchesNameOrTagsPredicateTestUtil.getHippoHaving(
                MatchesNameOrTagsPredicateTestUtil.NAME_MATCHING,
                MatchesNameOrTagsPredicateTestUtil.TAG_MATCHING
        );

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleMatchingName() {
        boolean expected = true;
        String needle = MatchesNameOrTagsPredicateTestUtil.NEEDLE_MATCHING;
        Hippo hippo = MatchesNameOrTagsPredicateTestUtil.getHippoHaving(
                MatchesNameOrTagsPredicateTestUtil.NAME_MATCHING,
                MatchesNameOrTagsPredicateTestUtil.TAG_NOT_MATCHING
        );

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleNotMatchingNameNorTag() {
        boolean expected = false;
        String needle = MatchesNameOrTagsPredicateTestUtil.NEEDLE_MATCHING;
        Hippo hippo = MatchesNameOrTagsPredicateTestUtil.getHippoHaving(
                MatchesNameOrTagsPredicateTestUtil.NAME_NOT_MATCHING,
                MatchesNameOrTagsPredicateTestUtil.TAG_NOT_MATCHING
        );

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleMatchingTag() {
        boolean expected = true;
        String needle = MatchesNameOrTagsPredicateTestUtil.NEEDLE_MATCHING;
        Hippo hippo = MatchesNameOrTagsPredicateTestUtil.getHippoHaving(
                MatchesNameOrTagsPredicateTestUtil.NAME_NOT_MATCHING,
                MatchesNameOrTagsPredicateTestUtil.TAG_MATCHING
        );

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleMatchingOneOfMultipleTags() {
        boolean expected = true;
        String needle = MatchesNameOrTagsPredicateTestUtil.NEEDLE_MATCHING;
        Hippo hippo = MatchesNameOrTagsPredicateTestUtil.getHippoHaving(
                MatchesNameOrTagsPredicateTestUtil.NAME_NOT_MATCHING,
                MatchesNameOrTagsPredicateTestUtil.TAGS_MATCHING
        );

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleMatchingNoneOfMultipleTags() {
        boolean expected = false;
        String needle = MatchesNameOrTagsPredicateTestUtil.NEEDLE_MATCHING;
        Hippo hippo = MatchesNameOrTagsPredicateTestUtil.getHippoHaving(
                MatchesNameOrTagsPredicateTestUtil.NAME_NOT_MATCHING,
                MatchesNameOrTagsPredicateTestUtil.TAGS_NOT_MATCHING
        );

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleMatchingNameAndTag() {
        boolean expected = true;
        String needle = MatchesNameOrTagsPredicateTestUtil.NEEDLE_MATCHING;
        Hippo hippo = MatchesNameOrTagsPredicateTestUtil.getHippoHaving(
                MatchesNameOrTagsPredicateTestUtil.NAME_MATCHING,
                MatchesNameOrTagsPredicateTestUtil.TAG_MATCHING
        );

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleMatchingNameAndTags() {
        boolean expected = true;
        String needle = MatchesNameOrTagsPredicateTestUtil.NEEDLE_MATCHING;
        Hippo hippo = MatchesNameOrTagsPredicateTestUtil.getHippoHaving(
                MatchesNameOrTagsPredicateTestUtil.NAME_MATCHING,
                MatchesNameOrTagsPredicateTestUtil.TAGS_MATCHING
        );

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleButNoHippo() {
        boolean expected = false;
        String needle = MatchesNameOrTagsPredicateTestUtil.NEEDLE_MATCHING;
        Hippo hippo = null;

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleAndHippoNotHavingName() {
        boolean expected = true;
        String needle = MatchesNameOrTagsPredicateTestUtil.NEEDLE_MATCHING;
        Hippo hippo = MatchesNameOrTagsPredicateTestUtil.getHippoHaving(
                null,
                MatchesNameOrTagsPredicateTestUtil.TAGS_MATCHING
        );

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleAndHippoNotHavingTags() {
        boolean expected = true;
        String needle = MatchesNameOrTagsPredicateTestUtil.NEEDLE_MATCHING;
        Hippo hippo = MatchesNameOrTagsPredicateTestUtil.getHippoHaving(
                MatchesNameOrTagsPredicateTestUtil.NAME_MATCHING,
                null
        );

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleAndHippoNotHavingNameNorTags() {
        boolean expected = false;
        String needle = MatchesNameOrTagsPredicateTestUtil.NEEDLE_MATCHING;
        Hippo hippo = MatchesNameOrTagsPredicateTestUtil.getHippoHaving(
                null,
                null
        );

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithNeedleAndHippoHavingNameAndTagsButNoMatch() {
        boolean expected = false;
        String needle = MatchesNameOrTagsPredicateTestUtil.NEEDLE_MATCHING;
        Hippo hippo = MatchesNameOrTagsPredicateTestUtil.getHippoHaving(
                MatchesNameOrTagsPredicateTestUtil.NAME_NOT_MATCHING,
                MatchesNameOrTagsPredicateTestUtil.TAGS_NOT_MATCHING
        );

        boolean result = new MatchesNameOrTagsPredicate().forNeedle(needle).test(hippo);

        assertEquals(expected, result);
    }

}
