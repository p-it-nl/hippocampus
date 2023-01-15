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
package io.hippocampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import io.hippocampus.R;
import io.hippocampus.hippodata.model.Hippo;

/**
 * Adapter for hippo list
 *
 * @author Patrick-4488
 */
public class HippoDapter extends ArrayAdapter<Hippo> {

    private final Context context;
    private final List<Hippo> hippos;

    public HippoDapter(final Context context, final List<Hippo> hippos) {
        super(context, 0, hippos);

        this.context = context;
        this.hippos = hippos;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item_hippo, parent, false);
        }

        Hippo hippo = hippos.get(position);
        TextView hippoText = listItem.findViewById(R.id.text);
        hippoText.setText(hippo.hippo);

        return listItem;
    }
}

