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
package io.hippocampus.hippodata.predicate;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import io.hippocampus.hippodata.entity.Hippo;

/**
 * Predicate for finding hippos matching id and (optionally) version.
 *
 * @author Patrick-4488
 */
public class IsNotInMapPredicate implements Predicate<Hippo> {

    private final Map<Long, Long> map;

    public IsNotInMapPredicate() {
        map = new HashMap<>();
    }

    /**
     * The hive resources to form the list to search in with
     *
     * @param id      the id to add to the map
     * @param version the version to add to the map
     * @return this fluent
     */
    public IsNotInMapPredicate addToMap(final Long id, final Long version) {
        map.put(id, version);

        return this;
    }

    /**
     * The test
     *
     * @param hippo the hippo to test
     * @return whether matches given criteria
     */
    @Override
    public boolean test(final Hippo hippo) {
        return !map.containsKey(hippo.getIdAsLong());
    }
}