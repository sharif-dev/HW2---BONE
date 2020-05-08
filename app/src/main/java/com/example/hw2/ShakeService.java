package com.example.hw2;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.FloatMath;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import java.util.EventListener;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ShakeService extends IntentService implements SensorEventListener {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String START_SERVICE = "com.example.hw2.action.FOO";
    public static final String STOP_SERVICE = "com.example.hw2.action.BAZ";
    private static SensorManager sensorManager;
    private static Sensor sensor;
    private static boolean power = true;
    private static float SHAKE_THRESHOLD = 1.0F;
    public static Handler handler;
    public static final int TURN_ON_SCREEN = 1;
    // TODO: Rename parameters

    private static final String EXTRA_PARAM1 = "com.example.hw2.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.hw2.extra.PARAM2";

    public ShakeService() {
        super("ShakeService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ShakeService.class);
        intent.setAction(START_SERVICE);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ShakeService.class);
        intent.setAction(STOP_SERVICE);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (START_SERVICE.equals(action)) {
                Log.d("SHAKE_SERVICE", "onHandleIntent: START_SERVICE");
                sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                power = true;
            } else if (STOP_SERVICE.equals(action)) {
                Log.d("SHAKE_SERVICE", "onHandleIntent: STOP_SERVICE");
                power = false;
            }
        }
    }

    @Override
    public boolean stopService(Intent name) {
        Log.d("SHAKE_SERVICE", "onHandleIntent: STOP_SERVICE");
        return super.stopService(name);
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onSensorChanged(SensorEvent event) {
        float gx = event.values[0] / SensorManager.GRAVITY_EARTH;
        float gy = event.values[1] / SensorManager.GRAVITY_EARTH;
        float gz = event.values[2] / SensorManager.GRAVITY_EARTH;
        float currentShake = (float) Math.sqrt(gx * gx + gy * gy + gz * gz);
        if (currentShake > SHAKE_THRESHOLD){
            Log.d("SHAKE_SERVICE", "onSensorChanged: YESSSSSS");
            Message message = new Message();
            message.what = TURN_ON_SCREEN;
            handler.sendMessage(message);
        }
        Log.d("SHAKE_SERVICE", "onSensorChanged: " + Float.toString(currentShake));
        if (!power){
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
