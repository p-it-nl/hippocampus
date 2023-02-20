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
package io.hippocampus.agent.fxml;

import io.hippocampus.agent.model.Hippo;
import javafx.event.Event;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests for hippo add controller
 *
 * @author Patrick-4488
 */
public class HippoViewControllerTest {

    @Test
    public void gotoToHippoAddWithoutEvent() {
        HippoViewController classUnderTest = new HippoViewController();
        Event event = null;

        assertDoesNotThrow(() -> classUnderTest.gotoToHippoAdd(event));
    }

    @Test
    public void gotoToHippoAddWithEvent() {
        HippoViewController classUnderTest = new HippoViewController();
        Event mockedEvent = Mockito.mock(Event.class);

        assertDoesNotThrow(() -> classUnderTest.gotoToHippoAdd(mockedEvent));
    }

    @Test
    public void searchAddWithoutEvent() {
        HippoViewController classUnderTest = new HippoViewController();
        Event event = null;

        assertDoesNotThrow(() -> classUnderTest.search(event));
    }

    @Test
    public void searchWithEvent() {
        HippoViewController classUnderTest = new HippoViewController();
        Event mockedEvent = Mockito.mock(Event.class);

        assertDoesNotThrow(() -> classUnderTest.search(mockedEvent));
    }

    @Test
    public void setEditHippoWithoutHippo() {
        HippoViewController classUnderTest = new HippoViewController();
        Hippo hippo = null;

        assertDoesNotThrow(() -> classUnderTest.setEditHippo(hippo));
    }

    @Test
    public void setEditHippoWithEmptyHippo() {
        HippoViewController classUnderTest = new HippoViewController();
        Hippo hippo = new Hippo();

        assertDoesNotThrow(() -> classUnderTest.setEditHippo(hippo));
    }
}
