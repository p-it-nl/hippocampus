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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.hivemind.synchronizer.HiveResource;

/**
 * The Hippo model
 *
 * @author Patrick-4488
 */
public class Hippo extends HiveResource {

    private long id;
    private long version;
    private String hippo;
    private List<String> tags;
    private LocalDateTime creationDate;

    public Hippo() {
    }

    public Hippo(
            final long id,
            final String hippo,
            final LocalDateTime creationDate,
            final List<String> tags) {
        this.id = id;
        this.hippo = hippo;
        this.creationDate = creationDate;
        this.tags = tags;
    }

    @Override
    public Object getId() {
        return id;
    }

    public long getIdAsLong() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public void setVersion(final long version) {
        this.version = version;
    }

    public void upVersion() {
        version += 1;
    }

    @Override
    public Object getVersion() {
        return version;
    }

    public long getVersionAsLong() {
        return version;
    }

    public String getHippo() {
        return hippo;
    }

    public void setHippo(final String hippo) {
        this.hippo = hippo;
    }

    public List<String> getTags() {
        return (tags != null ? tags : new ArrayList<>());
    }

    public void setTags(final List<String> tags) {
        this.tags = tags;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final LocalDateTime creationDate) {
        this.creationDate = creationDate;
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
