package com.example.hw2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.Calendar;
import java.util.Set;

public class Alarm extends AppCompatActivity {

    Button editButton;
    ToggleButton toggleButton;
    TextView timeOfAlarm;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm2);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        timeOfAlarm = (TextView) findViewById(R.id.timeOfAlarm);
        editButton = (Button) findViewById(R.id.editAlarm);
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        InputStream inputStream = null;
        try {
            inputStream = getApplicationContext().
                    openFileInput(SettingAlarm.CACHE_FILE);
            if (inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String data = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((data = bufferedReader.readLine()) != null){
                    stringBuilder.append("\n").append(data);
                }
                inputStream.close();
                String wakeTime = stringBuilder.toString();
                JSONObject wakeTimeJson = new JSONObject(wakeTime);
                String myTime = wakeTimeJson.getString(SettingAlarm.wakeHour) + ":" + wakeTimeJson.getString(SettingAlarm.wakeMinute);
                timeOfAlarm.setText(myTime);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            timeOfAlarm.setText(R.string.default_time);
        }
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Log.d("TOGGLE_BUTTON", "toggle button is on");
                    String[] currentTime = timeOfAlarm.getText().toString().split(":");
                    int hour = Integer.parseInt(currentTime[0]);
                    int minute = Integer.parseInt(currentTime[1]);
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    Log.d("TOGGLE_BUTTON", "hour: " + Integer.toString(hour) + "minute: " + Integer.toString(minute));
                    Intent intent = new Intent(Alarm.this, AlarmReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(Alarm.this, 0, intent, 0);
                    alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
                }else {
                    Log.d("TOGGLE_BUTTON", "toggle button is off ");
                    alarmManager.cancel(pendingIntent);
                }
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SettingAlarm.class);
                startActivity(intent);
            }
        });
    }
}
