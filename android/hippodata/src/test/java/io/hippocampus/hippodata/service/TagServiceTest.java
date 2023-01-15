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
package io.hippocampus.hippodata.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.hippocampus.hippodata.dao.TagDao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;

/**
 * Tests for tag service
 *
 * @author Patrick-4488
 */
@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagDao tagDao;

    private TagService classUnderTest;

    private static final String TEXT = "mock";
    private static final String TEXT_TWO = "mock";
    private static final String TEXT_THREE = "mock";

    @BeforeEach
    public void setup() {
        classUnderTest = new TagService(tagDao);
    }

    @Test
    public void getTagsByTextWithoutTextValue() {
        String text = null;

        classUnderTest.getTagsByText(text);

        verify(tagDao, atMostOnce()).findByTags(text);
    }

    @Test
    public void getTagsByTextWithEmptyTextValue() {
        String text = new String();

        classUnderTest.getTagsByText(text);

        verify(tagDao, atMostOnce()).findByTags(text);
    }

    @Test
    public void getTagsByTextWithTextValue() {
        String text = TEXT;

        classUnderTest.getTagsByText(text);

        verify(tagDao, atMostOnce()).findByTags(text);
    }

    @Test
    public void saveTagsWithoutTags() {
        List<String> tags = null;

        List<Long> result = classUnderTest.saveTags(tags);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void saveTagsWithEmptyTags() {
        List<String> tags = new ArrayList<>();

        List<Long> result = classUnderTest.saveTags(tags);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void saveTagsWithOneTag() {
        int expected = 1;
        List<String> tags = Arrays.asList(TEXT);

        List<Long> result = classUnderTest.saveTags(tags);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(expected, result.size());
    }

    @Test
    public void saveTagsWithMultipleTags() {
        int expected = 3;
        List<String> tags = Arrays.asList(TEXT, TEXT_TWO, TEXT_THREE);

        List<Long> result = classUnderTest.saveTags(tags);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(expected, result.size());
    }
}