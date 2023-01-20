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

import io.hippocampus.agent.exception.HippoCeption;
import io.hippocampus.agent.exception.HippoDataException;
import io.hippocampus.agent.fxml.HippoViewController;
import io.hippocampus.agent.model.Hippo;
import java.io.IOException;
import javafx.event.Event;
import javafx.event.EventHandler;

import static java.lang.System.Logger.Level.ERROR;

/**
 * The edit a hippo event
 *
 * @author Patrick-4488
 */
public class OnEditClick implements EventHandler {

    private final Hippo hippo;
    private final HippoViewController hippoViewController;

    private static final System.Logger LOGGER = System.getLogger(OnEditClick.class.getName());

    public OnEditClick(final Hippo hippo, final HippoViewController hippoViewController) {
        this.hippo = hippo;
        this.hippoViewController = hippoViewController;
    }

    @Override
    public void handle(final Event e) {
        try {
            hippoViewController.setEditHippo(hippo);
            hippoViewController.gotoToHippoAdd(e);
        } catch (IOException ex) {
            LOGGER.log(ERROR, "Exception occurred handling an edit event", ex);
            throw new HippoDataException(HippoCeption.UNABLE_TO_TRANSITION_TO_PAGE);
        }
    }
}
