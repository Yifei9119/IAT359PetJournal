package com.example.iat359_petjournal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

//    declare variables
    ImageButton addPet;
    ImageButton tips;
    ImageButton settings;
    static Button journal;

    TextView petName;
    ImageView petSelected;

    int breedSelected =1;

    MyDatabase db;
    MyHelper helper;

    String[] tag;
    int selectedImage = 1;


    Intent intent;
    static SharedPreferences sharedPrefs;
    Intent bgmusicPlayer;
    ViewGroup container;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

// initialize viewgroup that's a linearlayout
        container = (ViewGroup) findViewById(R.id.petavatar);

        db = new MyDatabase(this);
        helper = new MyHelper(this);


        // initialize variables
        addPet = (ImageButton) findViewById(R.id.add_pet);
        tips = (ImageButton) findViewById(R.id.tips_advice);
        settings = (ImageButton) findViewById(R.id.settings);
        journal = (Button) findViewById(R.id.journal);
        petName = (TextView) findViewById(R.id.petName);
        petSelected = (ImageView) findViewById(R.id.petSelected);


        sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        intent = new Intent(this, LightDarkMode.class);
        startService(intent);

        bgmusicPlayer = new Intent(this, MusicPlayer.class);
        startService(bgmusicPlayer);

//        get all the data from the pet table

        Cursor cursor = db.getPetData();

        int index1 = cursor.getColumnIndex(Constants.NAME);
        int index2 = cursor.getColumnIndex(Constants.TYPE);

        ArrayList<String> mArrayList = new ArrayList<String>();
        cursor.moveToFirst();

//        get each of the pet name and pet breed and store in an arraylist
        while (!cursor.isAfterLast()) {
            String petName = cursor.getString(index1);
            String petType = cursor.getString(index2);
            String s = petName +"," + petType;

            mArrayList.add(s);
            cursor.moveToNext();
        }

        tag = new String[mArrayList.size()];

        for (int i = 0; i < mArrayList.size(); i++) {
            String s = mArrayList.get(i);
            String[] split = s.split(",");
            String petBreed = split[1];


            tag[i] = String.valueOf(i+1);
//            call the createNewImageView method to add a new ImageView based on how many items are in the array list
//            pass the petbreed and handle it in the method

            breedSelected = createNewImageView(petBreed);
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(breedSelected);

            imageView.setTag(tag[i]);
            imageView.setOnClickListener(this);



//            set the view group to match the parent and wrap the content h x w
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

//            view group container variable adds a new view based on the newly created imageview and the layout
            container.addView(imageView, params);

        }
        String selected = db.getSelectedData(String.valueOf(selectedImage));
        String[]  results = (selected.split(","));
        petName.setText(results[0]);
        breedSelected = createNewImageView(results[1]);
        petSelected.setImageResource(breedSelected);


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
                stopService(bgmusicPlayer);
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
    private Integer createNewImageView(String text) {

        Log.d("tag", ""+text);

        switch (text) {
            case "German Shepherd":
                breedSelected = R.drawable.germansherperd;
                break;

                case "Poodle":
                breedSelected = R.drawable.poodle;
                break;

            case "Golden Retriever":
                breedSelected = R.drawable.retriever;
                break;


        }

        return breedSelected;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals(String.valueOf(1))) {
                selectedImage=1;
        }
        else if (v.getTag().equals(String.valueOf(2))) {
            selectedImage=2;
        }
        else if (v.getTag().equals(String.valueOf(3))) {
            selectedImage=3;
        }
        else if (v.getTag().equals(String.valueOf(4))) {
            selectedImage=4;
        }
        else if (v.getTag().equals(String.valueOf(5))) {
            selectedImage=5;
        }
//        String selected = db.getSelectedData(String.valueOf(selectedImage));
//        String[]  results = (selected.split(","));
//        petName.setText(results[0]);
//        breedSelected = createNewImageView(results[1]);

//        if(results[1].equals("German Shepherd")){
//            breedSelected = R.drawable.germansherperd;
//        }
//        else if(results[1].equals("Poodle")){
//            breedSelected = R.drawable.poodle;
//            Log.d("tag", ""+breedSelected);
//        }
//        else if(results[1].equals("Golden Retriever")) {
//            breedSelected = R.drawable.retriever;
//            Log.d("tag", "retriever");
//        }

        String selected = db.getSelectedData(String.valueOf(selectedImage));
        String[]  results = (selected.split(","));
        petName.setText(results[0]);
        breedSelected = createNewImageView(results[1]);
        petSelected.setImageResource(breedSelected);


//        for(int i = 0; i<tag.length;i++){
//            if(v.getTag().equals(String.valueOf(tag[i]))){

//            }
//        }

    }
}
