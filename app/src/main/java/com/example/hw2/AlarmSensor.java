package com.example.hw2;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AlarmSensor extends Activity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor gSensor;
    Handler handler;
    private static float DES_SPEED = 10;
    public static int STOP_ALARM = 1;
    @Override
    public void onSensorChanged(SensorEvent event) {
        float speed = event.values[2];
        Log.d("SENSOR", "onSensorChanged: " + Float.toString(speed));
//        if (speed > DES_SPEED){
//            Message message = new Message();
//            message.what = STOP_ALARM;
//            handler.sendMessage(message);
//        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public AlarmSensor(Handler handler){
        Log.d("SENSOR", "AlarmSensor: start set sensores");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Log.d("SENSOR", "AlarmSensor: set sensores");
    }

    public void on(){
        sensorManager.registerListener(this, gSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void off(){
        sensorManager.unregisterListener(this);
    }

}
