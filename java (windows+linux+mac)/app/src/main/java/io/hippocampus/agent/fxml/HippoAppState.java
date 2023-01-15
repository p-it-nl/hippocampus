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

/**
 * The current state of the app
 *
 * @author Patrick-4488
 */
public class HippoAppState {

    private static HippoAppState instance;

    private Scene scene;
    private Node previousView;
    private Hippo editHippo;

    private HippoAppState() {
    }

    /**
     * returns instance of hippo app state
     *
     * @return singleton hippo app state
     */
    public static synchronized HippoAppState getInstance() {
        if (instance == null) {
            instance = new HippoAppState();
        }
        return instance;
    }

    /**
     * Set the (current) scene
     *
     * @param scene the scene
     */
    public void setScene(final Scene scene) {
        this.scene = scene;
    }

    /**
     * Get the (current) scene
     *
     * @return the scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Set the previous view (FXML)
     *
     * @param previousView the previous view
     */
    public void setPreviousView(final Node previousView) {
        this.previousView = previousView;
    }

    /**
     * Get the previous view
     *
     * @return the previous view
     */
    public Node getPreviousView() {
        return previousView;
    }

    /**
     * Get the hippo that is being edited
     *
     * @return hippo or null
     */
    public Hippo getEditHippo() {
        return editHippo;
    }

    /**
     * Set the hippo that is being edited
     *
     * @param editHippo the hippo
     */
    public void setEditHippo(final Hippo editHippo) {
        this.editHippo = editHippo;
    }
}
