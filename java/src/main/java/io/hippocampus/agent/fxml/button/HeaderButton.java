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
package io.hippocampus.agent.fxml.button;

import io.hippocampus.agent.data.backup.SimpleHippoWriter;
import io.hippocampus.agent.exception.HippoCeption;
import io.hippocampus.agent.exception.HippoEventException;
import io.hippocampus.agent.model.Hippo;
import io.hippocampus.agent.service.HippoService;
import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.INFO;

/**
 * The header button<br>
 * Includes sub menu functionality
 *
 * @author Patrick-4488
 */
public class HeaderButton extends MenuButton {

    private final HeaderButton instance;
    private final HippoService service;

    private static final String APP_ICON = "ic_menu.png";
    private static final String ID_HOVER = "hover";
    private static final String ID_MENU_ITEM = "menuItem";
    private static final String TEXT_LOAD_BACKUP = "Load hippo\'s from backup";
    private static final String TITLE_LOAD_BACKUP = "Select a backup file (.hippos) to load hippo\'s from";
    private static final String TEXT_CREATE_BACKUP = "Create backup ";
    private static final String TITLE_CREATE_BACKUP = "Choose where to save the backup file";
    private static final String TEXT_CLOSE_APP = "Close Hippocampus";
    private static final String EXTENSION = ".hippos";
    private static final String EXTENSION_WILDCARD = "*" + EXTENSION;
    private static final String EXTENSION_FILTER_NAME = "Hippocampus datafile";

    private static final System.Logger LOGGER = System.getLogger(HeaderButton.class.getName());

    public HeaderButton() {
        instance = this;
        service = HippoService.getInstance();

        setImage();
        setMenuItems();
        setEvents();
    }

    private void setImage() {
        Image headerMenuIcon = new Image(getClass().getClassLoader().getResourceAsStream(APP_ICON));

        super.setGraphic(new ImageView(headerMenuIcon));
    }

    private void setEvents() {
        super.setOnMouseEntered((MouseEvent t) -> instance.setId(ID_HOVER));
        super.setOnMouseExited((MouseEvent t) -> instance.setId(null));
    }

    private void setMenuItems() {
        instance.getItems().addAll(
                getLoadBackupItem(),
                getCreateBackupItem(),
                getCloseItem());
    }

    private MenuItem getLoadBackupItem() {
        MenuItem loadBackup = new MenuItem(TEXT_LOAD_BACKUP);
        loadBackup.setOnAction((final ActionEvent t) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(TITLE_LOAD_BACKUP);
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter(EXTENSION_FILTER_NAME, EXTENSION_WILDCARD));
            Stage stage = (Stage) this.getScene().getWindow();

            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                openFile(file);
            }

            if (file != null && file.exists() && file.canRead()) {
                service.loadHipposFromBackUp(file);
            } else if (file != null && !file.canRead()) {
                throw new HippoEventException(HippoCeption.FAILURE_TO_READ_FILE);
            } else {
                // Seems the action is cancelled
            }
        });
        return loadBackup;
    }

    private MenuItem getCreateBackupItem() {
        MenuItem saveBackup = new MenuItem(TEXT_CREATE_BACKUP);
        saveBackup.setOnAction((final ActionEvent t) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(TITLE_CREATE_BACKUP);
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter(EXTENSION_FILTER_NAME, EXTENSION_WILDCARD));
            Stage stage = (Stage) this.getScene().getWindow();

            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                file = determineFile(file);
                saveHipposToBackup(file);
            } else {
                // No file meaning nothing to save
            }
        });
        return saveBackup;
    }

    private MenuItem getCloseItem() {
        MenuItem closeItem = new MenuItem(TEXT_CLOSE_APP);
        closeItem.setId(ID_MENU_ITEM);
        closeItem.setOnAction((final ActionEvent t) -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.close();
        });

        return closeItem;
    }

    private void openFile(final File file) {
        LOGGER.log(INFO, "Opening back-up file");

        try {
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            LOGGER.log(ERROR, """
                              Exception occurred when trying to interact with a 
                              file via the filechooser""", ex);
            throw new HippoEventException(HippoCeption.FAILURE_TO_OPEN_FILECHOOSER);
        }
    }

    private void saveHipposToBackup(final File file) {
        LOGGER.log(INFO, "Writing back-up file");

        try ( FileOutputStream fos = new FileOutputStream(file)) {
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            for (Hippo hippo : service.getHippos()) {
                bos.write(SimpleHippoWriter.write(hippo).getBytes());
            }
            bos.flush();
        } catch (IOException ex) {
            LOGGER.log(ERROR, "Exception occurred when trying to save a backup file "
                    + "via the filechooser", ex);
            throw new HippoEventException(HippoCeption.FAILURE_TO_SAVE_BACKUP);
        }
    }

    private File determineFile(final File file) {
        String currPath = file.getAbsolutePath();
        if (currPath.endsWith(EXTENSION)) {
            return file;
        } else {
            return new File(file.getAbsolutePath() + EXTENSION);
        }
    }
}
