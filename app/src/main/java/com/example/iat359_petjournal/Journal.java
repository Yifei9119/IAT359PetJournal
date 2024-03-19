package com.example.iat359_petjournal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Journal extends AppCompatActivity {

    Button buttonStartCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journal);
        buttonStartCamera = findViewById(R.id.Addpost);

//        explicit intent to add photo interface
        buttonStartCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), AddPhoto.class);
                startActivity(i);
            }
        });
    }


}
