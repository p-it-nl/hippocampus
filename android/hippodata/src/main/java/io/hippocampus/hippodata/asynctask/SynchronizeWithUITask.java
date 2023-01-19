package io.hippocampus.hippodata.asynctask;

import android.os.Handler;

import java.util.List;

import io.hippocampus.hippodata.HippoDatabase;
import io.hippocampus.hippodata.entity.Hippo;
import io.hippocampus.hippodata.hive.Hivemind;
import io.hippocampus.hippodata.service.HippoService;
import io.hivemind.synchronizer.HiveSynchronizer;

/**
 * Start synchronization and update UI when data has been updated
 *
 * @author Patrick-4488
 */
public class SynchronizeWithUITask implements Runnable {

    private final OnTaskCompleted listener;
    private final Handler handler;
    private final HippoService hippoService;
    private final Hivemind hivemind;

    public SynchronizeWithUITask(final Handler handler, final OnTaskCompleted listener) {
        this.listener = listener;
        this.handler = handler;

        hippoService = new HippoService(HippoDatabase.getInstance().getDatabase());
        hivemind = Hivemind.getInstance();
        hivemind.setUiTask(this);
        hivemind.start();
    }

    @Override
    public void run() {
        List<Hippo> hippos = hippoService.getHippos(null);
        handler.post(() -> {
            listener.onTaskCompleted(hippos);
        });
    }
}
