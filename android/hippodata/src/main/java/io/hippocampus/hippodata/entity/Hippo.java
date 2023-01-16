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
package io.hippocampus.hippodata.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

/**
 * Hippo entity
 * 1 - * -> relation with tags via HippoTag
 *
 * @see HippoTag
 */
@Entity
public class Hippo {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "hippo")
    public String hippo;

    @ColumnInfo(name = "creation_date")
    public Date creationDate;

    @Ignore
    public List<Tag> tags;
}