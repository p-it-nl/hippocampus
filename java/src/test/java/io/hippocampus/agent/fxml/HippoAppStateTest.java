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
package io.hippocampus.agent.fxml;

import io.hippocampus.agent.model.Hippo;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for hippo app state
 *
 * @author Patrick-4488
 */
public class HippoAppStateTest {

    private HippoAppState classUnderTest;

    @BeforeEach
    public void setUp() {
        classUnderTest = HippoAppState.getInstance();
    }

    @Test
    public void setSceneWithoutScene() {
        Scene scene = null;

        classUnderTest.setScene(scene);

        assertEquals(scene, classUnderTest.getScene());
    }

    @Test
    public void setSceneWithScene() {
        Scene scene = Mockito.mock(Scene.class);

        classUnderTest.setScene(scene);

        assertEquals(scene, classUnderTest.getScene());
    }

    @Test
    public void setPreviousViewWithoutView() {
        Node node = null;

        classUnderTest.setPreviousView(node);

        assertEquals(node, classUnderTest.getPreviousView());
    }

    @Test
    public void setPreviousViewWithView() {
        Node node = Mockito.mock(Node.class);

        classUnderTest.setPreviousView(node);

        assertEquals(node, classUnderTest.getPreviousView());
    }

    @Test
    public void setEditHippoWithoutScene() {
        Hippo hippo = null;

        classUnderTest.setEditHippo(hippo);

        assertEquals(hippo, classUnderTest.getEditHippo());
    }

    @Test
    public void setEditHippoWithHippo() {
        Hippo hippo = Mockito.mock(Hippo.class);

        classUnderTest.setEditHippo(hippo);

        assertEquals(hippo, classUnderTest.getEditHippo());
    }
}
