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
package io.hippocampus.hippodata.service;

import java.util.Date;
import java.util.List;

import io.hippocampus.hippodata.dao.HippoDao;
import io.hippocampus.hippodata.database.AppDatabase;
import io.hippocampus.hippodata.model.Hippo;
import io.hippocampus.hippodata.model.Tag;

/**
 * Service for Hippos
 *
 * @author Patrick-4488
 */
public class HippoService {

    private final HippoDao hippoDao;
    private final TagService tagService;
    private final HippoTagService hippoTagService;

    public HippoService(final AppDatabase db) {
        hippoDao = db.getHippoDao();
        hippoTagService = new HippoTagService(db.getHippoTagDao());
        tagService = new TagService(db.getTagDao());
    }

    /**
     * Save hippo and implicitly tags
     *
     * @param hippoText the hippo text
     * @param hippoTags the tags
     */
    public void saveHippo(final String hippoText, final List<String> hippoTags) {
        if (hippoText != null && !hippoText.isEmpty()) {
            Hippo hippo = prepareHippo(hippoText);
            saveHippo(hippo);

            List<Long> tagIds = tagService.saveTags(hippoTags);
            hippoTagService.saveHippoTags(hippo.id, tagIds);
        } else {
            // A hippo is required, tags are optional
        }
    }

    /**
     * Get all hippos
     * FUTURE_WORK: add pagination
     *
     * @return hippos
     */
    public List<Hippo> getAllHippos() {
        return hippoDao.getAll();
    }

    private Hippo prepareHippo(final String hippoText) {
        Hippo hippo = new Hippo();
        hippo.hippo = hippoText;
        hippo.creationDate = new Date();

        return hippo;
    }

    private void saveHippo(final Hippo hippo) {
        long id = hippoDao.insert(hippo);
        hippo.id = id;
    }
}
