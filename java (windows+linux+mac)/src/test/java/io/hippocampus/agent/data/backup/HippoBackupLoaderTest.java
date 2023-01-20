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

import io.hippocampus.agent.data.HippoData;
import io.hippocampus.agent.exception.HippoCeption;
import io.hippocampus.agent.exception.HippoDataException;
import io.hippocampus.agent.exception.HippoException;
import io.hippocampus.agent.model.Hippo;
import io.hippocampus.agent.service.HippoFileHelper;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for hippo backup loader
 *
 * @author Patrick-4488
 */
@ExtendWith(MockitoExtension.class)
public class HippoBackupLoaderTest {

    @Mock
    private HippoData hippoData;

    @Mock
    private HippoFileHelper fileHelper;

    @Mock
    private File file;

    private HippoBackupLoader classUnderTest;

    @BeforeEach
    public void setUp() {
        classUnderTest = new HippoBackupLoader();
    }

    @Test
    public void loadHipposFromBackUpWithoutHippoDataFileHelperAndFile() {
        assertDoesNotThrow(() -> classUnderTest.loadHipposFromBackUp(null, null, null));

        verify(hippoData, never()).addHippo(any(Hippo.class));
    }

    @Test
    public void loadHipposFromBackUpWithoutHippoData() {
        assertDoesNotThrow(() -> classUnderTest.loadHipposFromBackUp(null, fileHelper, file));

        verify(hippoData, never()).addHippo(any(Hippo.class));
    }

    @Test
    public void loadHipposFromBackUpWithoutFilehelper() {
        HippoCeption expected = HippoCeption.NOT_A_HIPPOS_FILE;

        HippoException exception = assertThrows(HippoDataException.class,
                () -> classUnderTest.loadHipposFromBackUp(hippoData, null, file));

        verify(hippoData, never()).addHippo(any(Hippo.class));
        assertEquals(expected.getMessage(), exception.getMessage());
    }

    /**
     * When the file == null, the default location is used. At the time of
     * running the unit test, there might be an existing backup in this
     * location. Both are good situations, that is why the unit test asserts two
     * paths
     */
    @Test
    public void loadHipposFromBackUpWithoutFile() {
        HippoCeption expected = HippoCeption.NOT_A_HIPPOS_FILE;

        try {
            classUnderTest.loadHipposFromBackUp(hippoData, fileHelper, null);
            verify(hippoData, atLeast(1)).addHippo(any(Hippo.class));
        } catch (HippoException ex) {
            verify(hippoData, never()).addHippo(any(Hippo.class));
            assertEquals(expected.getMessage(), ex.getMessage());
        }
    }

    /**
     * When the file == null, the default location is used. At the time of
     * running the unit test, there might be an existing backup in this
     * location. Both are good situations, that is why the unit test asserts two
     * paths
     */
    @Test
    public void loadHipposFromBackUpHavingHippoDataFileHelperAndFile() {
        HippoCeption expected = HippoCeption.NOT_A_HIPPOS_FILE;

        try {
            classUnderTest.loadHipposFromBackUp(hippoData, fileHelper, file);
            verify(hippoData, atLeast(1)).addHippo(any(Hippo.class));
        } catch (HippoException ex) {
            verify(hippoData, never()).addHippo(any(Hippo.class));
            assertEquals(expected.getMessage(), ex.getMessage());
        }
    }
}
