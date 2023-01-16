package io.hippocampus.hippodata.asynctask;

import android.os.AsyncTask;

import io.hippocampus.hippodata.HippoDatabase;
import io.hippocampus.hippodata.database.AppDatabase;
import io.hippocampus.hippodata.entity.Hippo;
import io.hippocampus.hippodata.service.HippoService;

/**
 * Task for deleting an hippo
 *
 * @author Patrick-4488
 * @see AsyncTask
 */
public class DeleteHippoTask extends AsyncTask<Hippo, Void, Hippo> {

    private final OnTaskCompleted listener;
    private final HippoService hippoService;

    public DeleteHippoTask(final OnTaskCompleted listener) {
        this.listener = listener;

        AppDatabase db = HippoDatabase.getInstance().getDatabase();
        hippoService = new HippoService(db);
    }

    /**
     * Only deleting the first entry at this moment, the list is pure android implementation
     * PS: weird exception handling but its only to make sure hippo is only removed from the list if
     * actually deleted.
     *
     * @param hippos the hippo(s) to delete (max 1)
     * @return the deleted hippo or null
     */
    @Override
    protected Hippo doInBackground(final Hippo... hippos) {
        Hippo hippo = hippos[0];

        try {
            hippoService.deleteHippo(hippo);
            return hippo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(final Hippo hippo) {
        listener.onTaskCompleted(hippo);
    }
}

