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

import io.hippocampus.agent.exception.HippoCeption;
import io.hippocampus.agent.exception.HippoDataException;
import io.hippocampus.agent.model.Hippo;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.INFO;
import static java.lang.System.Logger.Level.WARNING;

/**
 * Class that interacts with hippos
 *
 * @author Patrick-4488
 */
public class HippoData {

    private Data data;

    private static final System.Logger LOGGER = System.getLogger(HippoData.class.getName());

    public HippoData() {
        data = new Data();
    }

    /**
     * Constructor with source file
     *
     * @param source the serialized hippos
     */
    public HippoData(final ObjectInputStream source) {
        try {
            data = (Data) (source != null ? source.readObject() : null);
        } catch (IOException | ClassNotFoundException ex) {
            LOGGER.log(ERROR, "Error occurred trying to access the stored hippos", ex);
            throw new HippoDataException(HippoCeption.FAILURE_TO_READ_DATA);
        }
    }

    /**
     * Add a hippo<br>
     * - will generate and set an id if none is given (meaning new Hippo)<br>
     * - if has id none is given (meaning loaded existing Hippo);
     *
     * @param hippo the hippo to add
     * @return the list of hippos with the new hippo
     */
    public List<Hippo> addHippo(final Hippo hippo) {
        if (hippo != null) {
            Hippo existing = getHippo((Hippo h) -> h.getIdAsLong() == hippo.getIdAsLong());
            boolean isNewer = (existing == null || (hippo.getVersionAsLong() > existing.getVersionAsLong()));
            if (existing == null || isNewer) {
                determineTheId(hippo);
                determineTheVersion(hippo);
                determineTheCreationDate(hippo);

                data.hippos.add(hippo);
            } else {
                LOGGER.log(INFO, """
                    Received hippo with id {0} but the existing one is newer, 
                    not saving. Synchronization will sort this out""",
                        hippo.getId());
            }
        } else {
            LOGGER.log(WARNING, """
                    Request to save hippo was made, but no hippo was provided, 
                    this might indicate to an issue in prerequisite steps""");
        }

        return data.hippos;
    }

    /**
     * Update a hippo
     *
     * @param hippo the hippo to update
     * @return the list with hippo's including the updated hippo
     */
    public List<Hippo> updateHippo(final Hippo hippo) {
        data.hippos.stream()
                .filter(h -> h.getId() == hippo.getId())
                .forEach(h -> {
                    h.upVersion();
                    h.setHippo(hippo.getHippo());
                    h.setTags(hippo.getTags());
                });

        return data.hippos;
    }

    /**
     * Delete a hippo
     *
     * @param hippo the hippo to delete
     * @return the list of hippos without the removed one
     */
    public List<Hippo> deleteHippo(final Hippo hippo) {
        data.hippos = getHippos(h -> (hippo != null && h.getId() != hippo.getId()), null);

        return data.hippos;
    }

    /**
     * Get hippos by predicate
     *
     * @param predicate the predicate
     * @param comparator (optional) comparator, defaults to creation date
     * @return the hippos
     */
    public List<Hippo> getHippos(final Predicate<Hippo> predicate,
            final Comparator<Hippo> comparator) {
        if (predicate != null) {
            return data.hippos.stream()
                    .filter(predicate)
                    .sorted(comparator != null ? comparator : new SortByCreationDate())
                    .collect(Collectors.toList());
        } else {
            return getHippos(Criteria.ALL, comparator);
        }
    }

    /**
     * Get hippo by predicate, when multiple results are found, will return the
     * first
     *
     * @param predicate the predicate
     * @return the found hippo or null
     */
    public Hippo getHippo(final Predicate<Hippo> predicate) {
        if (predicate != null) {
            return data.hippos.stream()
                    .filter(predicate)
                    .findFirst()
                    .orElse(null);
        } else {
            return null;
        }
    }

    /**
     * Get hippos by criteria
     *
     * @param criteria the criteria
     * @param comparator (optional) comparator, defaults to creation date
     * @return the hippos
     */
    public List<Hippo> getHippos(final Criteria criteria,
            final Comparator<Hippo> comparator) {
        switch (criteria) {
            case ALL:
                return data.hippos.stream()
                        .sorted(comparator != null ? comparator : new SortByCreationDate())
                        .collect(Collectors.toList());
            // FUTURE_WORK: Add more cases like NEW, TODAY, THIS WEEK and such
            default:
                return data.hippos;
        }
    }

    /**
     * Get count
     *
     * @return amount of hippos
     */
    public int getSize() {
        return data.hippos.size();
    }

    /**
     * Saves the data to something persistent
     *
     * @param os output stream
     * @throws IOException possible IO exception
     */
    public void saveData(final FileOutputStream os) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(data);
    }

    private void determineTheId(final Hippo hippo) {
        long currentId = hippo.getIdAsLong();
        if (currentId == 0) {
            long id = data.lastId + 1L;
            hippo.setId(id);
            data.lastId = id;
        } else if (currentId > data.lastId) {
            data.lastId = currentId;
        } else {
            // Not a very special id
        }
    }

    private void determineTheCreationDate(final Hippo hippo) {
        LocalDateTime date = hippo.getCreationDate();
        if (date == null) {
            hippo.setCreationDate(LocalDateTime.now());
        } else {
            // Its alright, let it keep its date
        }
    }

    private void determineTheVersion(final Hippo hippo) {
        if (hippo.getVersion() == null || hippo.getVersionAsLong() == 0) {
            hippo.setVersion(1L);
        } else {
            // Its alright, let it keep its version
        }
    }
}
