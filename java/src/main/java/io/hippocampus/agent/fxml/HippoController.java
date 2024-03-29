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

import io.hippocampus.agent.service.HippoService;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Base controller for hippocampus application pages (view, add..)
 *
 * @author Patrick-4488
 */
public abstract class HippoController {

    protected final HippoAppState hippoAppState;
    protected final HippoService hippoService;

    protected static final String HIPPO_HIPPO = "#hippo";
    protected static final String HIPPO_TAGS = "#tags";
    protected static final String BASE_NODE = "#app";
    protected static final int ANIMATE_TIME = 140;

    private static final String ID_HIPPO_VIEW = "hippoView";

    protected HippoController() {
        hippoService = HippoService.getInstance();
        hippoAppState = HippoAppState.getInstance();
    }

    protected void animate(final Parent in, final StackPane out, final VBox hippoView) {
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(in.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(ANIMATE_TIME), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            hippoAppState.setPreviousView(hippoView);
            out.getChildren().remove(hippoView);
            if (ID_HIPPO_VIEW.equals(in.getId())) {
                hippoService.updatePropagation();
                hippoAppState.setEditHippo(null);
            } else {
                /**
                 * At this moment no other actions required for other screens
                 * after loading
                 */
            }
        });
        timeline.play();
    }
}
