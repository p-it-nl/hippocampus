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
package io.hippocampus.agent.data.backup;

import io.hippocampus.agent.exception.HippoCeption;
import io.hippocampus.agent.exception.HippoFileReadException;
import io.hippocampus.agent.model.Hippo;
import static java.lang.System.Logger.Level.ERROR;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

/**
 * Simple reader for string to hippo from .hippos file
 *
 * @author Patrick-4488
 */
public class SimpleHippoReader {

    private static final int ID = 0;
    private static final int VERSION = 1;
    private static final int HIPPO = 2;
    private static final int TAGS = 3;
    private static final int DATE = 4;

    private static final String SEPARATOR = ";";
    private static final String SEPARATOR_TAGS = ",";

    private static final System.Logger LOGGER = System.getLogger(SimpleHippoReader.class.getName());

    private SimpleHippoReader() {
    }

    /**
     * Attempt to convert the line to Hippo
     *
     * @param line the line
     * @return Hippo or null in case of failure
     */
    public static Hippo read(final String line) {
        Hippo hippo = null;
        if (line != null) {
            String[] vals = line.split(SEPARATOR);

            if (vals != null && vals.length > 1) {
                try {
                    hippo = processVals(vals);
                } catch (IllegalCallerException | NumberFormatException ex) {
                    LOGGER.log(ERROR, "Exception occurred when reading a line from the "
                            + "backup file", ex);
                    throw new HippoFileReadException(HippoCeption.NOT_A_HIPPOS_FILE);
                }
            } else {
                /**
                 * The bare minimum to make sense of a line is an id and hippo,
                 * so two values
                 */
            }
        } else {
            // No line, no hippo
        }

        return ifValid(hippo);
    }

    private static Hippo processVals(final String[] vals) {
        Hippo hippo = new Hippo();

        for (int i = 0; i < vals.length; i++) {
            switch (i) {
                case ID:
                    hippo.setId(Long.parseLong(vals[i]));
                    break;
                case VERSION:
                    hippo.setVersion(Long.parseLong(vals[i]));
                    break;
                case HIPPO:
                    hippo.setHippo(vals[i]);
                    break;
                case TAGS:
                    hippo.setTags(
                            List.of(vals[i].split(SEPARATOR_TAGS))
                    );
                    break;
                case DATE:
                    LocalDateTime date = LocalDateTime
                            .ofInstant(Instant.ofEpochMilli(
                                    Long.parseLong(vals[i])),
                                    TimeZone.getDefault().toZoneId());
                    hippo.setCreationDate(date);
                    break;
                default:
                    throw new IllegalCallerException(
                            HippoCeption.REDUNDANT_INFO_MESSAGE.getMessage());
            }
        }

        return hippo;
    }

    private static Hippo ifValid(final Hippo hippo) {
        if (hippo != null
                && hippo.getHippo() != null
                && hippo.getTags() != null) {
            return hippo;
        } else {
            // Lets ignore it, does not seem to be a hippo we can use
            return null;
        }
    }
}
