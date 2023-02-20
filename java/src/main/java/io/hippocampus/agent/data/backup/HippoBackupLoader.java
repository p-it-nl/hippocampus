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

import io.hippocampus.agent.data.HippoData;
import io.hippocampus.agent.exception.HippoCeption;
import io.hippocampus.agent.exception.HippoDataException;
import io.hippocampus.agent.model.Hippo;
import io.hippocampus.agent.service.HippoFileHelper;
import io.hippocampus.agent.util.ApplicationData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.INFO;

/**
 * Back-up loader can be used to read Hippo's from a data file. This is useful
 * when recovering from problems
 *
 * @author Patrick-4488
 */
public class HippoBackupLoader {

    private static final String DATA_BACKUP_FILE_NAME = "hippo.hippos";
    private static final System.Logger LOGGER = System.getLogger(HippoBackupLoader.class.getName());

    /**
     * Loads the hippos from a back-up file into the hippo data object<br>
     * - file usually called hippo.hippos, but its just a fancy extension for a
     * csv
     *
     * @param hippoData the hippo data
     * @param hippoFileHelper the file helper
     * @param file (optional) the selected file (menu -> load from backup). If
     * the method is called without a value for file, the app data location will
     * be checked for a back-up file
     */
    public void loadHipposFromBackUp(final HippoData hippoData,
            final HippoFileHelper hippoFileHelper,
            final File file) {
        LOGGER.log(INFO, "Loading hippos from back up");

        if (hippoData != null) {
            File backupFile = determineBackupFile(file);
            if (backupFile.exists()) {
                processFile(backupFile, hippoData);
            } else {
                throw new HippoDataException(HippoCeption.NOT_A_HIPPOS_FILE);
            }
            hippoFileHelper.save(hippoData);
        } else {
            /* 
             * Nothing to do when hippoData is null, its not a breaking issue 
             * either so not throwing an exception
             */
        }
    }

    private File getBackupFile() {
        String appDirPath = ApplicationData.getAppDataLocation();

        File file = new File(appDirPath);
        file.mkdirs();

        return new File(appDirPath + DATA_BACKUP_FILE_NAME);
    }

    private void processFile(final File backupFile, final HippoData hippoData) {
        try ( BufferedReader br = new BufferedReader(new FileReader(backupFile))) {
            for (String line; (line = br.readLine()) != null;) {
                Hippo hippo = SimpleHippoReader.read(line);
                if (hippo != null) {
                    hippoData.addHippo(hippo);
                } else {
                    /*
                     * Something went wrong attempting to convert the line to a
                     * hippo. To prevent null and other invalid values to disrupt 
                     * the hippo data, we do nothing
                     */
                }
            }
        } catch (IOException ex) {
            LOGGER.log(ERROR, "Exception occurred trying to buffer a backup file", ex);
            throw new HippoDataException(HippoCeption.FAILURE_TO_RECOVER_DATA_FROM_BACKUP);
        }
    }

    private File determineBackupFile(final File file) {
        if (file == null) {
            return getBackupFile();
        } else {
            return file;
        }
    }
}
