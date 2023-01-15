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
package io.hippocampus.hippodata;

import android.content.Context;

import androidx.room.Room;

import io.hippocampus.hippodata.database.AppDatabase;

/**
 * Hippo database facade implementation of which there is only one.
 * Used for preparing and retrieving application database
 *
 * @author Patrick-4488
 */
public class HippoDatabase {

    private AppDatabase database;

    private static HippoDatabase instance;

    private static final String DATABASE_NAME = "hippo-database";

    private HippoDatabase() {
    }

    /**
     * @return singleton instance of hippo database
     */
    public static synchronized HippoDatabase getInstance() {
        if (instance == null) {
            instance = new HippoDatabase();
        }
        return instance;
    }

    /**
     * Get the database
     *
     * @return database or null
     */
    public AppDatabase getDatabase() {
        return database;
    }

    /**
     * Prepare the database
     *
     * @param applicationContext application context
     */
    public void prepareDatabase(final Context applicationContext) {
        if (database == null) {
            database = Room.databaseBuilder(applicationContext, AppDatabase.class, DATABASE_NAME).build();
        } else {
            // No action required, database already initialized
        }
    }
}
