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
package io.hippocampus.hippodata.service;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import io.hippocampus.hippodata.dao.HippoDao;
import io.hippocampus.hippodata.database.AppDatabase;
import io.hippocampus.hippodata.entity.Hippo;

/**
 * Service for Hippos
 *
 * @author Patrick-4488
 */
public class HippoService {

    private final HippoDao hippoDao;

    public HippoService(final AppDatabase db) {
        hippoDao = db.getHippoDao();
    }

    /**
     * Save hippo
     *
     * @param hippoText the hippo text
     * @param hippoTags the tags
     */
    public void saveHippo(final String hippoText, final String hippoTags) {
        if (hippoText != null && !hippoText.isEmpty()) {
            Hippo hippo = new Hippo(hippoText, hippoTags);
            saveHippo(hippo);
        } else {
            // A hippo is required, tags are optional
        }
    }

    /**
     * Save multiple hippos
     *
     * @param hippos the hippos to save
     */
    public void saveHippos(final List<Hippo> hippos) {
        if (hippos != null) {
            for (Hippo hippo : hippos) {
                saveHippo(hippo);
            }
        }
    }

    /**
     * Update hippo
     *
     * @param hippo the hippo to update
     */
    public void saveHippo(final Hippo hippo) {
        String hippoText = hippo.getHippoText();
        if (hippo != null && hippoText != null && !hippoText.isEmpty()) {
            Hippo existing = hippoDao.getById(hippo.getId());
            boolean isNewer = (existing == null || (hippo.getVersion() > existing.getVersion()));
            if (existing == null || isNewer) {
                determineTheVersion(hippo);
                determineTheCreationDate(hippo);

                hippoDao.insert(hippo);
            } else {
                /**
                 *  Received hippo but the existing one is newer, not saving. Synchronization will sort this out
                 */
            }
        } else {
            /**
             * Request to save hippo was made, but no hippo was provided, this might indicate
             * an issue in prerequisite steps
             */
        }
    }

    /**
     * Get hippos
     *
     * @return hippos
     */
    public List<Hippo> getHippos(final Predicate<Hippo> predicate) {
        List<Hippo> hippos = getAllHippos();
        if (predicate != null) {
            hippos = hippos.stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
        }

        return hippos;
    }

    /**
     * Deletes hippos
     *
     * @param hippo hippos to delete
     */
    public void deleteHippos(final Hippo[] hippo) {
        if (hippo != null) {
            hippoDao.delete(hippo);
        }
    }

    /**
     * Deletes an hippo
     *
     * @param hippo hippo to delete
     */
    public void deleteHippo(final Hippo hippo) {
        if (hippo != null) {
            hippoDao.delete(hippo);
        }
    }

    private void determineTheCreationDate(final Hippo hippo) {
        Date date = hippo.getCreationDate();
        if (date == null) {
            hippo.setCreationDate(new Date());
        } else {
            // Its alright, let it keep its date
        }
    }

    private void determineTheVersion(final Hippo hippo) {
        if (hippo.getVersion() == 0) {
            hippo.setVersion(1L);
        } else {
            // Its alright, let it keep its version
        }
    }

    private List<Hippo> getAllHippos() {
        return hippoDao.getAll();
    }

}
