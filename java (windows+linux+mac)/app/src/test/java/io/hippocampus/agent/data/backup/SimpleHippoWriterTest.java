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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for simple hippo writer
 *
 * @author Patrick-4488
 */
public class SimpleHippoWriterTest {

    @Test
    public void writeWithoutHippo() {
        Hippo hippo = null;

        String result = SimpleHippoWriter.write(hippo);

        assertTrue(result.isEmpty());
    }

    @Test
    public void writeWithEmptyHippo() {
        Hippo hippo = new Hippo();
        String expectedId = SimpleHippoWriterTestUtil.ID_DEFAULT;

        String result = SimpleHippoWriter.write(hippo);

        assertFalse(result.isEmpty());
        assertTrue(result.contains(expectedId));
    }

    @Test
    public void writeWithHippoNotHavingId() {
        Hippo hippo = SimpleHippoWriterTestUtil.getHippoWith(false, true, true, true);
        String expectedId = SimpleHippoWriterTestUtil.ID_DEFAULT;
        String expectedHippo = SimpleHippoWriterTestUtil.HIPPO;
        String expectedTag = SimpleHippoWriterTestUtil.TAG_1;
        String expectedSecondTag = SimpleHippoWriterTestUtil.TAG_2;
        String expectedThirdTag = SimpleHippoWriterTestUtil.TAG_3;

        String result = SimpleHippoWriter.write(hippo);

        assertFalse(result.isEmpty());
        assertTrue(result.contains(expectedId));
        assertTrue(result.contains(expectedHippo));
        assertTrue(result.contains(expectedTag));
        assertTrue(result.contains(expectedSecondTag));
        assertTrue(result.contains(expectedThirdTag));
    }

    @Test
    public void writeWithHippoNotHavingHippo() {
        Hippo hippo = SimpleHippoWriterTestUtil.getHippoWith(true, false, true, true);
        String expectedId = String.valueOf(SimpleHippoWriterTestUtil.ID);
        String expectedHippo = SimpleHippoWriterTestUtil.HIPPO;
        String expectedTag = SimpleHippoWriterTestUtil.TAG_1;
        String expectedSecondTag = SimpleHippoWriterTestUtil.TAG_2;
        String expectedThirdTag = SimpleHippoWriterTestUtil.TAG_3;

        String result = SimpleHippoWriter.write(hippo);

        assertFalse(result.isEmpty());
        assertTrue(result.contains(expectedId));
        assertFalse(result.contains(expectedHippo));
        assertTrue(result.contains(expectedTag));
        assertTrue(result.contains(expectedSecondTag));
        assertTrue(result.contains(expectedThirdTag));
    }

    @Test
    public void writeWithHippoNotHavingCreationDate() {
        Hippo hippo = SimpleHippoWriterTestUtil.getHippoWith(true, true, false, true);
        int expectedLength = 4;

        String result = SimpleHippoWriter.write(hippo);
        int length = result.trim().split(SimpleHippoWriterTestUtil.SEPARATOR).length;

        assertFalse(result.isEmpty());
        assertEquals(expectedLength, length);
    }

    @Test
    public void writeWithHippoNotHavingTags() {
        Hippo hippo = SimpleHippoWriterTestUtil.getHippoWith(true, true, true, false);
        String expectedId = String.valueOf(SimpleHippoWriterTestUtil.ID);
        String expectedHippo = SimpleHippoWriterTestUtil.HIPPO;
        String expectedTag = SimpleHippoWriterTestUtil.TAG_1;
        String expectedSecondTag = SimpleHippoWriterTestUtil.TAG_2;
        String expectedThirdTag = SimpleHippoWriterTestUtil.TAG_3;

        String result = SimpleHippoWriter.write(hippo);

        assertFalse(result.isEmpty());
        assertTrue(result.contains(expectedId));
        assertTrue(result.contains(expectedHippo));
        assertFalse(result.contains(expectedTag));
        assertFalse(result.contains(expectedSecondTag));
        assertFalse(result.contains(expectedThirdTag));
    }

    @Test
    public void writeWithHippoNotHavingAllValues() {
        Hippo hippo = SimpleHippoWriterTestUtil.getHippoWith(true, true, true, true);
        String expectedId = String.valueOf(SimpleHippoWriterTestUtil.ID);
        String expectedHippo = SimpleHippoWriterTestUtil.HIPPO;
        String expectedTag = SimpleHippoWriterTestUtil.TAG_1;
        String expectedSecondTag = SimpleHippoWriterTestUtil.TAG_2;
        String expectedThirdTag = SimpleHippoWriterTestUtil.TAG_3;
        int expectedLength = 5;

        String result = SimpleHippoWriter.write(hippo);
        int length = result.trim().split(SimpleHippoWriterTestUtil.SEPARATOR).length;

        assertFalse(result.isEmpty());
        assertTrue(result.contains(expectedId));
        assertTrue(result.contains(expectedHippo));
        assertTrue(result.contains(expectedTag));
        assertTrue(result.contains(expectedSecondTag));
        assertTrue(result.contains(expectedThirdTag));
        assertEquals(expectedLength, length);
    }
}
