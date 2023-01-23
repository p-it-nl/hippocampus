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
package io.hippocampus.agent.fxml.loader;

import io.hippocampus.agent.model.Hippo;
import java.util.List;
import javafx.scene.Scene;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for hippo loader
 *
 * @author Patrick-4488
 */
public class HippoLoaderTest {

    @Test
    public void addHipposToScreenWithoutValues() {
        HippoLoader classUnderTest = new HippoLoader();
        Scene scene = null;
        List<Hippo> hippos = null;

        assertDoesNotThrow(() -> classUnderTest.addHipposToScreen(scene, hippos));
    }

    @Test
    public void addHipposToScreenWithScreenButWithoutHippos() {
        HippoLoader classUnderTest = new HippoLoader();
        Scene mockedScene = Mockito.mock(Scene.class);
        List<Hippo> hippos = null;

        RuntimeException ex = assertThrows(IllegalStateException.class,
                () -> classUnderTest.addHipposToScreen(mockedScene, hippos));

        verify(mockedScene, times(2)).lookup(any(String.class));

        assertNotNull(ex);
    }

    @Test
    public void addHipposToScreenWithScreenAndHippos() {
        HippoLoader classUnderTest = new HippoLoader();
        Scene mockedScene = Mockito.mock(Scene.class);
        List<Hippo> hippos = HippoLoaderTestUtil.getHippos(2);

        RuntimeException ex = assertThrows(IllegalStateException.class,
                () -> classUnderTest.addHipposToScreen(mockedScene, hippos));

        verify(mockedScene, times(2)).lookup(any(String.class));

        assertNotNull(ex);
    }
}
