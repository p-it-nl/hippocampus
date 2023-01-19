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
 * Tests for matches id and or version predicate
 *
 * @author Patrick-4488
 */
public class IsNotInListPredicateTest {

    @Test
    public void testWithoutEntry() {
        boolean expected = true;

        Hippo hippo = getHippoHaving(1, 1);

        boolean result = new IsNotInMapPredicate()
                .test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithEntryNotHavingIdAndNotHavingVersion() {
        boolean expected = true;
        Long id = null;
        Long version = null;

        Hippo hippo = getHippoHaving(1, 1);

        boolean result = new IsNotInMapPredicate()
                .addToMap(id, version)
                .test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithOneEntryHavingIdAndVersionMatchingId() {
        boolean expected = false;
        Long id = 1L;
        Long version = null;

        Hippo hippo = getHippoHaving(1, 1);

        boolean result = new IsNotInMapPredicate()
                .addToMap(id, version)
                .test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithOneEntryHavingIdAndVersionNotInList() {
        boolean expected = true;
        Long id = 2L;
        Long version = 1L;

        Hippo hippo = getHippoHaving(1, 1);

        boolean result = new IsNotInMapPredicate()
                .addToMap(id, version)
                .test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithMutlipleEntriesHavingOneMatching() {
        boolean expected = false;
        Long id = 1L;
        Long version = 1L;
        Long secondId = 2L;
        Long thirdId = 3L;

        Hippo hippo = getHippoHaving(1, 1);

        boolean result = new IsNotInMapPredicate()
                .addToMap(id, version)
                .addToMap(secondId, version)
                .addToMap(thirdId, version)
                .test(hippo);

        assertEquals(expected, result);
    }

    @Test
    public void testWithMutlipleEntriesHavingNoneMatching() {
        boolean expected = true;
        Long id = 1L;
        Long version = 1L;
        Long secondId = 2L;
        Long thirdId = 3L;

        Hippo hippo = getHippoHaving(4, 1);

        boolean result = new IsNotInMapPredicate()
                .addToMap(id, version)
                .addToMap(secondId, version)
                .addToMap(thirdId, version)
                .test(hippo);

        assertEquals(expected, result);
    }

    private Hippo getHippoHaving(final long id, final long version) {
        Hippo hippo = new Hippo();
        hippo.id = id;
        hippo.version = version;

        return hippo;
    }
}
