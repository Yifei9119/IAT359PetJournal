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

public class MainActivity extends Activity{

//    declare variables
    LinearLayout petAvatars;
    ImageButton addPet;
    ImageButton tips;
    ImageButton settings;
    static Button journal;

    Intent intent;
    static SharedPreferences sharedPrefs;


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
        sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        intent = new Intent(this, LightDarkMode.class);
        startService(intent);

        Intent bgmusicPlayer = new Intent(this, MusicPlayer.class);
        startService(bgmusicPlayer);


        setMode();


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

//    protected void DarkLightMode(){
//        float light_val = sharedPrefs.getFloat("lightSensor", 0);
//        if(light_val<threshold){
//            journal.setBackgroundColor(Color.BLUE);
//        }
//        else{
//            journal.setBackgroundColor(Color.RED);
//        }
//    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }

//    sets the dark, light, or auto mode based on user preferences
// this method is called in the beginning to set up the UI and called in the service for any updates to user preferences.
public static void setMode(){
    float light_val = sharedPrefs.getFloat("lightSensor", 0);
    float threshold = LightDarkMode.getThreshold();

    if(light_val<threshold){
            journal.setBackgroundColor(Color.BLUE);
        }
        else{
            journal.setBackgroundColor(Color.RED);
        }
}
}
