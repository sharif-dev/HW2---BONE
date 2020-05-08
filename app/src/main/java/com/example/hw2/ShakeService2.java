package com.example.hw2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ShakeService2 extends Service {
    public ShakeService2() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
