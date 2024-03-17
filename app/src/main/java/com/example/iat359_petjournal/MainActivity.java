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
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends Activity{

//    declare variables
    ImageButton addPet;
    ImageButton tips;
    ImageButton settings;
    static Button journal;

//    LinearLayout linearLayout;
//    ArrayList<ImageView> imageViewList;

    EditText petName;
    ImageView petSelected;

    View[] images;

    Button edit, delete;

    int breedSelected = 0;

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
        delete = (Button) findViewById(R.id.delete);
//        linearLayout = (LinearLayout) findViewById(R.id.imageViews);


        petName.setBackgroundResource(android.R.color.transparent);
        makeEditable(false, petName);

        sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        intent = new Intent(this, LightDarkMode.class);
        startService(intent);

        bgmusicPlayer = new Intent(this, MusicPlayer.class);
        startService(bgmusicPlayer);

        petSelected.setImageResource(breedSelected);

        createImageViews();
        setMode();

        if(container.getChildCount()<=1){
            delete.setVisibility(View.GONE);
        }
        else{
            delete.setVisibility(View.VISIBLE);
        }


        if(container.getChildCount()==5){
            addPet.setVisibility(View.GONE);
        }
        else{
            addPet.setVisibility(View.VISIBLE);
        }


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

// get pet data, select the petID
        Cursor cursor = db.getPetData();

        int index3 = cursor.getColumnIndex(Constants.PETID);

        ArrayList<String> mArrayList = new ArrayList<String>();
        cursor.moveToFirst();

//        get each of the pet name and pet breed and store in an arraylist
        while (!cursor.isAfterLast()) {
            String petID = cursor.getString(index3);

            mArrayList.add(petID);
            cursor.moveToNext();
        }

        images = new View[mArrayList.size()];
        for(int i=0;i< container.getChildCount();i++){
            String s = mArrayList.get(i);

            images[i] = ((ViewGroup) container).getChildAt(i).findViewById(Integer.parseInt(s));
            images[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v.getId()==Integer.parseInt(s)){
                        String selected = db.getSelectedData(s);
                        String[] results = (selected.split(","));
                        petName.setText(results[0]);
                        petName.setTag(s);
                        breedSelected = createNewImageView(results[1]);
                        petSelected.setImageResource(breedSelected);

                    }
                }
            });

            int finalI = i;
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView img = container.getChildAt(finalI).findViewById(Integer.valueOf(s));

                    if(img.getId()==Integer.parseInt(s)) {
                        db.deletePet(s);
                        container.removeView(img);

                    }
                    if(container.getChildCount()==1){
                        delete.setVisibility(View.GONE);
                    }
                    else {
                        delete.setVisibility(View.VISIBLE);
                    }

                }
            });

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
                        Log.d("okokok",""+petName.getTag());

                        makeEditable(false,petName);
                        edit.setId(R.id.editButton);
                        edit.setText("Edit");
                        if(petName.getTag().equals(s)){
                            db.updatePetNameData(petName.getText().toString(), s);
                        }
                        
                    }
                }
            });
        }


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }

//    sets the dark, light, or auto mode based on user preferences
// this method is called in the beginning to set up the UI and called in the service for any updates to user preferences.
public static void setMode(){
        if(sharedPrefs!=null) {
            float light_val = sharedPrefs.getFloat("lightSensor", 0);
            float threshold = LightDarkMode.getThreshold();

            if (light_val < threshold) {
                journal.setBackgroundColor(Color.BLUE);
            } else {
                journal.setBackgroundColor(Color.RED);
            }
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
 protected void createImageViews(){

//        get all the data from the pet table
     Cursor cursor = db.getPetData();

     int index1 = cursor.getColumnIndex(Constants.NAME);
     int index2 = cursor.getColumnIndex(Constants.TYPE);
     int index3 = cursor.getColumnIndex(Constants.PETID);

     ArrayList<String> mArrayList = new ArrayList<String>();
     cursor.moveToFirst();

//        get each of the pet name and pet breed and store in an arraylist
     while (!cursor.isAfterLast()) {
         String petName = cursor.getString(index1);
         String petType = cursor.getString(index2);
         String petID = cursor.getString(index3);
         String s = petName +"," + petType +","+petID;

         mArrayList.add(s);
         cursor.moveToNext();
     }

     for (int i = 0; i < mArrayList.size(); i++) {
         String s = mArrayList.get(i);
         String[] split = s.split(",");
         String petBreed = split[1];
         breedSelected = createNewImageView(petBreed);
         ImageView imageView = new ImageView(this);
         imageView.setImageResource(breedSelected);

         imageView.setId(Integer.valueOf(split[2]));

//            set the view group to match the parent and wrap the content h x w
         ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                 ViewGroup.LayoutParams.MATCH_PARENT,
                 ViewGroup.LayoutParams.WRAP_CONTENT
         );

//            view group container variable adds a new view based on the newly created imageview and the layout
         container.addView(imageView, params);

     }

     if(cursor.getCount()>0) {
         String s = mArrayList.get(0);
         String[] split = s.split(",");
         String selected = db.getSelectedData(split[2]);
         String[] results = (selected.split(","));
         breedSelected = createNewImageView(results[1]);
         petSelected.setImageResource(breedSelected);
         petName.setText(results[0]);
         petName.setTag(Integer.parseInt(split[2]));
     }

 }

}
