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

import java.util.ArrayList;
import java.util.List;

import io.hippocampus.hippodata.dao.HippoTagDao;
import io.hippocampus.hippodata.model.Hippo;
import io.hippocampus.hippodata.model.HippoTag;
import io.hippocampus.hippodata.model.Tag;

/**
 * Service for hippo - tag coupling
 *
 * @author Patrick-4488
 */
public class HippoTagService {

    private final HippoTagDao hippoTagDao;

    public HippoTagService(final HippoTagDao hippoTagDao) {
        this.hippoTagDao = hippoTagDao;
    }

    /**
     * Saves hippo - tag couplings
     *
     * @param hippoId the id of the hippo
     * @param tagIds  the id of the tags
     */
    public void saveHippoTags(final long hippoId, final List<Long> tagIds) {
        if (hippoId > 0 && tagIds != null && !tagIds.isEmpty()) {
            HippoTag[] hippoTags = prepareHippoTags(hippoId, tagIds);

            hippoTagDao.insert(hippoTags);
        } else {
            // No action taken when input is null, its non blocking and does not impact the application
        }
    }

    /**
     * Get hippos for tag
     *
     * @param tag the tag to search with
     * @return the hippos or empty
     */
    public List<Hippo> getHipposForTag(final Tag tag) {
        if (tag != null) {
            return hippoTagDao.getHipposForTag(tag.id);
        } else {
            return new ArrayList<>();
        }
    }

    private HippoTag[] prepareHippoTags(final long hippoId, final List<Long> tagIds) {
        HippoTag[] hippoTags = new HippoTag[tagIds.size()];

        for (int i = 0; i < tagIds.size(); i++) {
            HippoTag hippoTag = new HippoTag();
            hippoTag.hippoId = hippoId;
            hippoTag.tagId = tagIds.get(i);

            hippoTags[i] = hippoTag;
        }

        return hippoTags;
    }
}
