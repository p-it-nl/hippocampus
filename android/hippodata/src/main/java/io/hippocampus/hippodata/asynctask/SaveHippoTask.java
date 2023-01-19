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

import android.os.Handler;

import io.hippocampus.hippodata.HippoDatabase;
import io.hippocampus.hippodata.service.HippoService;

/**
 * Task for saving a hippo
 *
 * @author Patrick-4488
 */
public class SaveHippoTask implements Runnable {

    private final OnTaskCompleted listener;
    private final String hippoText;
    private final String hippoTags;
    private final HippoService service;
    private final Handler handler;

    public SaveHippoTask(final Handler handler, final OnTaskCompleted listener, final String hippoText, final String hippoTags) {
        this.listener = listener;
        this.hippoText = hippoText;
        this.hippoTags = hippoTags;
        this.handler = handler;

        service = new HippoService(HippoDatabase.getInstance().getDatabase());
    }

    @Override
    public void run() {
        service.saveHippo(hippoText, hippoTags);

        handler.post(() -> {
            listener.onTaskCompleted(true);
        });
    }
}