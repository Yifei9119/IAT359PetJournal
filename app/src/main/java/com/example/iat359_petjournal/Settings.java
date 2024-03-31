package com.example.iat359_petjournal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
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
    TextView volumeTextView;
    static TextView bgmusicText;
    static TextView appearanceText;
    static TextView volumeText;

    static TextView setting;
    AudioManager audio;
    Intent bgMusicPlayer;
    ToggleButton bgMusictoggle;

    static LinearLayout bg;

    static SharedPreferences sharedPrefs;

    Button auto, nightmode, lightmode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //      initialize variables
        Button logout = findViewById(R.id.logout);
        ImageButton backButton = findViewById(R.id.backButton);
        bgMusictoggle = findViewById(R.id.toggleButton);
        volume = (SeekBar) findViewById(R.id.seekBar);
        volumeTextView = (TextView) findViewById(R.id.volume);
        bgmusicText = (TextView) findViewById(R.id.bgmusic);
        appearanceText = (TextView) findViewById(R.id.appearance);
        volumeText = (TextView) findViewById(R.id.volumetext);
        setting = (TextView) findViewById(R.id.setting);

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

        if(v == findViewById(R.id.autoButton)){
            auto.setBackgroundResource(R.drawable.text_image_button_selected);
            lightmode.setBackgroundResource(R.drawable.text_image_button);
            nightmode.setBackgroundResource(R.drawable.text_image_button);

        }
        else if(v == findViewById(R.id.LightMode)){
            auto.setBackgroundResource(R.drawable.text_image_button);
            lightmode.setBackgroundResource(R.drawable.text_image_button_selected);
            nightmode.setBackgroundResource(R.drawable.text_image_button);

        }
        else if(v == findViewById(R.id.DarkMode)){
            auto.setBackgroundResource(R.drawable.text_image_button);
            lightmode.setBackgroundResource(R.drawable.text_image_button);
            nightmode.setBackgroundResource(R.drawable.text_image_button_selected);
        }

    }

    public void saveSettings(View view){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        if (auto.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.text_image_button_selected).getConstantState())){
            LightDarkMode.setLightsensor(6);
            editor.putString("selected", "auto");
        }
        else if(lightmode.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.text_image_button_selected).getConstantState())) {
            LightDarkMode.setLightsensor(0);
            editor.putString("selected", "light");
        }
        else if(nightmode.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.text_image_button_selected).getConstantState())) {
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
                    bg.setBackgroundColor(Color.parseColor("#282828"));
                    bgmusicText.setTextColor(Color.WHITE);
                    volumeText.setTextColor(Color.WHITE);
                    appearanceText.setTextColor(Color.WHITE);
                    setting.setTextColor(Color.WHITE);
                } else {
                    bg.setBackgroundColor(Color.WHITE);
                    bgmusicText.setTextColor(Color.BLACK);
                    volumeText.setTextColor(Color.BLACK);
                    appearanceText.setTextColor(Color.BLACK);
                    setting.setTextColor(Color.BLACK);
                }
        }
    }
}
