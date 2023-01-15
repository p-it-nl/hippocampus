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
package io.hippocampus.hippodata.converter;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for date converter
 *
 * @author Patrick-4488
 */
public class DateConverterTest {

    // Sun Jan 15 14:51:16 CET 2023
    private static final long LONG_VALID_DATE = 1673790676103L;

    @Test
    public void toDateWithoutLong() {
        Long input = null;

        Date result = DateConverter.toDate(input);

        assertNull(result);
    }

    @Test
    public void toDateWithMinValue() {
        Long input = 1L;

        Date result = DateConverter.toDate(input);

        assertNotNull(result);
        assertTrue(Date.from(Instant.EPOCH).compareTo(result) == -1);
    }

    @Test
    public void toDateWithDateValue() {
        Long input = 1673790676103L;

        Date result = DateConverter.toDate(input);

        assertNotNull(result);
        assertEquals(LONG_VALID_DATE, result.getTime());
    }

    @Test
    public void toLongWithoutDate() {
        Date input = null;

        Long result = DateConverter.toLong(input);

        assertNull(result);
    }

    @Test
    public void toLongWithDate() {
        Date input = new Date(LONG_VALID_DATE);

        Long result = DateConverter.toLong(input);

        assertNotNull(result);
        assertEquals(LONG_VALID_DATE, result);
        ;
    }
}
