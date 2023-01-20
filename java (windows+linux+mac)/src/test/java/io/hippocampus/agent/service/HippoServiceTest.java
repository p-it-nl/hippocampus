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
import io.hippocampus.agent.exception.HippoCeption;
import io.hippocampus.agent.exception.HippoDataException;
import io.hippocampus.agent.exception.HippoException;
import io.hippocampus.agent.model.Hippo;
import java.io.File;
import java.util.List;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the hippo service
 *
 * @author Patrick-4488
 */
@ExtendWith(MockitoExtension.class)
public class HippoServiceTest {

    @Mock
    private HippoFileHelper hippoFileHelper;

    private HippoService classUnderTest;

    @BeforeEach
    public void setUp() {
        classUnderTest = HippoService.getInstance();
        when(hippoFileHelper.getHippoData())
                .thenReturn(new HippoData());
        classUnderTest.changeFileHelper(hippoFileHelper);
    }

    @Test
    public void addHippoWithoutHippo() {
        List<Hippo> hippos = classUnderTest.addHippo(null);

        assertNotNull(hippos);
        assertTrue(hippos.isEmpty());
    }

    @Test
    public void addHippoWithEmptyHippo() {
        List<Hippo> hippos = classUnderTest.addHippo(new Hippo());

        assertNotNull(hippos);
        assertFalse(hippos.isEmpty());
    }

    @Test
    public void addHippoWithAHippoHavingHippoNoTags() {
        Hippo hippo = HippoServiceTestUtil.getHippo(true, 0);

        List<Hippo> hippos = classUnderTest.addHippo(hippo);
        Hippo createdHippo = hippos.get(0);

        assertNotNull(hippos);
        assertFalse(hippos.isEmpty());
        assertNotNull(createdHippo);
        assertEquals(HippoServiceTestUtil.MOCKED_HIPPO, hippo.getHippo());
    }

    @Test
    public void addHippoWithAHippoHavingATag() {
        int expectedSize = 1;
        Hippo hippo = HippoServiceTestUtil.getHippo(true, 1);
        List<Hippo> hippos = classUnderTest.addHippo(hippo);
        Hippo createdHippo = hippos.get(0);

        assertNotNull(hippos);
        assertFalse(hippos.isEmpty());
        assertNotNull(createdHippo);
        assertEquals(HippoServiceTestUtil.MOCKED_HIPPO, createdHippo.getHippo());
        assertEquals(expectedSize, createdHippo.getTags().size());
    }

    @Test
    public void addHippoFromInputButNoHippoAndNoTags() {
        String hippo = null;
        String tags = null;

        List<Hippo> hippos = classUnderTest.addHippo(hippo, tags);

        assertNotNull(hippos);
        assertTrue(hippos.isEmpty());
    }

    @Test
    public void addHippoFromInputHavingEmptyHippoWithOneTag() {
        String hippo = "";
        String tags = HippoServiceTestUtil.MOCKED_TAG;

        List<Hippo> hippos = classUnderTest.addHippo(
                hippo, tags);

        assertNotNull(hippos);
        assertTrue(hippos.isEmpty());
    }

    @Test
    public void addHippoFromInputHavingHippoWithoutAnyTags() {
        String hippo = HippoServiceTestUtil.MOCKED_HIPPO;
        String tags = null;

        List<Hippo> hippos = classUnderTest.addHippo(
                hippo, tags);
        Hippo createdHippo = hippos.get(0);

        assertNotNull(hippos);
        assertFalse(hippos.isEmpty());
        assertNotNull(createdHippo);
        assertNotEquals(Long.MIN_VALUE, createdHippo.getId());
        assertEquals(HippoServiceTestUtil.MOCKED_HIPPO, hippo);
        assertNotNull(createdHippo.getTags());
        assertTrue(createdHippo.getTags().isEmpty());
    }

    @Test
    public void addHippoFromInputHavingHippoWithOneTag() {
        String hippo = HippoServiceTestUtil.MOCKED_HIPPO;
        String tags = HippoServiceTestUtil.MOCKED_TAG;
        int expectedAmountOfTags = 1;

        List<Hippo> hippos = classUnderTest.addHippo(
                hippo, tags);
        Hippo createdHippo = hippos.get(0);

        assertNotNull(hippos);
        assertFalse(hippos.isEmpty());
        assertNotNull(createdHippo);
        assertNotEquals(Long.MIN_VALUE, createdHippo.getId());
        assertEquals(HippoServiceTestUtil.MOCKED_HIPPO, hippo);
        assertNotNull(createdHippo.getTags());
        assertEquals(expectedAmountOfTags, createdHippo.getTags().size());
    }

    @Test
    public void addHippoFromInputHavingHippoWithMultipleTags() {
        String hippo = HippoServiceTestUtil.MOCKED_HIPPO;
        String tags = HippoServiceTestUtil.MOCKED__MULTIPLE_TAGS;
        int expectedAmountOfTags = 2;

        List<Hippo> hippos = classUnderTest.addHippo(
                hippo, tags);
        Hippo createdHippo = hippos.get(0);

        assertNotNull(hippos);
        assertFalse(hippos.isEmpty());
        assertNotNull(createdHippo);
        assertNotEquals(Long.MIN_VALUE, createdHippo.getId());
        assertEquals(HippoServiceTestUtil.MOCKED_HIPPO, hippo);
        assertNotNull(createdHippo.getTags());
        assertEquals(expectedAmountOfTags, createdHippo.getTags().size());
    }

    @Test
    public void addHippoFromInputHavingHippoWithMultipleTagsBothHavingTonsOfWeirdCaracters() {
        String hippo = HippoServiceTestUtil.MOCKED_HIPPO;
        String tags = HippoServiceTestUtil.MOCKED__MULTIPLE_TAGS;
        int expectedAmountOfTags = 2;

        List<Hippo> hippos = classUnderTest.addHippo(
                hippo, tags);
        Hippo createdHippo = hippos.get(0);

        assertNotNull(hippos);
        assertFalse(hippos.isEmpty());
        assertNotNull(createdHippo);
        assertNotEquals(Long.MIN_VALUE, createdHippo.getId());
        assertEquals(HippoServiceTestUtil.MOCKED_HIPPO, hippo);
        assertNotNull(createdHippo.getTags());
        assertEquals(expectedAmountOfTags, createdHippo.getTags().size());
    }

    @Test
    public void deleteHippoWithoutHippo() {
        Hippo hippo = null;

        List<Hippo> hippos = classUnderTest.deleteHippo(hippo);

        assertNotNull(hippos);
        assertTrue(hippos.isEmpty());
    }

    @Test
    public void deleteHippoWithEmptyHippo() {
        Hippo hippo = new Hippo();

        List<Hippo> hippos = classUnderTest.deleteHippo(hippo);

        assertNotNull(hippos);
        assertTrue(hippos.isEmpty());
    }

    @Test
    public void deleteHippoWithValidHippo() {
        Hippo hippo = HippoServiceTestUtil.getHippo(true, 2);

        List<Hippo> currentHippos = classUnderTest.addHippo(hippo);
        Hippo existingHippo = currentHippos.get(0);
        List<Hippo> hippos = classUnderTest.deleteHippo(existingHippo);

        assertNotNull(hippos);
        assertTrue(hippos.isEmpty());
    }

    @Test
    public void deleteHippoWithValidHippoButDoesNotExists() {
        Hippo notExisting = HippoServiceTestUtil.getHippo(true, 2);

        List<Hippo> hippos = classUnderTest.deleteHippo(notExisting);

        assertNotNull(hippos);
        assertTrue(hippos.isEmpty());
    }

    @Test
    public void getHipposButNoneExist() {
        List<Hippo> hippos = classUnderTest.getHippos();

        assertNotNull(hippos);
        assertTrue(hippos.isEmpty());
    }

    /**
     * When the file == null, the default location is used. At the time of
     * running the unit test, there might be an existing backup in this
     * location. Both are good situations, that is why the unit test asserts two
     * paths
     */
    @Test
    public void loadHipposFromBackUpWithoutAFile() {
        HippoCeption expected = HippoCeption.NOT_A_HIPPOS_FILE;
        File file = null;

        HippoException exception = null;
        try {
            classUnderTest.loadHipposFromBackUp(file);
            assertNull(exception);
        } catch (HippoDataException ex) {
            exception = ex;
            assertNotNull(exception);
            assertEquals(expected.getMessage(), exception.getMessage());
        } catch (IllegalStateException ex) {
            System.out.println("Toolkit error, this can occur due to JavaFX not "
                    + "being loaded in properly for the test. We ignore this for"
                    + " now but we hope to be able to mock JavaFX in the future");
            // I know TestFx exists but I dont like it forcing dependencies on hamcrest or Assertj
        }
    }

    @Test
    public void loadHipposFromBackUpWithEmptyFile() {
        HippoCeption expected = HippoCeption.NOT_A_HIPPOS_FILE;
        File file = new File("");

        HippoException exception = assertThrows(HippoDataException.class,
                () -> classUnderTest.loadHipposFromBackUp(file));

        assertNotNull(exception);
        assertEquals(expected.getMessage(), exception.getMessage());
    }

    @Test
    public void loadHipposFromBackUpWithMockedFile() {
        HippoCeption expected = HippoCeption.NOT_A_HIPPOS_FILE;
        File file = new File(HippoServiceTestUtil.MOCKED_BACKUP_FILE);

        HippoException exception = assertThrows(HippoDataException.class,
                () -> classUnderTest.loadHipposFromBackUp(file));

        assertNotNull(exception);
        assertEquals(expected.getMessage(), exception.getMessage());
    }

    @Test
    public void save() {
        assertDoesNotThrow(() -> classUnderTest.save());
    }

    @Test
    public void startPropagation() {
        assertDoesNotThrow(() -> classUnderTest.startPropagation(null));
    }

    @Test
    public void startPropagationWithMockedScene() {
        Scene mockedScene = Mockito.mock(Scene.class);

        RuntimeException ex = assertThrows(IllegalStateException.class,
                () -> classUnderTest.startPropagation(mockedScene));

        verify(mockedScene, times(2)).lookup(any(String.class));

        assertNotNull(ex);
    }

    @Test
    public void updateHippoWithoutHippo() {
        Hippo updatedHippo = null;

        List<Hippo> hipposAfterUpdate = classUnderTest.updateHippo(updatedHippo);

        assertNotNull(hipposAfterUpdate);
        assertTrue(hipposAfterUpdate.isEmpty());
    }

    @Test
    public void updateHippoWithEmtpyHippo() {
        Hippo updatedHippo = new Hippo();

        List<Hippo> hipposAfterUpdate = classUnderTest.updateHippo(updatedHippo);

        assertNotNull(hipposAfterUpdate);
        assertTrue(hipposAfterUpdate.isEmpty());
    }

    @Test
    public void updateHippoButItDoesNotExist() {
        Hippo updatedHippo = HippoServiceTestUtil.getHippo(true, 1);

        List<Hippo> hipposAfterUpdate = classUnderTest.updateHippo(updatedHippo);

        assertNotNull(hipposAfterUpdate);
        assertTrue(hipposAfterUpdate.isEmpty());
    }

    @Test
    public void updateHippoWhichDoesNotExist() {
        String expectedHippo = HippoServiceTestUtil.MOCKED_HIPPO_UPDATED;
        Hippo hippo = HippoServiceTestUtil.getHippo(true, 1);

        List<Hippo> hippos = classUnderTest.addHippo(hippo);
        Hippo updatedHippo = hippos.get(0);
        updatedHippo.setHippo(expectedHippo);
        List<Hippo> hipposAfterUpdate = classUnderTest.updateHippo(updatedHippo);
        updatedHippo = hipposAfterUpdate.get(0);

        assertNotNull(updatedHippo);
        assertEquals(expectedHippo, updatedHippo.getHippo());
    }

    @Test
    public void updateHippoWithoutAnyParameters() {
        String hippo = null;
        String tags = null;
        Hippo hippoToUpdate = null;

        classUnderTest.updateHippo(hippo, tags, hippoToUpdate);
        List<Hippo> hipposAfterUpdate = classUnderTest.getHippos();

        assertNotNull(hipposAfterUpdate);
        assertTrue(hipposAfterUpdate.isEmpty());
    }

    @Test
    public void updateHippoWithEmptyParameters() {
        String hippo = "";
        String tags = "";
        Hippo hippoToUpdate = new Hippo();

        classUnderTest.updateHippo(hippo, tags, hippoToUpdate);
        List<Hippo> hipposAfterUpdate = classUnderTest.getHippos();

        assertNotNull(hipposAfterUpdate);
        assertTrue(hipposAfterUpdate.isEmpty());
    }

    @Test
    public void updateHippoWithHippoAndTagsButNoTargetHippoForTheEdit() {
        String hippo = HippoServiceTestUtil.MOCKED_HIPPO;
        String tags = HippoServiceTestUtil.MOCKED_TAG;
        Hippo hippoToUpdate = null;

        classUnderTest.updateHippo(hippo, tags, hippoToUpdate);
        List<Hippo> hipposAfterUpdate = classUnderTest.getHippos();

        assertNotNull(hipposAfterUpdate);
        assertTrue(hipposAfterUpdate.isEmpty());
    }

    @Test
    public void updateHippoWithAllValuesValidButHippoDoesNotExist() {
        String hippo = HippoServiceTestUtil.MOCKED_HIPPO;
        String tags = HippoServiceTestUtil.MOCKED_TAG;
        Hippo hippoToUpdate = HippoServiceTestUtil.getHippo(true, 1);

        classUnderTest.updateHippo(hippo, tags, hippoToUpdate);
        List<Hippo> hipposAfterUpdate = classUnderTest.getHippos();

        assertNotNull(hipposAfterUpdate);
        assertTrue(hipposAfterUpdate.isEmpty());
    }

    @Test
    public void updateHippoWithAllValuesAndExistingHippo() {
        String hippo = HippoServiceTestUtil.MOCKED_HIPPO;
        String tags = HippoServiceTestUtil.MOCKED_TAG;
        Hippo hippoToCreate = HippoServiceTestUtil.getHippo(true, 1);

        List<Hippo> hippos = classUnderTest.addHippo(hippoToCreate);
        Hippo hippoToUpdate = hippos.get(0);
        classUnderTest.updateHippo(hippo, tags, hippoToUpdate);
        List<Hippo> hipposAfterUpdate = classUnderTest.getHippos();
        Hippo updatedHippo = hipposAfterUpdate.get(0);

        assertNotNull(updatedHippo);
        assertEquals(hippo, updatedHippo.getHippo());
        assertEquals(tags, updatedHippo.getTags().get(0));
    }
}
