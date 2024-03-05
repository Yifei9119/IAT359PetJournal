package com.example.iat359_petjournal;

import android.app.Activity;
import android.os.Bundle;
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
    }


}
