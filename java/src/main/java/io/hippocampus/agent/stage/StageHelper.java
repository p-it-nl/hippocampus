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
package io.hippocampus.agent.stage;

import io.hippocampus.agent.exception.HippoCeption;
import io.hippocampus.agent.exception.HippoRuntimeException;
import java.io.IOException;
import static java.lang.System.Logger.Level.ERROR;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Stage helper allows adjusting the stage
 *
 * @author Patrick-4488
 */
public class StageHelper {

    private final Stage stage;

    private static final String URL_HIPPO_VIEW = "fxml/hippocampus-view.fxml";
    private static final String URL_HIPPO_STYLE = "css/style.css";
    private static final String APP_TITLE = "Hippocampus";
    private static final String APP_ICON = "hippocampus.png";

    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 600;

    private static final System.Logger LOGGER = System.getLogger(StageHelper.class.getName());

    public StageHelper(final Stage stage) {
        this.stage = stage;
        setTitle();
        setIcon();
    }

    /**
     * Build the (starting) scene including style sheet
     *
     * @return the scene
     */
    public Scene buildStartupScene() {
        Scene scene = new Scene(getStartupViewContent(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
        scene.getStylesheets().add(URL_HIPPO_STYLE);

        return scene;
    }

    private void setTitle() {
        if (stage != null) {
            stage.setTitle(APP_TITLE);
        } else {
            // No stage, no title, but lets try continuing setting things up
        }
    }

    /**
     * Retrieves the file from the classpath resources assets folder and set it
     * as the application icon
     *
     * FUTURE_WORK: JavaFX seems to only support bitmap and no vector files for
     * icons (yet), might be due to limitations. Might add vector support
     * myself.
     */
    private void setIcon() {
        if (stage != null && stage.getIcons() != null) {
            Image appIcon = new Image(getClass().getClassLoader()
                    .getResourceAsStream(APP_ICON));
            stage.getIcons().add(appIcon);
        } else {
            /**
             * No stage or stage icons, means we cannot set our icon, but lets
             * try continuing setting things up
             */
        }
    }

    private StackPane getStartupViewContent() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(URL_HIPPO_VIEW));
        try {
            return loader.<StackPane>load();
        } catch (IOException | ExceptionInInitializerError | NoClassDefFoundError ex) {
            LOGGER.log(ERROR, "An exception occurred trying to read "
                    + "`" + URL_HIPPO_VIEW + "` from the classpath.", ex);
            throw new HippoRuntimeException(HippoCeption.FAILURE_TO_START_UP);
        }
    }
}
