package com.example.iat359_petjournal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.ColorInt;

public class MainActivity extends Activity implements SensorEventListener {

//    declare variables
    LinearLayout petAvatars;
    ImageButton addPet;
    ImageButton tips;
    ImageButton settings;
    Button journal;

    float threshold = 6;

    SensorManager mySensorManager;
    Sensor lightSensor;
    SharedPreferences sharedPrefs;


    public static final String DEFAULT = "not available";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

//        initialize variables
        petAvatars = (LinearLayout) findViewById(R.id.petavatar);
        addPet = (ImageButton) findViewById(R.id.add_pet);
        tips = (ImageButton) findViewById(R.id.tips_advice);
        settings = (ImageButton) findViewById(R.id.settings);
        journal = (Button) findViewById(R.id.journal);
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        Intent bgmusicPlayer = new Intent(this, MusicPlayer.class);
        startService(bgmusicPlayer);
        // image button listening on click
//    after click start intent to journal class
        tips.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), TipsAdvice.class);
                startActivity(intent);
            }
        });

// add image button listening to onclick
//    after click start intent to addPet class
        addPet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), AddPet.class);
                startActivity(intent);
            }
        });

        // journal button listens to onclick
//    after click start intent to journal class
        journal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), Journal.class);
                startActivity(intent);
            }
        });

// settings image button listens to onclick
//    after click start intent to settings class
        settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), Settings.class);
                startActivity(intent);
            }
        });
    }

    protected void DarkLightMode(){
        float light_val = sharedPrefs.getFloat("lightSensor", 0);
        if(light_val<threshold){
            journal.setBackgroundColor(Color.BLUE);
        }
        else{
            journal.setBackgroundColor(Color.RED);
        }
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        if(type == Sensor.TYPE_LIGHT){
            float light_val = event.values[0];
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putFloat("lightSensor", light_val);
            editor.commit();
            DarkLightMode();

        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //    on resume start listening to the light and accelerometer sensors
    @Override
    protected void onResume(){
        super.onResume();
        mySensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    // on pause unregister or release all the sensors
    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(this);
    }

}
