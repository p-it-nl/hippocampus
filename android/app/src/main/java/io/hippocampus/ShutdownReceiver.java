package io.hippocampus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import io.hippocampus.hippodata.hive.Hivemind;

/**
 * Handles shutdown event
 *
 * @see BroadcastReceiver
 */
public class ShutdownReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.i("Shutting down", "stopping Hivemind");

        Hivemind.getInstance().stop();
    }
}
