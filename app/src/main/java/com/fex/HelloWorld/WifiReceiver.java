package com.fex.HelloWorld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiReceiver extends BroadcastReceiver {

    private static final String TAG = "WifiReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Check the state here
        int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);

        Log.d(TAG, "Wifi State: " + state);
    }
}
