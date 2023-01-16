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
package io.hippocampus.hippodata.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.hippocampus.hippodata.entity.Hippo;
import io.hippocampus.hippodata.entity.HippoTag;
import io.hippocampus.hippodata.entity.Tag;

/**
 * Hippo tag coupling table dao
 *
 * @author Patrick-4488
 */
@Dao
public interface HippoTagDao {

    @Query("SELECT * FROM hippotag WHERE hippoId =:hippoId")
    HippoTag[] getByHippoId(final long hippoId);

    @Insert
    void insert(final HippoTag... hippoTags);

    @Query("SELECT hippo.id, hippo.hippo, hippo.creation_date FROM hippo INNER JOIN hippotag ON hippo.id = hippotag.hippoId WHERE hippotag.tagId =:tagId ORDER BY hippo.creation_date DESC")
    List<Hippo> getHipposForTag(final long tagId);

    @Query("SELECT tag.id, tag.tag, tag.creation_date FROM tag INNER JOIN hippotag ON tag.id = hippotag.tagId WHERE hippotag.hippoId =:hippoId ORDER BY tag.creation_date DESC")
    List<Tag> getTagsForHippo(final long hippoId);

    @Delete
    void delete(final HippoTag... hippoTags);
}