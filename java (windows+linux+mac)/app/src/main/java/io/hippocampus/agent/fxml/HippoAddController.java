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

import io.hippocampus.agent.fxml.button.SaveButton;
import io.hippocampus.agent.model.Hippo;
import java.io.IOException;
import static java.lang.System.Logger.Level.WARNING;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Hippo controller provides the behaviour for the add hippo page
 *
 * @author Patrick-4488
 */
public class HippoAddController extends HippoController {

    private final String HIPPO_ADD_NODE = "#hippoAdd";

    private static final System.Logger LOGGER = System.getLogger(HippoAddController.class.getName());

    public HippoAddController() {
        super();
    }

    /**
     * Hippo event listener for going to view Hippo page
     *
     * @param e event the triggering event
     * @throws IOException when the .fxml file cannot be found or read
     */
    @FXML
    public void saveHippo(final Event e) throws IOException {
        if (e != null && e.getSource() != null) {
            SaveButton button = (SaveButton) e.getSource();
            Scene scene = button.getScene();

            TextField hippoText = (TextField) scene.lookup(HIPPO_HIPPO);
            TextField tagsText = (TextField) scene.lookup(HIPPO_TAGS);

            Hippo editHippo = hippoAppState.getEditHippo();
            if (editHippo != null) {
                hippoService.updateHippo(hippoText.getText(), tagsText.getText(), editHippo);
            } else {
                hippoService.addHippo(hippoText.getText(), tagsText.getText());
            }

            gotoHippoView();
        } else {
            LOGGER.log(WARNING, "Received event to save a hippo but the event or the "
                    + "event source was null");
        }
    }

    private void gotoHippoView() {
        Scene prevScene = hippoAppState.getScene();

        Parent in = (Parent) hippoAppState.getPreviousView();

        in.translateXProperty().set((0 - prevScene.getWidth()));

        StackPane out = (StackPane) prevScene.lookup(BASE_NODE);
        VBox hippoView = (VBox) prevScene.lookup(HIPPO_ADD_NODE);
        out.getChildren().add(in);

        animate(in, out, hippoView);
    }
}
