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
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

import io.hippocampus.hippodata.asynctask.OnTaskCompleted;
import io.hippocampus.hippodata.asynctask.SaveHippoTask;

/**
 * Screen to add a Hippo
 *
 * @author Patrick-4488
 * @see AppCompatActivity
 */
public class HippoAdd extends AppCompatActivity implements OnTaskCompleted<Boolean> {

    private static final String SPACE = " ";

    @Override
    public void onTaskCompleted(final Boolean saved) {
        if (saved) {
            goToHippoView();
        } else {
            Toast.makeText(this, "Ooops, something went wrong, try saving again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hippo_add);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.save);
        fab.setOnClickListener(view -> saveHippo());
    }

    private void saveHippo() {
        String hippoText = getHippoText();
        List<String> hippoTags = getTags();

        if (!hippoText.isEmpty() && !hippoTags.isEmpty()) {
            new SaveHippoTask(this, hippoText, hippoTags).execute();
        } else {
            // Nothing to save when either hippoText or tags is empty
        }
    }

    private String getHippoText() {
        EditText hippoEditText = findViewById(R.id.hippo);

        return hippoEditText.getText().toString();
    }

    private List<String> getTags() {
        EditText hippoEditText = findViewById(R.id.tags);
        String tagText = hippoEditText.getText().toString();
        String[] tags = tagText.split(SPACE);

        return Arrays.asList(tags);
    }

    private void goToHippoView() {
        Intent toHippoViewIntent = new Intent(HippoAdd.this, HippoView.class);
        toHippoViewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(toHippoViewIntent);
    }
}
