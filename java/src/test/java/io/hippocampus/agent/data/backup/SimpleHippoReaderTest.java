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

import io.hippocampus.agent.exception.HippoCeption;
import io.hippocampus.agent.exception.HippoException;
import io.hippocampus.agent.exception.HippoFileReadException;
import io.hippocampus.agent.model.Hippo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for simple hippo reader
 *
 * @author Patrick-4488
 */
public class SimpleHippoReaderTest {

    @Test
    public void readWithoutLine() {
        String line = null;

        Hippo result = SimpleHippoReader.read(line);

        assertNull(result);
    }

    @Test
    public void readWithEmptyLine() {
        String line = "";

        Hippo result = SimpleHippoReader.read(line);

        assertNull(result);
    }

    @Test
    public void readWithIncorrectLineWithoutSeparator() {
        String line = SimpleHippoReaderTestUtil.HIPPO_STRING_INVALID;

        Hippo result = SimpleHippoReader.read(line);

        assertNull(result);
    }

    @Test
    public void readWithIncorrectLineWithSeparator() {
        HippoCeption expected = HippoCeption.NOT_A_HIPPOS_FILE;
        String line = SimpleHippoReaderTestUtil.HIPPO_STRING_INVALID_WITH_SEPARATOR;

        HippoException exception = assertThrows(HippoFileReadException.class,
                () -> SimpleHippoReader.read(line));

        assertEquals(expected.getMessage(), exception.getMessage());
    }

    @Test
    public void readWithIncorrectLineHavingNoId() {
        HippoCeption expected = HippoCeption.NOT_A_HIPPOS_FILE;
        String line = SimpleHippoReaderTestUtil.HIPPO_STRING_INVALID_WITH_SEPARATOR;

        HippoException exception = assertThrows(HippoFileReadException.class,
                () -> SimpleHippoReader.read(line));

        assertEquals(expected.getMessage(), exception.getMessage());
    }

    @Test
    public void readWithIncorrectDate() {
        HippoCeption expected = HippoCeption.NOT_A_HIPPOS_FILE;
        String line = SimpleHippoReaderTestUtil.getHippoString(true);

        HippoException exception = assertThrows(HippoFileReadException.class,
                () -> SimpleHippoReader.read(line));

        assertEquals(expected.getMessage(), exception.getMessage());
    }

    @Test
    public void readWithCorrectLine() {
        int expectedAmountOfTags = 2;
        String line = SimpleHippoReaderTestUtil.getHippoString(false);

        Hippo result = SimpleHippoReader.read(line);

        assertNotNull(result);
        assertEquals(SimpleHippoReaderTestUtil.HIPPO_ID, String.valueOf(result.getId()));
        assertEquals(SimpleHippoReaderTestUtil.HIPPO_VERSION, String.valueOf(result.getVersion()));
        assertEquals(SimpleHippoReaderTestUtil.HIPPO_HIPPO, result.getHippo());
        assertNotNull(result.getCreationDate());
        assertEquals(expectedAmountOfTags, result.getTags().size());
    }
}
