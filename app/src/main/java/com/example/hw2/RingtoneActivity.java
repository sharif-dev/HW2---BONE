package com.example.hw2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class RingtoneActivity extends AppCompatActivity implements SensorEventListener {
    private static float DES_SPEED = 20;
    public static int STOP_ALARM = 1;

    SensorManager sensorManager;
    Sensor gSensor;
    public Handler handler = new Handler(Looper.getMainLooper()){
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == STOP_ALARM){
                Log.d("SENSOR", "handleMessage: stop");
                ringtone.setLooping(false);
                ringtone.stop();
            }
        }
    };
    Ringtone ringtone;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringtone);
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Log.d("ALARM_RECEIVER", "broadcast of alarm is received!");
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(getBaseContext(), alarmUri);
        ringtone.setLooping(true);
        ringtone.play();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, gSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float speed = event.values[2];
        Log.d("SENSOR", "onSensorChanged: " + Float.toString(speed));
        if (speed > DES_SPEED){
            Message message = new Message();
            message.what = STOP_ALARM;
            handler.sendMessage(message);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
