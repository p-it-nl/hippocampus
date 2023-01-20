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
package io.hippocampus.agent.util;

/**
 * Application data util helps solving OS differences in determining the
 * location the application should store its data
 *
 * @author Patrick-4488
 */
public class ApplicationData {

    private static final String OS_NAME = "os.name";
    private static final String APP_DATA = "AppData";
    private static final String USER_HOME = "user.home";

    private static final String NEEDLE_WINDOWS = "win";
    private static final String NEEDLE_MAC = "mac";

    private static final String APP_DIR_PATH_MAC = "/Library/Application Support/";
    private static final String APP_DIR_PATH_LINUX = "/.";
    private static final String APP_DIR_PATH_WINDOWS = "/";

    private static final String APP_DIR_NAME = "Hippocampus/";

    public static String getAppDataLocation() {
        StringBuilder dirBuilder = new StringBuilder();
        String os = System.getProperty(OS_NAME).toLowerCase();

        if (os.contains(NEEDLE_WINDOWS)) {
            dirBuilder.append(System.getenv(APP_DATA));
            dirBuilder.append(APP_DIR_PATH_WINDOWS);
        } else {
            dirBuilder.append(System.getProperty(USER_HOME));

            if (os.contains(NEEDLE_MAC)) {
                dirBuilder.append(APP_DIR_PATH_MAC);
            } else {
                dirBuilder.append(APP_DIR_PATH_LINUX);
            }
        }
        dirBuilder.append(APP_DIR_NAME);

        return dirBuilder.toString();
    }
}
