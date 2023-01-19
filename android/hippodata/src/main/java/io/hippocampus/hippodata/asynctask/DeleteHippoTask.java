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
package io.hippocampus.hippodata.asynctask;

import android.os.AsyncTask;
import android.os.Handler;

import io.hippocampus.hippodata.HippoDatabase;
import io.hippocampus.hippodata.database.AppDatabase;
import io.hippocampus.hippodata.entity.Hippo;
import io.hippocampus.hippodata.service.HippoService;

/**
 * Task for deleting an hippo
 *
 * @author Patrick-4488
 * @see AsyncTask
 */
public class DeleteHippoTask implements Runnable {

    private final OnTaskCompleted listener;
    private final HippoService hippoService;
    private final Hippo hippo;
    private final Handler handler;

    public DeleteHippoTask(final Handler handler, final OnTaskCompleted listener, final Hippo toDelete) {
        this.listener = listener;
        this.hippo = toDelete;
        this.handler = handler;

        hippoService = new HippoService(HippoDatabase.getInstance().getDatabase());
    }

    @Override
    public void run() {
        hippoService.deleteHippo(hippo);

        handler.post(() -> {
            listener.onTaskCompleted(hippo);
        });
    }
}

