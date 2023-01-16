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
import java.util.Date;
import java.util.List;

import io.hippocampus.hippodata.dao.TagDao;
import io.hippocampus.hippodata.entity.Tag;
import io.hippocampus.hippodata.util.DataUtil;

/**
 * Service for tags
 *
 * @author Patrick-4488
 */
public class TagService {

    private final TagDao tagDao;

    public TagService(final TagDao tagDao) {
        this.tagDao = tagDao;
    }

    /**
     * Retrieves tags based on text
     *
     * @param tagText the tag text
     * @return the tags
     */
    public List<Tag> getTagsByText(final String tagText) {
        return tagDao.findByTags(DataUtil.needleToLikeQuery(tagText));
    }

    /**
     * Saves tags if not exist else adds the id to the output
     *
     * @param hippoTags tags
     * @return list with ids of affected tags
     */
    public List<Long> saveTags(final List<String> hippoTags) {
        List<Long> ids = new ArrayList<>();

        if (hippoTags != null && !hippoTags.isEmpty()) {
            for (String tagText : hippoTags) {
                Tag tag = tagDao.findByTag(DataUtil.needleToLikeQuery(tagText));
                if (tag == null) {
                    tag = prepareTag(tagText);
                    saveTag(tag);
                } else {
                    // Tag exits, no need to save
                }

                ids.add(tag.id);
            }
        }

        return ids;
    }

    private Tag prepareTag(final String tagText) {
        Tag tag = new Tag();
        tag.tag = tagText;
        tag.creationDate = new Date();

        return tag;
    }

    private void saveTag(final Tag tag) {
        long id = tagDao.insert(tag);
        tag.id = id;
    }
}
