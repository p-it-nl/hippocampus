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
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for hippo data
 *
 * @author Patrick-4488
 */
public class HippoDataTest {

    private HippoData classUnderTest;

    @BeforeEach
    public void setUp() {
        classUnderTest = new HippoData();
    }

    @Test
    public void newHippoDataWithoutObjectStream() {
        assertDoesNotThrow(() -> new HippoData(null));
    }

    @Test
    public void addHippoWithoutHippo() {
        Hippo hippo = null;

        List<Hippo> result = classUnderTest.addHippo(hippo);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void addHippoWithEmptyHippo() {
        int expected = 1;
        Hippo hippo = new Hippo();

        List<Hippo> result = classUnderTest.addHippo(hippo);

        assertNotNull(result);
        assertEquals(expected, result.size());
    }

    @Test
    public void addHippoWithHippoHavingAllValues() {
        int expected = 1;
        long id = 1l;
        Hippo hippo = HippoDataTestUtil.getHippo(id, HippoDataTestUtil.HIPPO_DEFAULT, 1);

        List<Hippo> result = classUnderTest.addHippo(hippo);

        assertNotNull(result);
        assertEquals(expected, result.size());
        assertEquals(id, result.get(0).getId());
    }

    @Test
    public void updateHippoButWithoutHippo() {
        Hippo hippo = null;

        List<Hippo> result = classUnderTest.updateHippo(hippo);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void updateHippoButWithEmptyHippo() {
        Hippo hippo = new Hippo();

        List<Hippo> result = classUnderTest.updateHippo(hippo);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void updateHippoButHippoDoesNotExist() {
        long id = 1l;
        Hippo hippo = HippoDataTestUtil.getHippo(id, HippoDataTestUtil.HIPPO_DEFAULT, 1);

        List<Hippo> result = classUnderTest.updateHippo(hippo);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void updateExistingHippo() {
        int expected = 1;
        long id = 1l;
        Hippo hippo = HippoDataTestUtil.getHippo(id, HippoDataTestUtil.HIPPO_DEFAULT, 1);
        Hippo hippoUpdate = HippoDataTestUtil.getHippo(id, HippoDataTestUtil.HIPPO_NOT_DEFAULT, 1);

        classUnderTest.addHippo(hippo);
        List<Hippo> result = classUnderTest.updateHippo(hippoUpdate);

        assertNotNull(result);
        assertEquals(expected, result.size());
        assertEquals(HippoDataTestUtil.HIPPO_NOT_DEFAULT, result.get(0).getHippo());
        assertEquals(hippo.getHippo(), hippoUpdate.getHippo());
    }

    @Test
    public void deleteHippoWithoutHippo() {
        Hippo hippo = null;

        List<Hippo> result = classUnderTest.deleteHippo(hippo);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void deleteHippoWithEmptyHippo() {
        Hippo hippo = new Hippo();

        List<Hippo> result = classUnderTest.deleteHippo(hippo);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void deleteHippoWithHippoThatDoesNotExist() {
        long id = 1l;
        Hippo hippo = HippoDataTestUtil.getHippo(id, HippoDataTestUtil.HIPPO_DEFAULT, 1);

        List<Hippo> result = classUnderTest.deleteHippo(hippo);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void deleteHippoThatExists() {
        long id = 1l;
        Hippo hippo = HippoDataTestUtil.getHippo(id, HippoDataTestUtil.HIPPO_DEFAULT, 1);
        Hippo hippoToDelete = hippo;

        classUnderTest.addHippo(hippo);
        List<Hippo> result = classUnderTest.deleteHippo(hippoToDelete);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getHipposWithoutPredicateAndComparator() {
        Predicate<Hippo> predicate = null;
        Comparator<Hippo> comparator = null;

        List<Hippo> result = classUnderTest.getHippos(predicate, comparator);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getHipposWithoutPredicateAndDateComparatorButNoHippos() {
        Predicate<Hippo> predicate = null;
        Comparator<Hippo> comparator = new SortByCreationDate();

        List<Hippo> result = classUnderTest.getHippos(predicate, comparator);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getHipposWithoutPredicateAndDateComparatorHavingHippos() {
        long id = 1l;
        long secondId = 2l;
        Predicate<Hippo> predicate = null;
        Comparator<Hippo> comparator = new SortByCreationDate();
        Hippo hippo = HippoDataTestUtil.getHippo(id, HippoDataTestUtil.HIPPO_DEFAULT, 2);
        Hippo hippoOlder = HippoDataTestUtil.getHippo(secondId, HippoDataTestUtil.HIPPO_NOT_DEFAULT, 1);
        classUnderTest.addHippo(hippo);
        classUnderTest.addHippo(hippoOlder);

        List<Hippo> result = classUnderTest.getHippos(predicate, comparator);

        assertNotNull(result);
        assertEquals(HippoDataTestUtil.HIPPO_DEFAULT, result.get(0).getHippo());
        assertEquals(HippoDataTestUtil.HIPPO_NOT_DEFAULT, result.get(1).getHippo());
    }

    @Test
    public void getHipposWithoutPredicateAndWithoutDateComparatorHavingHippos() {
        long id = 1l;
        long secondId = 2l;
        Predicate<Hippo> predicate = null;
        Comparator<Hippo> comparator = null;
        Hippo hippo = HippoDataTestUtil.getHippo(id, HippoDataTestUtil.HIPPO_DEFAULT, 2);
        Hippo hippoOlder = HippoDataTestUtil.getHippo(secondId, HippoDataTestUtil.HIPPO_NOT_DEFAULT, 1);
        classUnderTest.addHippo(hippo);
        classUnderTest.addHippo(hippoOlder);

        List<Hippo> result = classUnderTest.getHippos(predicate, comparator);

        assertNotNull(result);
        assertEquals(HippoDataTestUtil.HIPPO_DEFAULT, result.get(0).getHippo());
        assertEquals(HippoDataTestUtil.HIPPO_NOT_DEFAULT, result.get(1).getHippo());
    }
}
