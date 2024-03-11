package com.example.iat359_petjournal;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    SeekBar volume;
    int currentVolume;
    TextView volumeTextView;
    AudioManager audio;
    Intent bgMusicPlayer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
//      initialize variables
        Button logout = findViewById(R.id.logout);
        ImageButton backButton = findViewById(R.id.backButton);
        ToggleButton bgMusictoggle = findViewById(R.id.toggleButton);
        volume = (SeekBar) findViewById(R.id.seekBar);
        volumeTextView = (TextView) findViewById(R.id.volume);
        bgMusicPlayer = new Intent(this, MusicPlayer.class);

        Button auto = (Button) findViewById(R.id.autoButton);
        Button nightmode = (Button) findViewById(R.id.DarkMode);
        Button lightmode = (Button) findViewById(R.id.LightMode);



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
        auto.setBackgroundResource(R.drawable.text_image_button_selected);

        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto.setBackgroundResource(R.drawable.text_image_button_selected);
                lightmode.setBackgroundResource(R.drawable.text_image_button);
                nightmode.setBackgroundResource(R.drawable.text_image_button);
            }
        });
        nightmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto.setBackgroundResource(R.drawable.text_image_button);
                lightmode.setBackgroundResource(R.drawable.text_image_button);
                nightmode.setBackgroundResource(R.drawable.text_image_button_selected);
            }
        });

        lightmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto.setBackgroundResource(R.drawable.text_image_button);
                lightmode.setBackgroundResource(R.drawable.text_image_button_selected);
                nightmode.setBackgroundResource(R.drawable.text_image_button);
            }
        });


    }

CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
@Override
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            startService(bgMusicPlayer);
        }
        else{
        stopService(bgMusicPlayer);
        }
    }};
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
}
