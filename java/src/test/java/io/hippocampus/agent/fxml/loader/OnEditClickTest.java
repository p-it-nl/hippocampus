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

import io.hippocampus.agent.fxml.HippoViewController;
import io.hippocampus.agent.model.Hippo;
import java.io.IOException;
import javafx.event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.mockito.InjectMocks;

/**
 * Tests for on edit click
 *
 * @author Patrick-4488
 */
@ExtendWith(MockitoExtension.class)
public class OnEditClickTest {

    @Mock
    private Hippo hippo;

    @Mock
    private HippoViewController hippoViewController;

    @InjectMocks
    private OnEditClick classUnderTest;

    @Test
    public void handleWithoutEvent() throws IOException {
        Event event = null;

        assertDoesNotThrow(() -> classUnderTest.handle(event));

        verify(hippoViewController, times(1)).setEditHippo(hippo);
        verify(hippoViewController, times(1)).gotoToHippoAdd(event);
    }

    @Test
    public void handleWithEvent() throws IOException {
        Event mockedEvent = Mockito.mock(Event.class);

        assertDoesNotThrow(() -> classUnderTest.handle(mockedEvent));

        verify(hippoViewController, times(1)).setEditHippo(hippo);
        verify(hippoViewController, times(1)).gotoToHippoAdd(mockedEvent);
    }
}
