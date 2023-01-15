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
import java.io.IOException;
import static java.lang.System.Logger.Level.WARNING;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Hippo controller provides the behaviour
 *
 * @author Patrick-4488
 */
public class HippoViewController extends HippoController {

    protected static final String HIPPO_ADD_URL = "fxml/hippocampus-add.fxml";
    protected static final String HIPPO_VIEW_NODE = "#hippoView";

    private static String needle;
    private static boolean inQueue;

    private final ScheduledExecutorService executor;

    private static final String SPLIT = " ";

    private static final System.Logger LOGGER = System.getLogger(HippoViewController.class.getName());

    public HippoViewController() {
        super();

        inQueue = false;
        executor = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Hippo event listener for going to add Hippo page
     *
     * @param e event the triggering event
     * @throws java.io.IOException when the .fxml file cannot be found or read
     */
    @FXML
    public void gotoToHippoAdd(final Event e) throws IOException {
        if (e != null && e.getSource() != null) {
            Scene prevScene = hippoAppState.getScene();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource(HIPPO_ADD_URL));
            Parent in = loader.load();

            setEditHippo(in);

            in.translateXProperty().set(prevScene.getWidth());

            StackPane out = (StackPane) prevScene.lookup(BASE_NODE);
            VBox hippoView = (VBox) prevScene.lookup(HIPPO_VIEW_NODE);
            out.getChildren().add(in);

            animate(in, out, hippoView);

        } else {
            LOGGER.log(WARNING, "Received event to go to hippo add screen but the event "
                    + "or the event source was null");
        }
    }

    /**
     * Hippo event listener for searching for hippos
     *
     * @param e the event
     */
    @FXML
    public void search(final Event e) {
        if (e != null && e.getSource() != null) {
            TextField field = (TextField) e.getSource();
            needle = field.getText();

            if (!inQueue) {
                executor.schedule(search(), 500, TimeUnit.MILLISECONDS);
                inQueue = true;
            } else {
                // already queued, preventing to many unnessary updates
            }
        } else {
            LOGGER.log(WARNING, "Received event to search but the event or the "
                    + "event source was null");
        }
    }

    /**
     * Set the hippo to edit
     *
     * @param hippo the hippo
     */
    public void setEditHippo(final Hippo hippo) {
        this.hippoAppState.setEditHippo(hippo);
    }

    private static Runnable search() {
        return () -> {
            hippoService.search(needle);
            inQueue = false;
        };
    }

    private void setEditHippo(final Parent in) {
        Hippo editHippo = this.hippoAppState.getEditHippo();
        if (editHippo != null) {
            TextField hippoText = (TextField) in.lookup(HIPPO_HIPPO);
            TextField tagsText = (TextField) in.lookup(HIPPO_TAGS);

            hippoText.setText(editHippo.getHippo());
            tagsText.setText(getTagsAsString(editHippo.getTags()));
        } else {

        }
    }

    private String getTagsAsString(List<String> tags) {
        StringBuilder sb = new StringBuilder();
        for (String tag : tags) {
            sb.append(tag);
            sb.append(SPLIT);
        }

        return sb.toString();
    }
}
