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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.hippocampus.hippodata.dao.HippoTagDao;
import io.hippocampus.hippodata.model.Hippo;
import io.hippocampus.hippodata.model.HippoTag;
import io.hippocampus.hippodata.model.Tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for hippo service
 *
 * @author Patrick-4488
 */
@ExtendWith(MockitoExtension.class)
public class HippoTagServiceTest {

    @Mock
    private HippoTagDao hippoTagDao;

    @InjectMocks
    private HippoTagService classUnderTest;

    @Test
    public void saveHippoTagsWithoutHippoIdAndWithoutTagIds() {
        long hippoId = 0;
        List<Long> tagIds = null;

        classUnderTest.saveHippoTags(hippoId, tagIds);

        verify(hippoTagDao, never()).insert(any(HippoTag[].class));
    }

    @Test
    public void saveHippoTagsWithHippoIdAndWithoutTagIds() {
        long hippoId = 1;
        List<Long> tagIds = null;

        classUnderTest.saveHippoTags(hippoId, tagIds);

        verify(hippoTagDao, never()).insert(any(HippoTag[].class));
    }

    @Test
    public void saveHippoTagsWithoutHippoIdAndWithOneTagId() {
        long hippoId = 0;
        List<Long> tagIds = Arrays.asList(1L);

        classUnderTest.saveHippoTags(hippoId, tagIds);

        verify(hippoTagDao, never()).insert(any(HippoTag[].class));
    }

    @Test
    public void saveHippoTagsWithHippoIdAndWithOneTagId() {
        long hippoId = 1;
        List<Long> tagIds = Arrays.asList(1L);

        classUnderTest.saveHippoTags(hippoId, tagIds);

        verify(hippoTagDao, atMostOnce()).insert(any(HippoTag[].class));
    }

    @Test
    public void saveHippoTagsWithHippoIdAndWithThreeTagIds() {
        long hippoId = 1;
        List<Long> tagIds = Arrays.asList(1L, 2L, 3L);

        classUnderTest.saveHippoTags(hippoId, tagIds);

        verify(hippoTagDao, atMostOnce()).insert(any(HippoTag[].class));
    }

    @Test
    public void getHipposForTagWithoutTag() {
        Tag tag = null;

        List<Hippo> result = classUnderTest.getHipposForTag(tag);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getHipposForTagWithTagButNoHippoFound() {
        Tag tag = new Tag();
        when(hippoTagDao.getHipposForTag(any(Long.class))).thenReturn(new ArrayList<Hippo>());

        List<Hippo> result = classUnderTest.getHipposForTag(tag);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getHipposForTagWithTagOneHippoFound() {
        int amountExpected = 1;
        Tag tag = new Tag();
        when(hippoTagDao.getHipposForTag(any(Long.class))).thenReturn(Arrays.asList(new Hippo()));

        List<Hippo> result = classUnderTest.getHipposForTag(tag);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(amountExpected, result.size());
    }

    @Test
    public void getHipposForTagWithTagFiveHipposFound() {
        int amountExpected = 5;
        Tag tag = new Tag();
        when(hippoTagDao.getHipposForTag(any(Long.class))).thenReturn(
                Arrays.asList(new Hippo(), new Hippo(), new Hippo(), new Hippo(), new Hippo()));

        List<Hippo> result = classUnderTest.getHipposForTag(tag);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(amountExpected, result.size());
    }
}