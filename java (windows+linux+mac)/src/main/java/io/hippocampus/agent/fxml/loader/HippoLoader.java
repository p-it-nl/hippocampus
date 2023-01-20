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
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static java.lang.System.Logger.Level.WARNING;

/**
 * Handles all the hippos in the list
 *
 * @author Patrick-4488
 */
public class HippoLoader {

    private final String HIPPO_LIST_ID = "#hippoList";
    private final String HIPPO_SCROLL_ID = "#scrollPane";
    private final String HIPPO_WRAPPER_ID = "hippo";
    private final String HIPPO_ROW_TEXT = "row-text";

    private static final System.Logger LOGGER = System.getLogger(HippoLoader.class.getName());

    /**
     * Will add hippos to the list
     *
     * @param scene the scene containing the list
     * @param hippos list of hippos to add
     */
    public void addHipposToScreen(final Scene scene, final List<Hippo> hippos) {
        if (scene != null) {
            VBox contentWrapper = (VBox) scene.lookup(HIPPO_LIST_ID);
            ScrollPane scroll = (ScrollPane) scene.lookup(HIPPO_SCROLL_ID);

            Platform.runLater(() -> {
                if (scroll != null && contentWrapper != null) {
                    generatePanes(hippos, contentWrapper, scroll);
                } else {
                    LOGGER.log(WARNING, "Cannot add anything to something that doesn't exist");
                }
            });
        } else {
            // No scene, no action
        }
    }

    /**
     * Remove the hippos currently loaded on the screen
     *
     * @param scene the scene containing the hippos
     */
    public void clearHipposFromScreen(final Scene scene) {
        if (scene != null) {
            VBox contentWrapper = (VBox) scene.lookup(HIPPO_LIST_ID);

            Platform.runLater(() -> {
                contentWrapper.getChildren().clear();
            });
        } else {
            // No scene, no action
        }
    }

    private void generatePanes(
            final List<Hippo> hippos,
            final VBox contentWrapper,
            final ScrollPane scroll) {
        for (Hippo hippo : hippos) {
            contentWrapper.getChildren().add(getHippoInPane(
                    hippo, contentWrapper, scroll));
        }
    }

    private VBox getHippoInPane(final Hippo hippo,
            final VBox contentWrapper,
            final ScrollPane scroll) {
        VBox hippoWrapper = new VBox();

        HBox textWrapper = new HBox();
        Text hippoText = new Text(hippo.getHippo());
        hippoText.wrappingWidthProperty().bind(scroll.widthProperty().subtract(20));
        textWrapper.getChildren().add(hippoText);
        textWrapper.setOnMouseClicked(
                new OnHippoClick(hippo, hippoWrapper, contentWrapper));
        textWrapper.setId(HIPPO_ROW_TEXT);

        hippoWrapper.getChildren().add(textWrapper);
        hippoWrapper.setId(HIPPO_WRAPPER_ID);

        return hippoWrapper;
    }
}
