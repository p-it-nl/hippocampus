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
package io.hippocampus.agent.service;

import io.hippocampus.agent.data.Criteria;
import io.hippocampus.agent.data.HippoData;
import io.hippocampus.agent.data.MatchesNameOrTagsPredicate;
import io.hippocampus.agent.data.backup.HippoBackupLoader;
import io.hippocampus.agent.exception.HippoCeption;
import io.hippocampus.agent.exception.HippoDataException;
import io.hippocampus.agent.exception.HippoFileReadException;
import io.hippocampus.agent.fxml.loader.HippoLoader;
import io.hippocampus.agent.model.Hippo;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import java.util.function.Predicate;

import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.INFO;
import static java.lang.System.Logger.Level.WARNING;

/**
 * All functionality for them hippos
 *
 * @author Patrick-4488
 */
public class HippoService {

    private static HippoService instance;
    private Scene scene;
    private HippoData hippoData;
    private HippoFileHelper hippoFileHelper;

    private final HippoLoader hippoLoader;
    private final HippoBackupLoader hippoBackUpLoader;

    private static final String TAG_SPLITTER = " ";
    private static final System.Logger LOGGER = System.getLogger(HippoService.class.getName());

    private HippoService() {
        LOGGER.log(INFO, "Setting up the HippoService instance");

        this.hippoLoader = new HippoLoader();
        hippoFileHelper = new HippoFileHelper(null);
        hippoBackUpLoader = new HippoBackupLoader();
        hippoData = this.hippoFileHelper.getHippoData();

        LOGGER.log(INFO, "Set up the HippoService instance");
    }

    /**
     * returns instance of hippo service
     * <p>
     * with the current hippo loader assigned, or in case non is assigned yet, a
     * new one will be created</p>
     *
     * @return singleton hippo service
     */
    public static synchronized HippoService getInstance() {
        if (instance == null) {
            instance = new HippoService();
        }

        return instance;
    }

    /**
     * Add a hippo
     *
     * @param hippo hippo to add
     * @return the hippo list including the new hippo or empty
     */
    public List<Hippo> addHippo(final Hippo hippo) {
        if (hippo != null) {
            // NOTICE: Hippos can be private information, so dont log them nor the tags
            LOGGER.log(INFO, """
                             Adding a new hippo with hippo being {0} caracter and having {1} tags""",
                    (hippo.getHippo() != null ? hippo.getHippo().length() : 0),
                    (hippo.getTags() != null ? hippo.getTags().size() : 0));

            List<Hippo> hippos = hippoData.addHippo(hippo);
            hippoFileHelper.save(hippoData);

            LOGGER.log(INFO, "Added the hippo");
            return hippos;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Add a hippo
     *
     * @param hippo the hippo
     * @param tags the tags for the hippo
     * @return the hippo list including the new hippo or empty
     */
    public List<Hippo> addHippo(final String hippo, final String tags) {
        if (isBaseInputValid(hippo)) {
            Hippo newHippo = new Hippo();
            newHippo.setHippo(hippo);
            newHippo.setTags(determineTags(tags));
            newHippo.setCreationDate(LocalDateTime.now());

            return addHippo(newHippo);
        } else {
            /**
             * FUTURE_WORK: Maybe show a user friendly message? atm I'm fine
             * with current behaviour meaning nothing happens and user is back
             * at the view screen.
             */
            LOGGER.log(WARNING, "Attempted to add a hippo but the input was invalid, "
                    + "the input being: {0}, {1}, {2}",
                    (hippo != null ? hippo.length() : "null"),
                    (tags != null ? tags.length() : "null"));
        }

        return getHippos();
    }

    /**
     * Delete a hippo
     *
     * @param hippo to delete
     * @return the hippo list without the removed one
     */
    public List<Hippo> deleteHippo(final Hippo hippo) {
        LOGGER.log(INFO, "Deleting hippo with id: {0}",
                (hippo != null ? hippo.getId() : "null"));

        List<Hippo> hippos = hippoData.deleteHippo(hippo);
        hippoFileHelper.save(hippoData);

        LOGGER.log(INFO, "Deleted hippo with id: {0}",
                (hippo != null ? hippo.getId() : "null"));

        return hippos;
    }

    /**
     * Get hippos
     *
     * FUTURE_WORK: Add parameter with criteria when there are more criteria
     *
     * @return hippos
     */
    public List<Hippo> getHippos() {
        return hippoData.getHippos(Criteria.ALL, null);
    }

    /**
     * Loads the hippos from a back-up file<br>
     * - file usually called hippo.hippos, but its just a fancy extension for a
     * csv
     *
     * @param file (optional) the selected file (menu -> load from backup). If
     * the method is called without a value for file, the app data location will
     * be checked for a back-up file
     */
    public void loadHipposFromBackUp(final File file) {
        int currentSize = hippoData.getSize();
        try {
            hippoBackUpLoader.loadHipposFromBackUp(hippoData, hippoFileHelper, file);

            int newSize = hippoData.getSize();

            if (currentSize == newSize) {
                throw new HippoDataException(HippoCeption.NO_NEW_HIPPOS);
            } else {
                updatePropagation();
            }
        } catch (HippoFileReadException ex) {
            LOGGER.log(ERROR, "Error attempting to load hippos from a the backup file"
                    + " with path: {0}",
                    (file != null ? file.getAbsolutePath() : "null"), ex);
        }
    }

    /**
     * Save the hippos - Allows manual saving
     */
    public void save() {
        LOGGER.log(INFO, "Saving hippo data");

        hippoFileHelper.save(hippoData);

        LOGGER.log(INFO, "Saved hippo data");
    }

    /**
     * Apply search
     *
     * @param needle what to search for
     */
    public void search(final String needle) {
        LOGGER.log(INFO, "Searching for hippos with the needle: {0}", needle);

        List<Hippo> results = getHipposForNeedle(needle);
        hippoLoader.clearHipposFromScreen(scene);

        if (results != null && !results.isEmpty()) {
            hippoLoader.addHipposToScreen(scene, results);
        } else {
            // No result, no need to update
        }

        LOGGER.log(INFO, """
                         Searching for hippos with the needle: {0}, resulted in 
                         finding: {1} hippos""", needle, (results != null ? results.size() : 0));
    }

    /**
     * Start propagation, meaning fill the screen with hippos<br>
     * - The given scene will also be used for updates e.g. when searching
     *
     * @param scene the scene to propagate to
     */
    public void startPropagation(final Scene scene) {
        LOGGER.log(INFO, "Starting the screen functionality (propagation)");

        this.scene = scene;

        hippoLoader.addHipposToScreen(scene, getHippos());

        LOGGER.log(INFO, "Started the screen functionality (propagation)");
    }

    /**
     * Update a hippo
     *
     * @param hippo hippo to add
     * @return the hippo list including the new hippo
     */
    public List<Hippo> updateHippo(final Hippo hippo) {
        LOGGER.log(INFO, "Updating hippo with id: {0}",
                (hippo != null ? hippo.getId() : "null"));

        List<Hippo> hippos = hippoData.updateHippo(hippo);
        hippoFileHelper.save(hippoData);

        LOGGER.log(INFO, "Updated hippo with id: {0}",
                (hippo != null ? hippo.getId() : "null"));

        return hippos;
    }

    /**
     * Update a hippo
     *
     * @param hippo the hippo to update
     * @param tags the tags to update
     * @param editHippo the hippo to update
     */
    public void updateHippo(final String hippo,
            final String tags,
            final Hippo editHippo) {
        if (isInputValid(hippo, editHippo)) {
            editHippo.setHippo(hippo);
            editHippo.setTags(List.of(tags.split(" ")));
            updateHippo(editHippo);
        } else {
            // Not saving invalid data
            LOGGER.log(WARNING, """
                                Attempted to update hippo but the input was 
                                invalid, the input being: {0}, {1}, {2}""",
                    (hippo != null ? hippo.length() : "null"),
                    (tags != null ? tags.length() : "null"),
                    (editHippo != null ? "having value" : "null"));
        }
    }

    /**
     * Updates the Hippo view<br>
     * - Will only work if currently on the hippo view screen, else it will list
     * and exception in the background and continue like nothing happened (which
     * shouldn't occur and if it does I'm fine with the exception)
     *
     * FUTURE_WORK: Maybe keep expanded expanded...
     */
    public void updatePropagation() {
        hippoLoader.clearHipposFromScreen(scene);
        hippoLoader.addHipposToScreen(scene, getHippos());
    }

    /**
     * Allow setting the hippo data to use<br>
     * Allows changing tabs, screen etc...
     *
     * @param hippoData the hippoData
     */
    public void setHippoData(final HippoData hippoData) {
        this.hippoData = hippoData;
    }

    /**
     * Change the file helper<br>
     * Allows saving (and reading) from different locations
     *
     * @param hippoFileHelper the hippo file helper for the location
     */
    public void changeFileHelper(final HippoFileHelper hippoFileHelper) {
        this.hippoFileHelper = hippoFileHelper;
        this.hippoData = this.hippoFileHelper.getHippoData();
    }

    private List<Hippo> getHipposForNeedle(final String needle) {
        if (needle == null || needle.isEmpty()) {
            return getHippos();
        } else {
            String searchNeedle = needle.toLowerCase().trim();
            return hippoData.getHippos(
                    new MatchesNameOrTagsPredicate()
                            .forNeedle(searchNeedle), null);
        }
    }

    private boolean isBaseInputValid(final String hippo) {
        return hippo != null
                && !hippo.isBlank();
    }

    private boolean isInputValid(final String hippo, final Hippo editHippo) {
        return isBaseInputValid(hippo)
                && editHippo != null;
    }

    private List<String> determineTags(final String tags) {
        return tags != null ? List.of(tags.split(TAG_SPLITTER)) : new ArrayList<>();
    }

    public class Synchronizer {

        /**
         * Get hippos
         *
         * @param predicate the predicate
         * @return hippos
         */
        public List<Hippo> getHippos(final Predicate predicate) {
            return hippoData.getHippos(predicate, null);
        }

        /**
         * Remove hippos
         *
         * @param hippos the hippos to remove
         */
        public void deleteHippos(final List<Hippo> hippos) {
            LOGGER.log(INFO, "Deleting {0} hippos",
                    (hippos != null ? hippos.size() : "null"));

            if (hippos != null) {
                for (Hippo hippo : hippos) {
                    hippoData.deleteHippo(hippo);
                }
                hippoFileHelper.save(hippoData);
                updatePropagation();
            }

            LOGGER.log(INFO, "Deleted {0} hippos",
                    (hippos != null ? hippos.size() : "null"));
        }

        /**
         * Add hippos received from synchronizer
         *
         * @param hippos the hippos to add
         */
        public void addHippos(final List<Hippo> hippos) {
            if (hippos != null) {
                LOGGER.log(INFO, "Adding {0} new hippos", hippos.size());
                for (Hippo hippo : hippos) {
                    hippoData.addHippo(hippo);
                }
                hippoFileHelper.save(hippoData);
                updatePropagation();

                LOGGER.log(INFO, "Added {0} new hippos", hippos.size());
            } else {
                LOGGER.log(WARNING, "A call to save hippos was performed without any hippos");
            }
        }
    }
}
