package com.example.hw2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SettingAlarm extends AppCompatActivity {
    public static final String wakeHour = "WAKEUP_HOUR";
    public static final String wakeMinute = "WAKEUP_MINUTE";
    public static String CACHE_FILE = "myCacheData.json";
    TimePicker timePicker;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_alarm);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        button = (Button) findViewById(R.id.finishSetting);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getApplicationContext().openFileOutput(SettingAlarm.CACHE_FILE, Context.MODE_PRIVATE));
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(wakeHour, timePicker.getHour());
                    jsonObject.put(wakeMinute, timePicker.getMinute());
                    outputStreamWriter.write(jsonObject.toString());
                    outputStreamWriter.close();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }
}
