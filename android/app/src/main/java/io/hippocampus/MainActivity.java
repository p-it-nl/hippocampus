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
package io.hippocampus;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import io.hippocampus.hippodata.HippoDatabase;

/**
 * Prepares the database and opens hippo view
 *
 * @author Patrick-4488
 * @see AppCompatActivity
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareHippoDatabase();
        openHippoView();
    }

    private void prepareHippoDatabase() {
        HippoDatabase.getInstance().prepareDatabase(getApplicationContext());
    }

    private void openHippoView() {
        Intent toHippoViewIntent = new Intent(MainActivity.this, HippoView.class);
        toHippoViewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(toHippoViewIntent);
    }
}
