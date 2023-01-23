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
package io.hippocampus.agent.data;

import io.hippocampus.agent.model.Hippo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The actual hippos
 *
 * @author Patrick-4488
 */
class Data implements Serializable {

    private long lastId;
    private List<Hippo> hippos;//NOSONAR, is always arraylist which is serializable

    public Data() {
        this.hippos = new ArrayList<>();
    }

    /**
     * @return the last id
     */
    public long getLastId() {
        return lastId;
    }

    /**
     * @param lastId the last id
     */
    public void setLastId(long lastId) {
        this.lastId = lastId;
    }

    /**
     * @return the hippos
     */
    public List<Hippo> getHippos() {
        return hippos;
    }

    /**
     * @param hippos add hippos
     */
    public void addHippos(final List<Hippo> hippos) {
        this.hippos.addAll(hippos);
    }

    /**
     * @param hippos add hippos
     */
    public void addHippo(final Hippo hippo) {
        this.hippos.add(hippo);
    }

    /**
     * @param hippos the hippos to set
     */
    public void setHippos(final List<Hippo> hippos) {
        this.hippos = hippos;
    }
}
