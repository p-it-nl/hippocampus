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
package io.hippocampus.agent;

import io.hippocampus.agent.exception.HippoException;
import io.hippocampus.agent.fxml.HippoAppState;
import io.hippocampus.agent.hive.HippoResourceProvider;
import io.hippocampus.agent.service.HippoService;
import io.hippocampus.agent.stage.StageHelper;
import io.hippocampus.agent.util.LogConfig;
import io.hivemind.synchronizer.HiveSynchronizer;
import io.hivemind.synchronizer.configuration.SynchronizerConfiguration;
import io.hivemind.synchronizer.constant.ConsistencyModel;
import io.hivemind.synchronizer.exception.HiveSynchronizationException;
import io.hivemind.synchronizer.exception.NotSupportedException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.INFO;

/**
 * Main class starts the application
 *
 * @author Patrick-4488
 */
public class App extends Application {

    private HiveSynchronizer synchronizer;

    private final HippoService hippoService;
    private final HippoAppState hippoAppState;

    private static final System.Logger LOGGER = System.getLogger(App.class.getName());

    public App() {
        LOGGER.log(INFO, "Building up Hippocampus");

        hippoService = HippoService.getInstance();
        hippoAppState = HippoAppState.getInstance();

        try {
            SynchronizerConfiguration config = new SynchronizerConfiguration("https://p-it.nl/hivemind", ConsistencyModel.EVENTUAL_CONSISTENCY);
            synchronizer = new HiveSynchronizer(
                    new HippoResourceProvider(hippoService), config);
        } catch (NotSupportedException ex) {
            // FUTURE_WORK: Should continue starting up but let user know its not synching
            LOGGER.log(ERROR, "Error starting sychronizer, unsupported consistency model requested", ex);
        }

        LOGGER.log(INFO, "Finished building up Hippocampus");
    }

    /**
     * Initializes Hippocampus
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        LogConfig.configure();
        LOGGER.log(INFO, "Launching Hippocampus");

        launch();

        LOGGER.log(INFO, "Exited Hippocampus");
    }

    /**
     * Starts Hippocampus<br>
     * FUTURE_WORK: setAlwaysOnTop to true and then after show to false is
     * hacky, but necessary since stage.toFront() does not work
     *
     * @param stage primary stage
     */
    @Override
    public void start(final Stage stage) {
        LOGGER.log(INFO, "Starting Hippocampus");

        try {
            StageHelper helper = new StageHelper(stage);
            Scene scene = helper.buildStartupScene();

            hippoAppState.setScene(scene);

            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.show();
            stage.setAlwaysOnTop(false);

            hippoService.startPropagation(scene);

            if (synchronizer != null) {
                synchronizer.startSynchronization();
            }
        } catch (final HippoException ex) {
            LOGGER.log(ERROR, "Error starting up", ex);
        } catch (final HiveSynchronizationException ex) {
            LOGGER.log(ERROR, "Error while synchronizing up", ex);
            stop();
        } finally {
            LOGGER.log(ERROR, "Started Hippocampus");
        }
    }

    @Override
    public void stop() {
        if (synchronizer != null) {
            synchronizer.stopSynchronization();

            LOGGER.log(INFO, "Stopping Hippocampus");
        }
    }
}
