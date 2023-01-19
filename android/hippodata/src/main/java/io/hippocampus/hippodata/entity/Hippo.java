/**
 * Copyright (c) p-it
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.hippocampus.hippodata.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.hivemind.synchronizer.HiveResource;

/**
 * Hippo entity
 * 1 - * -> relation with tags via HippoTag
 *
 * @see HiveResource
 */
@Entity
public class Hippo extends HiveResource {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "version")
    public long version;

    @ColumnInfo(name = "hippo")
    public String hippo;

    @ColumnInfo(name = "tags")
    public String tags;

    @ColumnInfo(name = "creation_date")
    public Date creationDate;

    private static final String SPLIT = " ";

    public Hippo() {
    }

    public Hippo(final String hippoText, final String hippoTags) {
        this.hippo = hippoText;
        this.tags = hippoTags;
    }

    @Override
    public Object getId() {
        return id;
    }

    @Override
    public Object getVersion() {
        return version;
    }

    /**
     * @return the id as long
     */
    public long getIdAsLong() {
        return id;
    }

    /**
     * @return the version as long
     */
    public long getVersionAsLong() {
        return version;
    }

    /**
     * up the version by 1
     */
    public void upVersion() {
        version += 1;
    }

    /**
     * @return tags separated
     */
    public List<String> getTags() {
        if (tags != null) {
            return Arrays.asList(tags.split(SPLIT));
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public String toString() {
        return "id: " + id + "\n"
                + "version: " + version + "\n"
                + "hippo: " + hippo + "\n"
                + "tags: " + (tags != null ? tags.toString() : "") + "\n"
                + "creationDate: "
                + (creationDate != null ? creationDate.toString() : "");
    }
}