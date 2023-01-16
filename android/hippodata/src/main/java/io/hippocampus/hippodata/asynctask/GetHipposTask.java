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
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.hippocampus.hippodata.HippoDatabase;
import io.hippocampus.hippodata.database.AppDatabase;
import io.hippocampus.hippodata.entity.Hippo;
import io.hippocampus.hippodata.entity.Tag;
import io.hippocampus.hippodata.service.HippoService;
import io.hippocampus.hippodata.service.HippoTagService;
import io.hippocampus.hippodata.service.TagService;

/**
 * Task for retrieving Hippos based on tags or all (null)
 *
 * @author Patrick-4488
 * @see AsyncTask
 */
public class GetHipposTask extends AsyncTask<String, Void, List<Hippo>> {

    private final OnTaskCompleted listener;
    private final TagService tagService;
    private final HippoTagService hippoTagService;
    private final HippoService hippoService;

    public GetHipposTask(final OnTaskCompleted listener) {
        this.listener = listener;

        AppDatabase db = HippoDatabase.getInstance().getDatabase();
        tagService = new TagService(db.getTagDao());
        hippoTagService = new HippoTagService(db.getHippoTagDao());
        hippoService = new HippoService(db);
    }

    @Override
    protected List<Hippo> doInBackground(final String... tagsTexts) {
        if (tagsTexts == null || tagsTexts.length == 0) {
            Log.i("getting all", "getting all");
            return hippoService.getAllHippos();
        } else {
            Log.i("getting for tags", "getting for tags");
            return getHippos(tagsTexts);
        }
    }

    private List<Hippo> getHippos(final String[] tagsTexts) {
        Map<Long, Hippo> hippos = new HashMap<>();

        for (int i = 0; i < tagsTexts.length; i++) {
            String tagText = tagsTexts[i];
            List<Tag> tags = tagService.getTagsByText(tagText);
            if (!tags.isEmpty()) {
                for (Tag tag : tags) {
                    appendUniqueHipposForTag(tag, hippos);
                }
            } else {
                // No tag found, so not required to find hippos for the tag text
            }
        }

        return getUniqueHipposFromMap(hippos);
    }

    private void appendUniqueHipposForTag(final Tag tag, final Map<Long, Hippo> hippos) {
        List<Hippo> foundHippos = hippoTagService.getHipposForTag(tag);
        for (Hippo foundHippo : foundHippos) {
            if (!hippos.containsKey(foundHippo.id)) {
                hippos.put(foundHippo.id, foundHippo);
            }
        }
    }

    private List<Hippo> getUniqueHipposFromMap(Map<Long, Hippo> hippos) {
        List<Hippo> uniqueHippos = new ArrayList<>();
        for (Map.Entry<Long, Hippo> entry : hippos.entrySet()) {
            uniqueHippos.add(entry.getValue());
        }

        return uniqueHippos;
    }

    @Override
    protected void onPostExecute(List<Hippo> hippos) {
        listener.onTaskCompleted(hippos);
    }
}