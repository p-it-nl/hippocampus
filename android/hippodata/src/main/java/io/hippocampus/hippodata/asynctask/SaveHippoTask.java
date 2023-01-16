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
package io.hippocampus.hippodata.asynctask;

import android.os.AsyncTask;

import java.util.List;

import io.hippocampus.hippodata.HippoDatabase;
import io.hippocampus.hippodata.service.HippoService;

/**
 * Task for saving a hippo
 *
 * @author Patrick-4488
 */
public class SaveHippoTask extends AsyncTask<Void, Void, Boolean> {

    private final OnTaskCompleted listener;
    private final String hippoText;
    private final List<String> hippoTags;
    private final HippoService service;

    public SaveHippoTask(final OnTaskCompleted listener, final String hippoText, final List<String> hippoTags) {
        this.listener = listener;
        this.hippoText = hippoText;
        this.hippoTags = hippoTags;

        service = new HippoService(HippoDatabase.getInstance().getDatabase());
    }

    @Override
    protected Boolean doInBackground(final Void... params) {
        boolean saved = false;
        try {
            service.saveHippo(hippoText, hippoTags);
            saved = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return saved;
    }

    @Override
    protected void onPostExecute(final Boolean saved) {
        listener.onTaskCompleted(saved);
    }
}