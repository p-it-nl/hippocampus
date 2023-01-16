package io.hippocampus.listener;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import io.hippocampus.adapter.HippoDapter;
import io.hippocampus.hippodata.asynctask.OnTaskCompleted;
import io.hippocampus.hippodata.entity.Hippo;

/**
 * Listener for delete event for an Hippo
 *
 * @author Patrick-4488
 * @see View.OnClickListener
 */
public abstract class HippoDeleteListener implements View.OnClickListener, OnTaskCompleted<Hippo> {

    private final Context context;
    private final HippoDapter dapter;

    public HippoDeleteListener(final Context context, final HippoDapter dapter) {
        this.context = context;
        this.dapter = dapter;
    }

    @Override
    public void onTaskCompleted(final Hippo hippo) {
        if (hippo != null) {
            dapter.hippos.remove(hippo);
            dapter.notifyDataSetChanged();
        } else {
            Toast.makeText(context, "Ooops, something went wrong, try saving again", Toast.LENGTH_SHORT).show();
        }
    }

    public abstract void onClick(final View view);
}
