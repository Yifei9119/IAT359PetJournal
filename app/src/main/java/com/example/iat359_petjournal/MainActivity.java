package com.example.iat359_petjournal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

//    declare variables
    LinearLayout petAvatars;
    ImageButton addPet;
    ImageButton tips;
    ImageButton settings;
    Button journal;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

//        initialize variables
        petAvatars = (LinearLayout) findViewById(R.id.petavatar);
        addPet = (ImageButton) findViewById(R.id.add_pet);
        tips = (ImageButton) findViewById(R.id.tips_advice);
        settings = (ImageButton) findViewById(R.id.settings);
        journal = (Button) findViewById(R.id.journal);

        tips.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), TipsAdvice.class);
                startActivity(intent);
            }
        });
        addPet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), AddPet.class);
                startActivity(intent);
            }
        });

// button listening to gotoSettings method
//    after click start intent to settings class
        settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), Settings.class);
                startActivity(intent);
            }
        });
    }

// button listening to gotojournal method
//    after click start intent to journal class
    protected void gotojournal(View view){
        Intent intent= new Intent(this, Journal.class);
        startActivity(intent);
    }

// button listening to gotoAddPetPage method
//    after click start intent to addPet class
    protected void gotoAddPetPage(View view){
//        Intent intent= new Intent(this, AddPet.class);
//        startActivity(intent);
    }

    protected void gotoTips(View v){
//        Intent intent= new Intent(this, TipsAdvice.class);
//        startActivity(intent);
    }


}