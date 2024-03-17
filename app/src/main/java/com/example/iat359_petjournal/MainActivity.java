package com.example.iat359_petjournal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

//    declare variables
    ImageButton addPet;
    ImageButton tips;
    ImageButton settings;
    static Button journal;

    EditText petName;
    ImageView petSelected;

    Button edit;

    int breedSelected;

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
        petName = (EditText) findViewById(R.id.petName);
        petSelected = (ImageView) findViewById(R.id.petSelected);
        edit = (Button) findViewById(R.id.editButton);


        petName.setBackgroundResource(android.R.color.transparent);
        makeEditable(false, petName);

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


//      edit button after click make edittext clickable for user to edit the text
//      update the text based on user input
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.editButton) {
                    makeEditable(true, petName);
                    edit.setId(R.id.saveButton);
                    edit.setText("Save");
                }
                else if(v.getId()==R.id.saveButton){
                    String id = "1";
                    makeEditable(false,petName);
                    edit.setId(R.id.editButton);
                    edit.setText("Edit");

                    Log.d("help", ""+petName.getId());
                    if(petName.getId()==R.id.name1){
                        id = "1";
                    }
                    else if(petName.getId()==R.id.name2){
                        id = "2";
                    }
                    if(petName.getId()==R.id.name3){
                        id = "3";
                    }
                    if(petName.getId()==R.id.name4){
                        id = "4";
                    }
                    if(petName.getId()==R.id.name5){
                        id = "5";
                    }
                    db.updatePetNameData(petName.getText().toString(), id);



                }
            }
        });

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
            petName.setId(R.id.name1);

        }
        else if (v.getTag().equals(String.valueOf(2))) {
            selectedImage=2;
            petName.setId(R.id.name2);

        }
        else if (v.getTag().equals(String.valueOf(3))) {
            selectedImage=3;
            petName.setId(R.id.name3);

        }
        else if (v.getTag().equals(String.valueOf(4))) {
            selectedImage=4;
            petName.setId(R.id.name4);

        }
        else if (v.getTag().equals(String.valueOf(5))) {
            selectedImage=5;
            petName.setId(R.id.name5);

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
        Log.d("LOG",results[1]);

        breedSelected = createNewImageView(results[1]);
        Log.d("LOG", ""+breedSelected);
        petSelected.setImageResource(breedSelected);


//        for(int i = 0; i<tag.length;i++){
//            if(v.getTag().equals(String.valueOf(tag[i]))){

//            }
//        }

    }

    private void makeEditable(boolean isEditable, EditText et){
        if(isEditable){
            et.setFocusable(true);
            et.setEnabled(true);
            et.setClickable(true);
            et.setFocusableInTouchMode(true);
            et.setBackgroundResource(R.drawable.underline);
            et.setKeyListener(new EditText(getApplicationContext()).getKeyListener());

        }else{
            et.setFocusable(false);
            et.setClickable(false);
            et.setFocusableInTouchMode(false);
            et.setBackground(null);
            et.setKeyListener(null);

        }
    }


}
