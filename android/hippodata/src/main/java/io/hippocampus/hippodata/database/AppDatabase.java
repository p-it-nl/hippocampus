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
package io.hippocampus.hippodata.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import io.hippocampus.hippodata.converter.DateConverter;
import io.hippocampus.hippodata.dao.HippoDao;
import io.hippocampus.hippodata.dao.HippoTagDao;
import io.hippocampus.hippodata.dao.TagDao;
import io.hippocampus.hippodata.entity.Hippo;
import io.hippocampus.hippodata.entity.HippoTag;
import io.hippocampus.hippodata.entity.Tag;

/**
 * Application database
 *
 * @author Patrick-4488
 */
@Database(entities = {Hippo.class, Tag.class, HippoTag.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    /**
     * @return the HippoDao
     */
    public abstract HippoDao getHippoDao();

    /**
     * @return the TagDao
     */
    public abstract TagDao getTagDao();

    /**
     * @return the HippoTagDao
     */
    public abstract HippoTagDao getHippoTagDao();

}
