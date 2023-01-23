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

import java.io.Serializable;
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
public class Hippo {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "version")
    private long version;

    @ColumnInfo(name = "hippo")
    private String hippoText;

    @ColumnInfo(name = "tags")
    private String tags;

    @ColumnInfo(name = "creation_date")
    private Date creationDate;

    private static final String SPLIT = " ";

    public Hippo() {
    }

    public Hippo(final String hippoText, final String hippoTags) {
        this.hippoText = hippoText;
        this.tags = hippoTags;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id  the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the version
     */
    public long getVersion() {
        return version;
    }

    /**
     * @param version the version
     */
    public void setVersion(long version) {
        this.version = version;
    }

    /**
     * @return the hippo
     */
    public String getHippoText() {
        return hippoText;
    }

    /**
     * @param hippoText the hippo to set
     */
    public void setHippoText(final String hippoText) {
        this.hippoText = hippoText;
    }

    /**
     * @return the tags as string
     */
    public String getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(final String tags) {
        this.tags = tags;
    }

    /**
     * @return the creation date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the date to set
     */
    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
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
    public List<String> getTagsAsParts() {
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
                + "hippoText: " + hippoText + "\n"
                + "tags: " + (tags != null ? tags : "") + "\n"
                + "creationDate: "
                + (creationDate != null ? creationDate.toString() : "");
    }
}