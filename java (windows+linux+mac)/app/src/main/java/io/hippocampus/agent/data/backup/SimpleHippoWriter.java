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

import io.hippocampus.agent.model.Hippo;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Simple writer for hippo to string for use in .hippos file
 *
 * @author Patrick-4488
 */
public class SimpleHippoWriter {

    private static final String SEPARATOR = ";";
    private static final String END_LINE = "\n";
    private static final String COMMA = ",";

    private SimpleHippoWriter() {
    }

    /**
     * Convert the Hippo to a String
     *
     * @param hippo the hippo
     * @return the hippo as string
     */
    public static String write(final Hippo hippo) {
        StringBuilder sb = new StringBuilder();
        if (hippo != null) {
            sb.append(hippo.getId());
            sb.append(SEPARATOR);
            sb.append(hippo.getVersion());
            sb.append(SEPARATOR);
            sb.append(hippo.getHippo());
            sb.append(SEPARATOR);
            setTags(sb, hippo);
            sb.append(SEPARATOR);
            sb.append(getMillis(hippo.getCreationDate()));
            sb.append(END_LINE);
        }

        return sb.toString();
    }

    private static String getMillis(final LocalDateTime creationDate) {
        if (creationDate != null) {
            long millis = creationDate
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli();
            return String.valueOf(millis);
        } else {
            return new String();
        }
    }

    private static void setTags(final StringBuilder sb, final Hippo hippo) {
        List<String> tags = hippo.getTags();
        for (int i = 0; i < tags.size(); i++) {
            if (i != 0) {
                sb.append(COMMA);
            } else {
                // Not need for a comma
            }
            sb.append(tags.get(i));
        }
    }
}
