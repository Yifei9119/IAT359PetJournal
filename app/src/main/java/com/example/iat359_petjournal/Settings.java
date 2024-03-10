package com.example.iat359_petjournal;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
//      initialize variables
        Button logout = findViewById(R.id.logout);
        ImageButton backButton = findViewById(R.id.backButton);
        ToggleButton bgMusictoggle = findViewById(R.id.toggleButton);

        bgMusictoggle.setOnCheckedChangeListener(listener);


        //       when logout button is clicked go to login.class
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Login.class);
                startActivity(intent);
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

    }

CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
@Override
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    Intent bgMusicPlayer = new Intent(buttonView.getContext(), MusicPlayer.class);
        if(isChecked){
            startService(bgMusicPlayer);
        }
        else{
        stopService(bgMusicPlayer);
        }
    }};
}
