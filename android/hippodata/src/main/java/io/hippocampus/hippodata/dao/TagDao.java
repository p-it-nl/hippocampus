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
import androidx.room.Update;

import java.util.List;

import io.hippocampus.hippodata.model.Tag;

/**
 * Tag dao
 *
 * @author Patrick-4488
 */
@Dao
public interface TagDao {
    
    @Query("SELECT * FROM tag ORDER BY creation_date DESC")
    List<Tag> getAll();

    @Query("SELECT * FROM tag WHERE id =:tagId")
    Tag getById(long tagId);

    @Query("SELECT * FROM tag WHERE id IN (:tagIds)")
    List<Tag> getAllByIds(long[] tagIds);

    @Query("SELECT * FROM tag WHERE tag.tag LIKE :tagNeedle")
    Tag findByTag(String tagNeedle);

    @Query("SELECT * FROM tag WHERE tag.tag LIKE :tagNeedle")
    List<Tag> findByTags(String tagNeedle);

    @Insert
    long insert(Tag tag);

    @Update
    void update(Tag... tags);

    @Delete
    void delete(Tag... tags);
}