package com.example.iat359_petjournal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity implements View.OnClickListener {
//    declare variables
    SeekBar volume;
    int currentVolume;
    static TextView volumeTextView;
    static TextView bgmusicText;
    static TextView appearanceText;
    static TextView volumeText;

    static TextView setting;
    AudioManager audio;
    Intent bgMusicPlayer;
    static ToggleButton bgMusictoggle;

    static LinearLayout bg;

    static SharedPreferences sharedPrefs;

    static Button auto;
    static Button nightmode;
    static Button lightmode, save, logout;

    static ImageButton backButton;

    static Drawable autoimage, nightimage,lightimage, lightimage2,nightimage2,autoimage2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //      initialize variables
        logout = findViewById(R.id.logout);
        backButton = findViewById(R.id.backButton);
        bgMusictoggle = findViewById(R.id.toggleButton);
        volume = (SeekBar) findViewById(R.id.seekBar);
        volumeTextView = (TextView) findViewById(R.id.volume);
        bgmusicText = (TextView) findViewById(R.id.bgmusic);
        appearanceText = (TextView) findViewById(R.id.appearance);
        volumeText = (TextView) findViewById(R.id.volumetext);
        setting = (TextView) findViewById(R.id.setting);
        save = findViewById(R.id.save);
        autoimage = getResources().getDrawable(R.drawable.auto_night);
        lightimage = getResources().getDrawable(R.drawable.brightness_dark);
        nightimage = getResources().getDrawable(R.drawable.night_mode_night);

        autoimage2 = getResources().getDrawable(R.drawable.auto);
        lightimage2 = getResources().getDrawable(R.drawable.brightness);
        nightimage2 = getResources().getDrawable(R.drawable.night_mode);

        bgMusicPlayer = new Intent(this, MusicPlayer.class);

        auto = (Button) findViewById(R.id.autoButton);
        nightmode = (Button) findViewById(R.id.DarkMode);
        lightmode = (Button) findViewById(R.id.LightMode);

        auto.setOnClickListener(this);
        nightmode.setOnClickListener(this);
        lightmode.setOnClickListener(this);

        sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        bg = (LinearLayout) findViewById(R.id.bg);

//        initialize audio manager and find the audio service
//        set the max volume the to the music stream and set the seekbar to the max volume
         audio = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume.setMax(maxVolume);

//      initialize the current volume seekbar to the default volume based on the audio stream volume for music
        currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
        volume.setProgress(currentVolume);
        volumeTextView.setText(String.valueOf(currentVolume*4));

//        add a seekbar listener whenever it changes
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audio.setStreamVolume(AudioManager.STREAM_MUSIC,progress, 0);
                volumeTextView.setText(String.valueOf(progress*4));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        listen to the oncheckedchange listener
        bgMusictoggle.setOnCheckedChangeListener(listener);

        //       when logout button is clicked go to login.class
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Login.class);
                startActivity(intent);
                stopService(bgMusicPlayer);
            }
        });

//    when back button is clicked go to home page
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

//        based on the sharedPrefs show the previous selection that the user has made
        String modeString = sharedPrefs.getString("selected", "");
        if(modeString.equals("")){
            auto.setBackgroundResource(R.drawable.text_image_button_selected);
        }
        else if(modeString.equals("auto")) {
            auto.setBackgroundResource(R.drawable.text_image_button_selected);
        }
        else if(modeString.equals("light")){
            lightmode.setBackgroundResource(R.drawable.text_image_button_selected);

        }
        else if(modeString.equals("dark")){
            nightmode.setBackgroundResource(R.drawable.text_image_button_selected);
        }

// gets the value from sharedpref to determine whether the sound toggle is on or off
        String togglebutton = sharedPrefs.getString("value","");
        if(togglebutton.equals("ON")){
            bgMusictoggle.setChecked(true);
        }
        else if(togglebutton.equals("OFF")){
            bgMusictoggle.setChecked(false);
        }
        setSettingsMode();


    }

//    toggle button listening to whether the user has toggled on or off. If on play background music, otherwise stop music player
CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
@Override
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    SharedPreferences.Editor editor = sharedPrefs.edit();

    if(isChecked){

        startService(bgMusicPlayer);
        MainActivity.setStart(true);
        editor.putString("value","ON");
        editor.commit();
    }
        else{
        stopService(bgMusicPlayer);
        MainActivity.setStart(false);
        editor.putString("value","OFF");
        editor.commit();

    }
    }};

//    listen to the keys of volume up and down hardware buttons. If up increase volume, if down decrease volume and show the volume state in the seekbar
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    //TODO
                    if(currentVolume*4<100) {
                        currentVolume += 1;
                        volume.setProgress(currentVolume);
                        volumeTextView.setText(String.valueOf(currentVolume*4));
                    }

                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    //TODO
                    if(currentVolume*4>0) {
                        currentVolume -= 1;
                        volume.setProgress(currentVolume);
                        volumeTextView.setText(String.valueOf(currentVolume*4));
                    }

                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

//    handling button clicks for the light/dark mode
    // if user selects on one of the three have the current selection active state
// and set the threshold of the light sensor according to user preferences, the service class would handle the appearance changes
    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = sharedPrefs.edit();

        Log.d("tags",sharedPrefs.getString("selected",""));
        if (v == findViewById(R.id.autoButton)) {

            if (sharedPrefs.getString("selected", "").equals("dark")) {
                auto.setBackgroundResource(R.drawable.text_image_button_selected_night);
                lightmode.setBackgroundResource(R.drawable.text_image_button_night);
                nightmode.setBackgroundResource(R.drawable.text_image_button_night);

//                editor.putFloat("lightSensor", 0);
//                editor.commit();

            } else if (sharedPrefs.getString("selected", "").equals("auto") || sharedPrefs.getString("selected", "").equals("light")) {
                auto.setBackgroundResource(R.drawable.text_image_button_selected);
                lightmode.setBackgroundResource(R.drawable.text_image_button);
                nightmode.setBackgroundResource(R.drawable.text_image_button);

//                editor.putFloat("lightSensor", 100);
//                editor.commit();

            }

        }

        else if (v == findViewById(R.id.LightMode)) {
                if (sharedPrefs.getString("selected", "").equals("dark")) {
                    auto.setBackgroundResource(R.drawable.text_image_button_night);
                    lightmode.setBackgroundResource(R.drawable.text_image_button_selected_night);
//                    nightmode.setBackgroundResource(R.drawable.text_image_button_night);
//                    editor.putFloat("lightSensor", 0);
//                    editor.commit();

                } else if (sharedPrefs.getString("selected", "").equals("light") || sharedPrefs.getString("selected", "").equals("auto")) {
                    auto.setBackgroundResource(R.drawable.text_image_button);
                    lightmode.setBackgroundResource(R.drawable.text_image_button_selected);
                    nightmode.setBackgroundResource(R.drawable.text_image_button);
//                    editor.putFloat("lightSensor", 100);
//                    editor.commit();
                }

            } else if (v == findViewById(R.id.DarkMode)) {
                if (sharedPrefs.getString("selected", "").equals("light") || sharedPrefs.getString("selected", "").equals("auto")) {
                    auto.setBackgroundResource(R.drawable.text_image_button);
                    lightmode.setBackgroundResource(R.drawable.text_image_button);
                    nightmode.setBackgroundResource(R.drawable.text_image_button_selected);
//                    editor.putFloat("lightSensor", 100);
//                    editor.commit();

                } else if (sharedPrefs.getString("selected", "").equals("dark")) {
                auto.setBackgroundResource(R.drawable.text_image_button_night);
                lightmode.setBackgroundResource(R.drawable.text_image_button_night);
                nightmode.setBackgroundResource(R.drawable.text_image_button_selected_night);
//                    editor.putFloat("lightSensor", 0);
//                        editor.commit();

                }


        }
    }

    public void saveSettings(View view){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        if (auto.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.text_image_button_selected).getConstantState()) || auto.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.text_image_button_selected_night).getConstantState())){
            LightDarkMode.setLightsensor(6);
            editor.putString("selected", "auto");
        }
        else if(lightmode.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.text_image_button_selected).getConstantState())|| lightmode.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.text_image_button_selected_night).getConstantState())) {
            LightDarkMode.setLightsensor(0);
            editor.putString("selected", "light");
        }
        else if(nightmode.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.text_image_button_selected).getConstantState())|| nightmode.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.text_image_button_selected_night).getConstantState())) {
            LightDarkMode.setLightsensor(100);
            editor.putString("selected", "dark");
        }
        setSettingsMode();
        editor.commit();
    }

    public static void setSettingsMode(){
        if(sharedPrefs!=null) {
                float light_val = sharedPrefs.getFloat("lightSensor", 0);
                float threshold = LightDarkMode.getThreshold();


                if (light_val < threshold) {
                    bg.setBackgroundColor(Color.parseColor("#4C4C4C"));
                    bgmusicText.setTextColor(Color.WHITE);
                    volumeText.setTextColor(Color.WHITE);
                    appearanceText.setTextColor(Color.WHITE);
                    setting.setTextColor(Color.WHITE);
                    save.setBackgroundResource(R.drawable.primary_buttonnight);
                    bgMusictoggle.setBackgroundResource(R.drawable.primary_buttonnight);
                    logout.setBackgroundResource(R.drawable.primary_buttonnight);
                    backButton.setImageResource(R.drawable.backbuttonnight);
                    volumeTextView.setTextColor(Color.WHITE);

                    if(sharedPrefs.getString("selected","").equals("dark")) {
                        nightmode.setBackgroundResource(R.drawable.text_image_button_selected_night);
                        auto.setBackgroundResource(R.drawable.text_image_button_night);
                        lightmode.setBackgroundResource(R.drawable.text_image_button_night);
                    }
                    else if(sharedPrefs.getString("selected","").equals("auto")){
                        nightmode.setBackgroundResource(R.drawable.text_image_button_night);
                        auto.setBackgroundResource(R.drawable.text_image_button_selected_night);
                        lightmode.setBackgroundResource(R.drawable.text_image_button_night);
                    }

                    auto.setTextColor(Color.WHITE);
                    auto.setCompoundDrawablesWithIntrinsicBounds(null, autoimage, null, null);
                    lightmode.setTextColor(Color.WHITE);
                    lightmode.setCompoundDrawablesWithIntrinsicBounds(null, lightimage, null, null);

                    nightmode.setTextColor(Color.WHITE);
                    nightmode.setCompoundDrawablesWithIntrinsicBounds(null, nightimage, null, null);


                } else if(light_val>=threshold){
                    bg.setBackgroundColor(Color.WHITE);
                    bgmusicText.setTextColor(Color.BLACK);
                    volumeText.setTextColor(Color.BLACK);
                    appearanceText.setTextColor(Color.BLACK);
                    setting.setTextColor(Color.BLACK);
                    save.setBackgroundResource(R.drawable.primary_button);
                    bgMusictoggle.setBackgroundResource(R.drawable.primary_button);
                    logout.setBackgroundResource(R.drawable.primary_button);
                    backButton.setImageResource(R.drawable.backbutton);
                    volumeTextView.setTextColor(Color.BLACK);


                    if(sharedPrefs.getString("selected","").equals("light")) {
                        auto.setBackgroundResource(R.drawable.text_image_button);
                        lightmode.setBackgroundResource(R.drawable.text_image_button_selected);
                        nightmode.setBackgroundResource(R.drawable.text_image_button);

                    }
                    else if(sharedPrefs.getString("selected","").equals("auto")){
                        auto.setBackgroundResource(R.drawable.text_image_button_selected);
                        lightmode.setBackgroundResource(R.drawable.text_image_button);
                        nightmode.setBackgroundResource(R.drawable.text_image_button);

                    }
                    auto.setTextColor(Color.BLACK);
                    auto.setCompoundDrawablesWithIntrinsicBounds(null, autoimage2, null, null);

                    lightmode.setTextColor(Color.BLACK);
                    lightmode.setCompoundDrawablesWithIntrinsicBounds(null, lightimage2, null, null);

                    nightmode.setTextColor(Color.BLACK);
                    nightmode.setCompoundDrawablesWithIntrinsicBounds(null, nightimage2, null, null);

                }
        }
    }
}
