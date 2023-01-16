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

import io.hippocampus.hippodata.dao.HippoDao;
import io.hippocampus.hippodata.dao.HippoTagDao;
import io.hippocampus.hippodata.dao.TagDao;
import io.hippocampus.hippodata.database.AppDatabase;
import io.hippocampus.hippodata.entity.Hippo;
import io.hippocampus.hippodata.entity.HippoTag;
import io.hippocampus.hippodata.entity.Tag;

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
public class HippoServiceTest {

    @Mock
    private AppDatabase db;

    @Mock
    private HippoDao hippoDao;

    @Mock
    private TagDao tagDao;

    @Mock
    private HippoTagDao hippoTagDao;

    private HippoService classUnderTest;

    private static final String HIPPO = "mock";
    private static final String TAG = "mock";

    @BeforeEach
    public void setUp() {
        when(db.getHippoDao()).thenReturn(hippoDao);
        when(db.getTagDao()).thenReturn(tagDao);
        when(db.getHippoTagDao()).thenReturn(hippoTagDao);

        classUnderTest = new HippoService(db);
    }

    @Test
    public void saveHippoWithoutHippoTextAndWithoutTags() {
        String hippoText = null;
        List<String> tags = null;

        classUnderTest.saveHippo(hippoText, tags);

        verify(hippoDao, never()).insert(any(Hippo.class));
        verify(tagDao, never()).insert(any(Tag.class));
    }

    @Test
    public void saveHippoWithEmptyHippoTextAndWithoutTags() {
        String hippoText = new String();
        List<String> tags = null;

        classUnderTest.saveHippo(hippoText, tags);

        verify(hippoDao, never()).insert(any(Hippo.class));
        verify(tagDao, never()).insert(any(Tag.class));
    }

    @Test
    public void saveHippoWithHippoTextAndWithoutTags() {
        String hippoText = HIPPO;
        List<String> tags = null;

        classUnderTest.saveHippo(hippoText, tags);

        verify(hippoDao, atMostOnce()).insert(any(Hippo.class));
        verify(tagDao, never()).insert(any(Tag.class));
    }

    @Test
    public void saveHippoWithHippoTextAndWithEmptyTags() {
        String hippoText = HIPPO;
        List<String> tags = new ArrayList<>();

        classUnderTest.saveHippo(hippoText, tags);

        verify(hippoDao, atMostOnce()).insert(any(Hippo.class));
        verify(tagDao, never()).insert(any(Tag.class));
    }

    @Test
    public void saveHippoWithHippoTextAndWithOneTag() {
        String hippoText = HIPPO;
        List<String> tags = Arrays.asList(TAG);

        classUnderTest.saveHippo(hippoText, tags);

        verify(hippoDao, atMostOnce()).insert(any(Hippo.class));
        verify(tagDao, atMostOnce()).insert(any(Tag.class));
        verify(hippoTagDao, atMostOnce()).insert(any(HippoTag[].class));
    }

    @Test
    public void saveHippoWithHippoTextAndWithTwoTags() {
        int expectedAmountTags = 2;
        String hippoText = HIPPO;
        List<String> tags = Arrays.asList(TAG, TAG);

        classUnderTest.saveHippo(hippoText, tags);

        verify(hippoDao, atMostOnce()).insert(any(Hippo.class));
        verify(tagDao, times(expectedAmountTags)).insert(any(Tag.class));
        verify(hippoTagDao, atMostOnce()).insert(any(HippoTag[].class));
    }

    @Test
    public void saveHippoWithHippoTextAndWithManyTags() {
        int expectedAmountTags = 6;
        String hippoText = HIPPO;
        List<String> tags = Arrays.asList(TAG, TAG, TAG, TAG, TAG, TAG);

        classUnderTest.saveHippo(hippoText, tags);

        verify(hippoDao, atMostOnce()).insert(any(Hippo.class));
        verify(tagDao, times(expectedAmountTags)).insert(any(Tag.class));
        verify(hippoTagDao, atMostOnce()).insert(any(HippoTag[].class));
    }

    @Test
    public void getAllHipposNotHavingHippos() {
        List<Hippo> result = classUnderTest.getAllHippos();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllHipposHavingOneHippo() {
        int amountExpected = 1;
        when(hippoDao.getAll()).thenReturn(Arrays.asList(new Hippo()));

        List<Hippo> result = classUnderTest.getAllHippos();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(amountExpected, result.size());
    }

    @Test
    public void getAllHipposHavingThreeHippos() {
        int amountExpected = 3;
        when(hippoDao.getAll()).thenReturn(Arrays.asList(new Hippo(), new Hippo(), new Hippo()));

        List<Hippo> result = classUnderTest.getAllHippos();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(amountExpected, result.size());
    }

    @Test
    public void deleteHippoWithoutHippo() {
        Hippo hippo = null;

        classUnderTest.deleteHippo(hippo);
    }

    @Test
    public void deleteHippoWithHippo() {
        Hippo hippo = new Hippo();

        classUnderTest.deleteHippo(hippo);
    }
}