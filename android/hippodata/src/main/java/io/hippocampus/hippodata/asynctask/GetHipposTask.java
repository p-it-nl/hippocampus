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

import java.util.ArrayList;
import java.util.List;

import io.hippocampus.hippodata.HippoDatabase;
import io.hippocampus.hippodata.entity.Hippo;
import io.hippocampus.hippodata.predicate.MatchesNameOrTagsPredicate;
import io.hippocampus.hippodata.service.HippoService;

/**
 * Task for retrieving Hippos based on input or all (null)
 *
 * @author Patrick-4488
 * @see AsyncTask
 */
public class GetHipposTask implements Runnable {

    private final String input;

    private final OnTaskCompleted listener;
    private final HippoService hippoService;
    private final Handler handler;

    public GetHipposTask(final Handler handler, final OnTaskCompleted listener, final String input) {
        this.listener = listener;
        this.input = input;
        this.handler = handler;

        hippoService = new HippoService(HippoDatabase.getInstance().getDatabase());
    }

    @Override
    public void run() {
        List<Hippo> result;
        if (input != null && !input.isEmpty()) {
            result = hippoService.getHippos(new MatchesNameOrTagsPredicate().forNeedle(input));
        } else {
            result = hippoService.getHippos(null);
        }

        handler.post(() -> {
            listener.onTaskCompleted(result);
        });
    }
}