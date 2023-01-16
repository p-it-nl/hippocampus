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

import io.hippocampus.hippodata.entity.Hippo;

/**
 * Hippo dao
 *
 * @author Patrick-4488
 */
@Dao
public interface HippoDao {

    @Query("SELECT * FROM hippo ORDER BY creation_date DESC")
    List<Hippo> getAll();

    @Query("SELECT * FROM hippo WHERE id =:hippoId")
    Hippo getById(final long hippoId);

    @Query("SELECT * FROM hippo WHERE id IN (:hippoIds)")
    List<Hippo> getAllByIds(final long[] hippoIds);

    @Query("SELECT * FROM hippo WHERE hippo LIKE :hippoNeedle")
    List<Hippo> findByHippo(final String hippoNeedle);

    @Insert
    long insert(Hippo hippo);

    @Update
    void update(Hippo... hippos);

    @Delete
    void delete(Hippo... hippos);
}
