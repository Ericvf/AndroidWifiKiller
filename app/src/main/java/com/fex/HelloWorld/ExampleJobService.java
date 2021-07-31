package com.fex.HelloWorld;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class ExampleJobService extends JobService {
    private static final String TAG = "ExampleJobService";
    private Date lastTime = null;

    @Override
    public boolean onStartJob(JobParameters params) {

        long lastTimeMs = getLastTime();
        if (lastTimeMs == -1){
            Log.d(TAG, "Very first start");
            lastTimeMs  = Calendar.getInstance().getTime().getTime();
            setLastTime(lastTimeMs);
        }

        lastTime = new Date(lastTimeMs);

        Log.d(TAG, "Job started: " + lastTime);
        doBackgroundWork(params);
        return true;
    }

    public void setLastTime(long lastTime){
        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("ExampleJobService.lastTime", lastTime);
        editor.commit();
    }

    public long getLastTime() {
        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        long myIntValue = sp.getLong("ExampleJobService.lastTime", -1);
        return myIntValue;
    }

    private void makeToast(Context context, String s) {
        ContextCompat.getMainExecutor(context).execute(() -> {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        });
        Log.d(TAG, s);
    }

    private void doBackgroundWork(final JobParameters params) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Context context = getApplicationContext();

                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                boolean isWifiEnabled = wifi.isWifiEnabled();
                if (isWifiEnabled) {
                    Date currentTime = Calendar.getInstance().getTime();
                    PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
                    boolean isScreenOn = powerManager.isScreenOn();
                    Log.d(TAG, "Screen: " + isScreenOn);
                    if (isScreenOn) {
                        lastTime = currentTime;
                        setLastTime(lastTime.getTime());
                        Log.d(TAG, "Set preference. Goodbye.");
                    } else {
                        long diff = currentTime.getTime() - lastTime.getTime();
                        Log.d(TAG, "Difference: " + diff);
                        if (diff > 1000 * 60 * 10) {
                            wifi.setWifiEnabled(false);
                        }
                    }
                }

                Scheduler.scheduleJob(getApplicationContext());
            }
        }).start();

    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job finished");
        return true;
    }
}