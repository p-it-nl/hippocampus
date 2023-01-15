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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests for on hippo click
 *
 * @author Patrick-4488
 */
@ExtendWith(MockitoExtension.class)
public class OnHippoClickTest {

    @Mock
    private Hippo hippo;

    @Mock
    private VBox hippoWrapper;

    @Mock
    private VBox contentWrapper;

    private OnHippoClick classUnderTest;

    @BeforeEach
    public void setUp() {
        classUnderTest = new OnHippoClick(hippo, hippoWrapper, contentWrapper);
    }

    @Test
    public void handleWithoutEvent() {
        Event event = null;

        assertDoesNotThrow(() -> classUnderTest.handle(event));
        verify(hippoWrapper, times(1)).lookup(any(String.class));
    }

    @Test
    public void handleWithEvent() {
        Event mockedEvent = Mockito.mock(Event.class);
        HBox mockedHBox = Mockito.mock(HBox.class);

        when(hippoWrapper.lookup(any(String.class)))
                .thenReturn(mockedHBox);

        assertDoesNotThrow(() -> classUnderTest.handle(mockedEvent));
        verify(hippoWrapper, times(1)).lookup(any(String.class));
        verify(hippoWrapper, times(1)).getChildren();
    }
}
