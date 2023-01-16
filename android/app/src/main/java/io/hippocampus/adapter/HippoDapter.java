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
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import io.hippocampus.R;
import io.hippocampus.hippodata.asynctask.DeleteHippoTask;
import io.hippocampus.hippodata.entity.Hippo;
import io.hippocampus.hippodata.entity.Tag;
import io.hippocampus.listener.HippoDeleteListener;

/**
 * Adapter for hippo list
 *
 * @author Patrick-4488
 * @see ArrayAdapter
 */
public class HippoDapter extends ArrayAdapter<Hippo> {

    private final Context context;
    public final List<Hippo> hippos;

    public HippoDapter(final Context context, final List<Hippo> hippos) {
        super(context, 0, hippos);

        this.context = context;
        this.hippos = hippos;
    }

    /**
     * Returns a view as list item for in the list containing the hippo data
     *
     * @param position position of the item within the list
     * @param convertView the list item view | null
     * @param parent the view containing the list (ListView)
     * @return the view as list item with hippo data
     */
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item_hippo, parent, false);
        } else {
            // The list item is already rendered, nothing to do
        }

        final Hippo hippo = hippos.get(position);

        prepareListItem(hippo, listItem);
        setText(hippo, listItem);
        setTags(hippo.tags, listItem, parent);

        return listItem;
    }

    private void prepareListItem(final Hippo hippo, final View listItem) {
        final TextView hippoText = listItem.findViewById(R.id.list_item_text);
        final RelativeLayout hippoListItemExpand = listItem.findViewById(R.id.list_item_expand);
        final Button hippoListItemDelete = listItem.findViewById(R.id.delete);

        hippoListItemExpand.setVisibility(View.GONE);
        hippoText.setOnClickListener(view -> toggleExpand(hippoListItemExpand));

        hippoListItemDelete.setOnClickListener(new HippoDeleteListener(context, this) {
            @Override
            public void onClick(View view) {
                new DeleteHippoTask(this).execute(hippo);
            }
        });
    }

    private void setText(final Hippo hippo, final View listItem) {
        TextView hippoText = listItem.findViewById(R.id.list_item_text);
        hippoText.setText(hippo.hippo);
    }

    private void toggleExpand(final View hippoListItemExpand) {
        if (hippoListItemExpand.getVisibility() == View.GONE) {
            hippoListItemExpand.setVisibility(View.VISIBLE);
        } else {
            hippoListItemExpand.setVisibility(View.GONE);
        }
    }

    private void setTags(final List<Tag> tags, final View listItem, final ViewGroup parent) {
        if (tags == null) {
            // Tags are not a must
            return;
        }

        RelativeLayout hippoListItemExpand = listItem.findViewById(R.id.list_item_expand);
        GridLayout tagList = hippoListItemExpand.findViewById(R.id.tag_grid);
        tagList.removeAllViews();
        addTags(tags, tagList, parent);
    }

    private void addTags(final List<Tag> tags, final GridLayout tagList, final ViewGroup parent) {
        for (Tag tag : tags) {
            View tagView = getViewForTag(parent);
            setTextViewForTag(tag, tagView);

            tagList.addView(tagView, 0);
        }
    }

    private View getViewForTag(final ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return vi.inflate(R.layout.list_item_tag, parent, false);
    }

    private void setTextViewForTag(final Tag tag, final View tagView) {
        TextView tagTextView = tagView.findViewById(R.id.tag_text);
        tagTextView.setText(tag.tag);
    }
}

