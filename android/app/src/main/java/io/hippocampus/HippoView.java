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
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.hippocampus.adapter.HippoDapter;
import io.hippocampus.hippodata.asynctask.GetHipposTask;
import io.hippocampus.hippodata.asynctask.OnTaskCompleted;
import io.hippocampus.hippodata.entity.Hippo;
import io.hippocampus.hippodata.hive.Hivemind;

/**
 * Shows the list of hippos and allows interaction
 *
 * @author Patrick-4488
 * @see AppCompatActivity
 */
public class HippoView extends AppCompatActivity implements OnTaskCompleted<List<Hippo>> {

    private HippoDapter itemsAdapter;
    private List<Hippo> hippos;
    private Hivemind hivemind;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void onTaskCompleted(final List<Hippo> hippos) {
        this.hippos.clear();
        this.hippos.addAll(hippos);
        this.itemsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hippo_view);

        hippos = new ArrayList<>();
        hivemind = Hivemind.getInstance();

        prepareLayout();
        prepareSearch();
    }

    @Override
    protected void onStart() {
        super.onStart();

        itemsAdapter = new HippoDapter(getApplicationContext(), hippos);

        ListView listView = findViewById(R.id.hippo_list);
        listView.setAdapter(itemsAdapter);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new GetHipposTask(handler, this, null));

        hivemind.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        hivemind.start();
    }

    @Override
    public void onStop() {
        super.onStop();

        hivemind.stop();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();

        hivemind.stop();
    }

    private void prepareLayout() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(view -> toHippoAdd());
    }

    private void prepareSearch() {
        EditText search = findViewById(R.id.search);
        final HippoView listener = this;

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(final Editable input) {
                executor.execute(new GetHipposTask(new Handler(Looper.getMainLooper()), listener, input.toString()));
            }

            @Override
            public void beforeTextChanged(final CharSequence s, final int start,
                                          final int count, final int after) {
                // No change before text change
            }

            @Override
            public void onTextChanged(final CharSequence s, final int start,
                                      final int before, final int count) {
                // No change on text changed
            }
        });
    }

    private void toHippoAdd() {
        Intent toIntent = new Intent(HippoView.this, HippoAdd.class);
        startActivity(toIntent);
    }
}
