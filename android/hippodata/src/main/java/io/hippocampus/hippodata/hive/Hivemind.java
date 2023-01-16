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
package io.hippocampus.hippodata.hive;

import io.hivemind.configuration.SynchronizerConfiguration;
import io.hivemind.constant.ConsistencyModel;
import io.hivemind.exception.HiveSynchronizationException;
import io.hivemind.exception.NotSupportedException;
import io.hivemind.synchronizer.HiveSynchronizer;

/**
 * Connects to and manages hivemind
 *
 * @author Patrick-4488
 */
public class Hivemind {

    private HiveSynchronizer synchronizer;

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
     * Start Hivemind synchronization
     */
    public void start() {
        try {
            SynchronizerConfiguration config = new SynchronizerConfiguration("http://192.168.178.108:8000", ConsistencyModel.EVENTUAL_CONSISTENCY);
            synchronizer = new HiveSynchronizer(
                    new HippoResourceProvider(), config);

            synchronizer.startSynchronization();
        } catch (NotSupportedException | HiveSynchronizationException e) {
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
