package com.example.iat359_petjournal;

import static androidx.core.app.ActivityCompat.recreate;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class LightDarkMode extends Service implements SensorEventListener {
//    decalre variables
     Sensor lightSensor;
     static SensorManager mySensorManager;
    SharedPreferences sharedPrefs;

    static float threshold = 6;
    boolean paused = false;
    Thread backgroundThread;
    public LightDarkMode(){
    }

    //we need to override onCreate() and onDestroy()
    //these methods contain the functionality of the service when started and stopped
    @Override
    public void onCreate() {
        super.onCreate();
        paused = false;
//        getshared prefs and get the light sensor
        sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        backgroundThread = new Thread(new Runnable() {
            public void run() {
                listenSensor();
            }
        });
        backgroundThread.start();
    }

    //    ondestroy unregister the listener and the background thread is interrupted
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (lightSensor!=null)
        {
            mySensorManager.unregisterListener(this);
        }

        paused = true;
        backgroundThread.interrupt();
    }

    //    register listener
    public void listenSensor()
    {
        try {
            mySensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        catch (Exception e)
        {
            Log.e("Service LogTag", "register failed", e);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    on sensor change check if the type is a light sensor and place the light sensor value in sharedprefs
//    update all the interfaces to the style based on the light sensor changes.
    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();

        if(type == Sensor.TYPE_LIGHT){
            float light_val = event.values[0];
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putFloat("lightSensor", light_val);
            editor.commit();

            MainActivity.setMode();
            Settings.setSettingsMode();

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

//    sets the threshold to a new one
    public static void setLightsensor(float newT){
        threshold = newT;
    }

//    gets the threshold
    public static float getThreshold(){
        return threshold;
    }
}
