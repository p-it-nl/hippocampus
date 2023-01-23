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

import io.hippocampus.agent.fxml.HippoViewController;
import io.hippocampus.agent.fxml.button.DeleteButton;
import io.hippocampus.agent.fxml.button.EditButton;
import io.hippocampus.agent.model.Hippo;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.event.Event;
import javafx.scene.layout.HBox;

import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.WARNING;

/**
 * On hippo click listener
 *
 * @author Patrick-4488
 */
public class OnHippoClick implements EventHandler {

    private final Hippo hippo;
    private final VBox hippoWrapper;
    private final VBox contentWrapper;

    private static final String PRE_SELECTOR = "#";
    private static final String SECLECTOR = "pane";

    private static final System.Logger LOGGER = System.getLogger(OnHippoClick.class.getName());

    public OnHippoClick(
            final Hippo hippo,
            final VBox hippoWrapper,
            final VBox contentWrapper) {
        this.hippo = hippo;
        this.hippoWrapper = hippoWrapper;
        this.contentWrapper = contentWrapper;
    }

    @Override
    public void handle(final Event e) {
        if (hippoWrapper != null) {
            HBox hippoPane = (HBox) hippoWrapper.lookup(PRE_SELECTOR + SECLECTOR);
            if (hippoPane != null) {
                removePane(hippoPane);
            } else {
                try {
                    addPane();
                } catch (NoClassDefFoundError | ExceptionInInitializerError ex) {
                    LOGGER.log(ERROR, "Error attempting to add a pane", ex);
                }
            }
        } else {
            LOGGER.log(WARNING, "Hippo click event was triggered but the hippoWrapper is null");
        }
    }

    /**
     * FUTURE_WORK: Improve readability and extract some private methods
     */
    private void addPane() {
        HBox wrapper = new HBox();
        wrapper.setId(SECLECTOR);

        FlowPane hippoTags = new FlowPane();
        for (String tag : hippo.getTags()) {
            Label label = new Label(tag);
            hippoTags.getChildren().add(label);
        }
        wrapper.getChildren().add(hippoTags);

        DeleteButton deleteButton = new DeleteButton();
        deleteButton.setOnMouseClicked(
                new OnDeleteClick(hippo, hippoWrapper, contentWrapper));
        wrapper.getChildren().add(deleteButton);

        EditButton editButton = new EditButton();
        editButton.setOnMouseClicked(
                new OnEditClick(hippo, new HippoViewController()));
        wrapper.getChildren().add(editButton);

        hippoWrapper.getChildren().add(wrapper);
    }

    private void removePane(final HBox hippoPane) {
        if (hippoPane != null && hippoWrapper.getChildren() != null) {
            hippoWrapper.getChildren().remove(hippoPane);
        } else {
            LOGGER.log(WARNING, """
                                Attempted to remove a hippo pane from the hippo 
                                wrapper, but the hippo wrapper was null""");
        }
    }
}
