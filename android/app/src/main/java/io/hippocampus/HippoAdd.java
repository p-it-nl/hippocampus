/**
 * Copyright (c) p-it
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.hippocampus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.hippocampus.hippodata.asynctask.OnTaskCompleted;
import io.hippocampus.hippodata.asynctask.SaveHippoTask;

/**
 * Screen to add a Hippo
 *
 * @author Patrick-4488
 * @see AppCompatActivity
 */
public class HippoAdd extends AppCompatActivity implements OnTaskCompleted<Boolean> {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

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

        if (!hippoText.isEmpty()) {
            executor.execute(new SaveHippoTask(new Handler(Looper.getMainLooper()), this, hippoText, getTags()));
        } else {
            // Nothing to save when either hippoText or tags is empty
        }
    }

    private String getHippoText() {
        EditText hippoEditText = findViewById(R.id.hippo);

        return hippoEditText.getText().toString();
    }

    private String getTags() {
        EditText hippoEditText = findViewById(R.id.tags);
        return hippoEditText.getText().toString();
    }

    private void goToHippoView() {
        Intent toHippoViewIntent = new Intent(HippoAdd.this, HippoView.class);
        toHippoViewIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(toHippoViewIntent);
    }
}
