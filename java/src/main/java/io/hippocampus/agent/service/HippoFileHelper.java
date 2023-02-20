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

import io.hippocampus.agent.data.HippoData;
import io.hippocampus.agent.exception.HippoCeption;
import io.hippocampus.agent.exception.HippoDataException;
import io.hippocampus.agent.util.ApplicationData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.INFO;

/**
 * Helper for all to do with hippo related files
 *
 * @author Patrick-4488
 */
public class HippoFileHelper {

    private final String location;

    private static final String DATA_FILE_NAME = "hippos.ser";

    private static final System.Logger LOGGER = System.getLogger(HippoFileHelper.class.getName());

    /**
     * Create and instance of a hippo file helper for a location<br>
     * If no location is given (null) then the default location is used,
     * being:<br>
     * ApplicationDataUtil.getAppDataLocation()
     *
     * This constructor allows saving and maintaining copies and versions of
     * hippos to multiple locations
     *
     * @param forLocation the location to save
     */
    public HippoFileHelper(final String forLocation) {
        this.location = (forLocation != null
                ? forLocation : ApplicationData.getAppDataLocation());
    }

    /**
     * Get the stored hippo data
     *
     * @return the hippo data
     */
    public HippoData getHippoData() {
        File file = getDataFile();
        if (file.exists()) {
            return fromDataFile(file);
        } else {
            return new HippoData();
        }
    }

    /**
     * Saves the hippo data
     *
     * FUTURE_WORK: encrypt the saved file? consider: Diffie-Helmann
     *
     * @param hippoData the data to save
     */
    public void save(final HippoData hippoData) {
        if (hippoData != null) {
            try (final FileOutputStream fos = new FileOutputStream(getDataFile())) {
                hippoData.saveData(fos);
            } catch (IOException ex) {
                LOGGER.log(ERROR, "Error occurred trying to write the hippo data file "
                        + "to {0}", location, ex);
                throw new HippoDataException(HippoCeption.FAILURE_TO_ACCESS_DATA_LOCATION);
            }
        } else {
            LOGGER.log(ERROR, "Received call to save hippo data, but the given hippo "
                    + "data was null");
        }
    }

    /**
     * Delete the saved hippo data
     */
    public void delete() {
        boolean isFileDeleted = false;
        boolean isDirDeleted = false;
        File file = getDataFile();
        File directory = new File(location);
        if (file.exists()) {
            isFileDeleted = file.delete();//NOSONAR, wanting the feedback here
        } else {
            // Cannot delete something that doesn't exist
        }

        if (isFileDeleted && directory.list().length == 0) {
            isDirDeleted = directory.delete();//NOSONAR, wanting the feedback here
        } else {
            // There are still files in the directory, lets keep it
        }

        if (isFileDeleted) {
            LOGGER.log(INFO, "File {0} is deleted", (isDirDeleted ? "and directory" : ""));
        }
    }

    private File getDataFile() {
        String appDirPath = location;

        File file = new File(appDirPath);
        file.mkdirs();

        return new File(appDirPath + DATA_FILE_NAME);
    }

    private HippoData fromDataFile(final File file) {
        try (final FileInputStream fos = new FileInputStream(file);  ObjectInputStream ois = new ObjectInputStream(fos);) {
            return new HippoData(ois);
        } catch (IOException ex) {
            LOGGER.log(ERROR, """
                              Error occurred trying to access and deserialize the 
                              hippo data file from: {0}""",
                    location, ex);
            throw new HippoDataException(HippoCeption.FAILURE_TO_ACCESS_DATA_LOCATION);
        } catch (HippoDataException hippoEx) {
            throw hippoEx;
        }
    }
}
