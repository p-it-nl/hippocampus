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
package io.hippocampus.agent.fxml.loader;

import io.hippocampus.agent.model.Hippo;
import javafx.event.Event;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests for on delete click
 *
 * @author Patrick-4488
 */
@ExtendWith(MockitoExtension.class)
public class OnDeleteClickTest {

    @Mock
    private Hippo hippo;

    @Mock
    private VBox hippoWrapper;

    @Mock
    private VBox contentWrapper;

    private OnDeleteClick classUnderTest;

    @BeforeEach
    public void setUp() {
        classUnderTest = new OnDeleteClick(hippo, hippoWrapper, contentWrapper);
    }

    @Test
    public void handleWithoutEvent() {
        Event event = null;

        assertDoesNotThrow(() -> classUnderTest.handle(event));
        verify(contentWrapper, times(1)).getChildren();
    }

    @Test
    public void handleWithEvent() {
        Event mockedEvent = Mockito.mock(Event.class);

        assertDoesNotThrow(() -> classUnderTest.handle(mockedEvent));
        verify(contentWrapper, times(1)).getChildren();
    }
}
