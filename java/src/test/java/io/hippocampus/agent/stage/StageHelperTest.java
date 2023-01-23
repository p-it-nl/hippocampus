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
package io.hippocampus.agent.stage;

import io.hippocampus.agent.exception.HippoException;
import io.hippocampus.agent.exception.HippoRuntimeException;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for stage helper
 *
 * @author Patrick-4488
 */
@ExtendWith(MockitoExtension.class)
public class StageHelperTest {

    @Mock
    private Stage stage;

    @Test
    public void buildStartupSceneWithoutStage() {
        String expected = HippoRuntimeException.class.getSimpleName();

        StageHelper stageHelper = new StageHelper(null);
        HippoException exception = assertThrows(HippoException.class, stageHelper::buildStartupScene);

        assertEquals(expected, exception.getClass().getSimpleName());
    }

    @Test
    public void buildStartupSceneWithStageNotHavingAnyValues() {
        String expected = HippoRuntimeException.class.getSimpleName();

        StageHelper stageHelper = new StageHelper(stage);
        HippoException exception = assertThrows(HippoException.class, stageHelper::buildStartupScene);

        assertEquals(expected, exception.getClass().getSimpleName());
    }
}
