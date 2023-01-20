/**
 * Copyright (c) p-it
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.hippocampus.hippodata.hive;

import android.util.Log;

import io.hippocampus.hippodata.asynctask.SynchronizeWithUITask;
import io.hivemind.synchronizer.HiveSynchronizer;
import io.hivemind.synchronizer.configuration.SynchronizerConfiguration;
import io.hivemind.synchronizer.constant.ConsistencyModel;
import io.hivemind.synchronizer.exception.HiveSynchronizationException;
import io.hivemind.synchronizer.exception.NotSupportedException;

/**
 * Connects to and manages hivemind
 *
 * @author Patrick-4488
 */
public class Hivemind {

    private HiveSynchronizer synchronizer;
    private SynchronizeWithUITask uiTask;

    private static Hivemind instance;

    private Hivemind() {
    }

    /**
     * @return singleton instance of hippo database
     */
    public static synchronized Hivemind getInstance() {
        if (instance == null) {
            instance = new Hivemind();
        }

        return instance;
    }

    /**
     * Update the UI with changes
     */
    public void propagateUIUpdate() {
        if (uiTask != null) {
            uiTask.run();
        } else {
            // need to know what to update
        }
    }

    /**
     * Set the UI task to propagate updates to
     *
     * @param task
     */
    public void setUiTask(final SynchronizeWithUITask task) {
        uiTask = task;
    }

    /**
     * Start Hivemind synchronization
     */
    public void start() {
        if (synchronizer == null) {
            try {
                SynchronizerConfiguration config = new SynchronizerConfiguration("https://p-it.nl/hivemind", ConsistencyModel.EVENTUAL_CONSISTENCY);
                config.setPeriodBetweenRequests(10);
                synchronizer = new HiveSynchronizer(
                        new HippoResourceProvider(this), config);
            } catch (NotSupportedException e) {
                e.printStackTrace();
            }
        }

        try {
            synchronizer.startSynchronization();
        } catch (HiveSynchronizationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stop Hivemind synchronization
     */
    public void stop() {
        synchronizer.stopSynchronization();
    }
}
