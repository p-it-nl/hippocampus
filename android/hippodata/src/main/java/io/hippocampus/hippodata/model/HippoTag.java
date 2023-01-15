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
package io.hippocampus.hippodata.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

/**
 * Hippo tag coupling table
 */
@Entity(primaryKeys = {"hippoId", "tagId"},
        foreignKeys = {
                @ForeignKey(entity = Hippo.class,
                        parentColumns = "id",
                        childColumns = "hippoId"),
                @ForeignKey(entity = Tag.class,
                        parentColumns = "id",
                        childColumns = "tagId")
        },
        indices = {@Index("tagId"),
                @Index(value = {"hippoId", "tagId"})})
public class HippoTag {

    @ColumnInfo(name = "hippoId")
    public long hippoId;

    @ColumnInfo(name = "tagId")
    public long tagId;
}
