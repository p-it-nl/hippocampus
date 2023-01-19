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
package io.hippocampus.hippodata.service;

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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import io.hippocampus.hippodata.dao.HippoDao;
import io.hippocampus.hippodata.database.AppDatabase;
import io.hippocampus.hippodata.entity.Hippo;

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

    private HippoService classUnderTest;

    private static final String HIPPO = "mock";
    private static final String TAG = "mock";
    private static final String TAG_TWO = "mock, mock";
    private static final String TAG_MANY = "mock, mock, mock, mock, mock, mock, mock";

    @BeforeEach
    public void setUp() {
        when(db.getHippoDao()).thenReturn(hippoDao);

        classUnderTest = new HippoService(db);
    }

    @Test
    public void saveHippoWithoutHippoTextAndWithoutTags() {
        String hippoText = null;
        String tags = null;

        classUnderTest.saveHippo(hippoText, tags);

        verify(hippoDao, never()).insert(any(Hippo.class));
    }

    @Test
    public void saveHippoWithEmptyHippoTextAndWithoutTags() {
        String hippoText = "";
        String tags = null;

        classUnderTest.saveHippo(hippoText, tags);

        verify(hippoDao, never()).insert(any(Hippo.class));
    }

    @Test
    public void saveHippoWithHippoTextAndWithoutTags() {
        String hippoText = HIPPO;
        String tags = null;

        classUnderTest.saveHippo(hippoText, tags);

        verify(hippoDao, atMostOnce()).insert(any(Hippo.class));
    }

    @Test
    public void saveHippoWithHippoTextAndWithEmptyTags() {
        String hippoText = HIPPO;
        String tags = "";

        classUnderTest.saveHippo(hippoText, tags);

        verify(hippoDao, atMostOnce()).insert(any(Hippo.class));
    }

    @Test
    public void saveHippoWithHippoTextAndWithOneTag() {
        String hippoText = HIPPO;
        String tags = TAG;

        classUnderTest.saveHippo(hippoText, tags);

        verify(hippoDao, atMostOnce()).insert(any(Hippo.class));
    }

    @Test
    public void saveHippoWithHippoTextAndWithTwoTags() {
        String hippoText = HIPPO;
        String tags = TAG_TWO;

        classUnderTest.saveHippo(hippoText, tags);

        verify(hippoDao, atMostOnce()).insert(any(Hippo.class));
    }

    @Test
    public void saveHippoWithHippoTextAndWithManyTags() {
        String hippoText = HIPPO;
        String tags = TAG_MANY;

        classUnderTest.saveHippo(hippoText, tags);

        verify(hippoDao, atMostOnce()).insert(any(Hippo.class));
    }

    @Test
    public void saveHippoWithoutHippo() {
        Hippo hippo = null;

        classUnderTest.saveHippo(hippo);

        verify(hippoDao, never()).insert(any(Hippo.class));
    }

    @Test
    public void saveHippoWithEmptyHippo() {
        Hippo hippo = new Hippo();

        classUnderTest.saveHippo(hippo);

        verify(hippoDao, never()).insert(any(Hippo.class));
    }

    @Test
    public void saveHippoWithHippoHavingHippoTextAndNoTags() {
        Hippo hippo = new Hippo(HIPPO, null);

        classUnderTest.saveHippo(hippo);

        verify(hippoDao, atMostOnce()).insert(any(Hippo.class));
    }

    @Test
    public void saveHippoWithHippoHavingHippoTextAndTags() {
        Hippo hippo = new Hippo(HIPPO, TAG_TWO);

        classUnderTest.saveHippo(hippo);

        verify(hippoDao, atMostOnce()).insert(any(Hippo.class));
    }

    @Test
    public void saveHippoButHippoAlreadyExistsHavingSameVersion() {
        int amountExpected = 2;
        Hippo hippo = new Hippo(HIPPO, TAG_TWO);
        hippo.id = 1L;

        classUnderTest.saveHippo(hippo);
        classUnderTest.saveHippo(hippo);

        verify(hippoDao, times(amountExpected)).insert(any(Hippo.class));
    }

    @Test
    public void saveHippoButHippoAlreadyExistsHavingHigherVersion() {
        int amountExpected = 2;
        Hippo hippo = new Hippo(HIPPO, TAG_TWO);
        hippo.id = 1L;

        classUnderTest.saveHippo(hippo);
        hippo.upVersion();
        classUnderTest.saveHippo(hippo);

        verify(hippoDao, times(amountExpected)).insert(any(Hippo.class));
    }


    @Test
    public void saveHippoButHippoAlreadyExistsHavingLowerVersion() {
        Hippo hippo = new Hippo(HIPPO, TAG_TWO);
        hippo.id = 1L;
        hippo.version = 2L;

        classUnderTest.saveHippo(hippo);

        when(hippoDao.getById(hippo.id)).thenReturn(hippo);
        hippo.version = 1L;
        classUnderTest.saveHippo(hippo);

        verify(hippoDao, atMostOnce()).insert(any(Hippo.class));
    }

    @Test
    public void saveHipposWithoutHippos() {
        List<Hippo> hippos = null;

        classUnderTest.saveHippos(hippos);

        verify(hippoDao, never()).insert(any(Hippo.class));
    }

    @Test
    public void saveHipposWithEmptyHippos() {
        List<Hippo> hippos = new ArrayList<>();

        classUnderTest.saveHippos(hippos);

        verify(hippoDao, never()).insert(any(Hippo.class));
    }

    @Test
    public void saveHipposWithOneHippo() {
        int amountExpected = 1;
        List<Hippo> hippos = Arrays.asList(new Hippo(HIPPO, TAG));

        classUnderTest.saveHippos(hippos);

        verify(hippoDao, times(amountExpected)).insert(any(Hippo.class));
    }

    @Test
    public void saveHipposWithTwoHippos() {
        int amountExpected = 2;
        List<Hippo> hippos = Arrays.asList(new Hippo(HIPPO, TAG), new Hippo(HIPPO, TAG));

        classUnderTest.saveHippos(hippos);

        verify(hippoDao, times(amountExpected)).insert(any(Hippo.class));
    }

    @Test
    public void saveHipposWithManyHippos() {
        int amountExpected = 4;
        List<Hippo> hippos = Arrays.asList(new Hippo(HIPPO, TAG), new Hippo(HIPPO, TAG), new Hippo(HIPPO, TAG_TWO), new Hippo(HIPPO, TAG_MANY));

        classUnderTest.saveHippos(hippos);

        verify(hippoDao, times(amountExpected)).insert(any(Hippo.class));
    }

    @Test
    public void getAllHipposNotHavingHipposAndNoPredicate() {
        Predicate predicate = null;

        List<Hippo> result = classUnderTest.getHippos(predicate);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllHipposHavingOneHippoAndNoPredicate() {
        int amountExpected = 1;
        Predicate predicate = null;
        when(hippoDao.getAll()).thenReturn(Arrays.asList(new Hippo()));

        List<Hippo> result = classUnderTest.getHippos(predicate);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(amountExpected, result.size());
    }

    @Test
    public void getAllHipposHavingThreeHipposAndNoPredicate() {
        int amountExpected = 3;
        Predicate predicate = null;
        when(hippoDao.getAll()).thenReturn(Arrays.asList(new Hippo(), new Hippo(), new Hippo()));

        List<Hippo> result = classUnderTest.getHippos(predicate);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(amountExpected, result.size());
    }

    @Test
    public void getAllHipposNotHavingHipposAndPredicateNotMatchingAnyHippos() {
        List<Hippo> result = classUnderTest.getHippos(hippo -> hippo.id == 100L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllHipposHavingOneHippoAndPredicateNotMatchingAnyHippos() {
        Hippo firstHippo = new Hippo();
        firstHippo.id = 1L;
        when(hippoDao.getAll()).thenReturn(Arrays.asList(firstHippo));

        List<Hippo> result = classUnderTest.getHippos(hippo -> hippo.id == 100L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllHipposHavingThreeHipposAndPredicateNotMatchingAnyHippos() {
        Hippo firstHippo = new Hippo();
        firstHippo.id = 1L;
        Hippo secondHippo = new Hippo();
        firstHippo.id = 2L;
        Hippo thirdHippo = new Hippo();
        firstHippo.id = 3L;
        when(hippoDao.getAll()).thenReturn(Arrays.asList(firstHippo, secondHippo, thirdHippo));

        List<Hippo> result = classUnderTest.getHippos(hippo -> hippo.id == 100L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllHipposHavingThreeHipposAndPredicateMatchingTwoHippos() {
        int amountExpected = 2;
        Hippo firstHippo = new Hippo();
        firstHippo.id = 1L;
        Hippo secondHippo = new Hippo();
        firstHippo.id = 2L;
        Hippo thirdHippo = new Hippo();
        firstHippo.id = 3L;
        when(hippoDao.getAll()).thenReturn(Arrays.asList(firstHippo, secondHippo, thirdHippo));

        List<Hippo> result = classUnderTest.getHippos(hippo -> hippo.id < 3L);

        for (Hippo h : result) {
            System.out.println(h);
        }
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(amountExpected, result.size());
    }

    @Test
    public void deleteHippoWithoutHippo() {
        Hippo hippo = null;

        classUnderTest.deleteHippo(hippo);

        verify(hippoDao, never()).delete(any(Hippo.class));
    }

    @Test
    public void deleteHippoWithEmptyHippo() {
        Hippo hippo = new Hippo();

        classUnderTest.deleteHippo(hippo);

        verify(hippoDao, atMostOnce()).delete(any(Hippo.class));
    }

    @Test
    public void deleteHipposWithoutHippos() {
        Hippo[] hippos = null;

        classUnderTest.deleteHippos(hippos);

        verify(hippoDao, never()).delete(any(Hippo.class));
    }

    @Test
    public void deleteHipposWithEmptyHippos() {
        Hippo[] hippos = new Hippo[1];

        classUnderTest.deleteHippos(hippos);

        verify(hippoDao, never()).delete(any(Hippo.class));
    }

    @Test
    public void deleteHipposWithOneHippo() {
        Hippo[] hippos = {new Hippo(HIPPO, TAG)};

        classUnderTest.deleteHippos(hippos);

        verify(hippoDao, atMostOnce()).delete(any(Hippo.class));
    }

    @Test
    public void deleteHipposWithThreeHippos() {
        Hippo[] hippos = {new Hippo(HIPPO, TAG), new Hippo(HIPPO, TAG), new Hippo(HIPPO, TAG)};

        classUnderTest.deleteHippos(hippos);

        verify(hippoDao, atMostOnce()).delete(any(Hippo.class));
    }
}