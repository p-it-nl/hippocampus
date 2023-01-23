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
package io.hippocampus.agent.exception;

import io.hippocampus.agent.util.ApplicationData;

/**
 * Custom exceptions including some nice messages that are user friendly
 *
 * FUTURE_WORK: Create a toaster or something and show the message to the user
 *
 * @author Patrick-4488
 */
public enum HippoCeption {

    FAILURE_TO_START_UP(
            "Hippocampus was unable to start up the application due to an "
            + "unexpected error, this might happen when the installation "
            + "is corrupt. Reinstall the application and try agian"),
    FAILURE_TO_READ_DATA(
            "Hippocampus was unable to read the data from the provided source, "
            + "the file might be corrupt or otherwise incomplete. Remove the "
            + ".cer file in the directory: " + ApplicationData.getAppDataLocation()
            + " and restart Hippocampus. It will read the data from the back-up "
            + ".hippos file if that is found."),
    FAILURE_TO_ACCESS_DATA_LOCATION(
            "Hippocampus was unable to access data files in the directory: "
            + ApplicationData.getAppDataLocation() + ". Validate that the "
            + "application has the correct access rights"),
    FAILURE_TO_RECOVER_DATA_FROM_BACKUP(
            "Hippocampus was unable to read Hippo data from the specified file. "
            + "Validate that the file is complete and has the proper format (.hippos)"),
    UNABLE_TO_TRANSITION_TO_PAGE(
            "Hippocampus was unable to move to the requested page, you can retry "
            + "the action or attempt to recreated the affected Hippo."),
    REDUNDANT_INFO_MESSAGE("A back-up line contained more information then, "
            + "expected this information has been discarded."),
    FAILURE_TO_OPEN_FILECHOOSER("An exception occurred when attempted to open a"
            + " file chooser (attempted when opening or saving a backup file"),
    FAILURE_TO_SAVE_BACKUP("An exception occurred when attempting to save the"
            + " backup file. Try agian."),
    NO_NEW_HIPPOS("It was not possible to detect any Hippos in the selected file"),
    NOT_A_HIPPOS_FILE("The selected file is either not a Hippocampus backup file"
            + " or it has been corrupted. Either way, it was not possible to "
            + "read the file"),
    FAILURE_TO_READ_FILE("An exception occurred when attempting to read the "
            + "selected file");

    private final String message;

    HippoCeption(String message) {
        this.message = message;
    }

    /**
     * Get the message
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
