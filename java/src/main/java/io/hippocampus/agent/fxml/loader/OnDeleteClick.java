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
import io.hippocampus.agent.service.HippoService;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

/**
 * The delete a hippo event
 *
 * @author Patrick-4488
 */
public class OnDeleteClick implements EventHandler {

    private final Hippo hippo;
    private final VBox hippoWrapper;
    private final VBox contentWrapper;
    private final HippoService hippoService;

    public OnDeleteClick(
            final Hippo hippo,
            final VBox hippoWrapper,
            final VBox contentWrapper) {
        this.hippo = hippo;
        this.hippoWrapper = hippoWrapper;
        this.contentWrapper = contentWrapper;

        this.hippoService = HippoService.getInstance();
    }

    @Override
    public void handle(final Event e) {
        this.hippoService.deleteHippo(hippo);

        if (contentWrapper.getChildren() != null) {
            contentWrapper.getChildren().remove(hippoWrapper);
        } else {
            // Nothing to remove if there are no children
        }
    }
}
