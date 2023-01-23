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

import io.hippocampus.agent.data.HippoData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for the hippo file helper
 *
 * @author Patrick-4488
 */
public class HippoFileHelperTest {

    private HippoFileHelper classUnderTest;

    @BeforeEach
    public void setUp() {
        classUnderTest = new HippoFileHelper(HippoFileHelperTestUtil.MOCKED_SAVE_LOCATION);
    }

    @AfterEach
    public void breakDown() {
        classUnderTest.delete();
    }

    @Test
    public void saveWithoutHippoData() {
        assertDoesNotThrow(() -> classUnderTest.save(null));
    }

    @Test
    public void saveWithEmtpyHippoData() {
        HippoData hippoData = new HippoData();

        assertDoesNotThrow(() -> classUnderTest.save(hippoData));
    }

    @Test
    public void saveWithSomeHippoData() {
        HippoData hippoData = HippoFileHelperTestUtil.getHippoDataWith(2);

        assertDoesNotThrow(() -> classUnderTest.save(hippoData));
    }

    @Test
    public void getHippoDataWhenNothingExists() {
        int expected = 0;
        HippoData hippoData = classUnderTest.getHippoData();

        assertNotNull(hippoData);
        assertEquals(expected, hippoData.getSize());
    }

    @Test
    public void saveAndThenGetHippoDataWithSomeHippoData() {
        HippoData hippoData = HippoFileHelperTestUtil.getHippoDataWith(2);

        assertDoesNotThrow(() -> classUnderTest.save(hippoData));
        HippoData readHippoData = classUnderTest.getHippoData();

        assertNotNull(readHippoData);
        assertEquals(hippoData.getSize(), readHippoData.getSize());
    }

    @Test
    public void saveThenDeleteAndThenGetHippoDataWithSomeHippoData() {
        int expected = 0;
        HippoData hippoData = HippoFileHelperTestUtil.getHippoDataWith(2);

        assertDoesNotThrow(() -> classUnderTest.save(hippoData));
        classUnderTest.delete();
        HippoData readHippoData = classUnderTest.getHippoData();

        assertNotNull(readHippoData);
        assertEquals(expected, readHippoData.getSize());
    }
}
