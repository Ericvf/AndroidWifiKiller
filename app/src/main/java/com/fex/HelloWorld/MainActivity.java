package com.fex.HelloWorld;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        setLastTime(Calendar.getInstance().getTime().getTime());
        super.onCreate(savedInstanceState);
    }

    public void setLastTime(long lastTime){
        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("ExampleJobService.lastTime", lastTime);
        editor.commit();
    }

    public void scheduleJob(View v) {
        Scheduler.scheduleJob(this);
    }
}