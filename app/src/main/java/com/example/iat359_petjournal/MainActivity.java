package com.example.iat359_petjournal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class MainActivity extends Activity{

    //    declare variables
    static ImageButton addPet;
    static ImageButton settings;
    static ImageButton schedule;
    static Button journal;
    static Button tips;

    static LinearLayout upcomings;


    static ConstraintLayout bg;


    static Boolean start = true;

    static EditText petName;
    ImageView petSelected, petGender;

    View[] images;

    static Button edit;
    ImageButton delete;

    int breedSelected = 0;

    int genderSelected = 0;

    MyDatabase db;
    MyHelper helper;


    Intent intent;
    static SharedPreferences sharedPrefs;
    Intent bgmusicPlayer;
    ViewGroup container;
    Date currentDate;

    String currentTime;


    TextView upcomingTaskTime, upcomingTask;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

// initialize viewgroup that's a linearlayout
        container = (ViewGroup) findViewById(R.id.petavatar);

//initialize database
        db = new MyDatabase(this);
        helper = new MyHelper(this);

        currentDate = Calendar.getInstance().getTime();
        String currentTimeString = currentDate.toString();
        String[] results = currentTimeString.split(" ");
        String[] timeResults = results[3].split(":");
        currentTime = timeResults[0] + ":" + timeResults[1];


        // initialize variables
        addPet = (ImageButton) findViewById(R.id.add_pet);
        tips = (Button) findViewById(R.id.tips_advice);
        settings = (ImageButton) findViewById(R.id.settings);
        journal = (Button) findViewById(R.id.journal);
        schedule = (ImageButton) findViewById(R.id.schedule);
        petName = (EditText) findViewById(R.id.petName);
        petSelected = (ImageView) findViewById(R.id.petSelected);
        petGender = (ImageView) findViewById(R.id.gender);
        edit = (Button) findViewById(R.id.editButton);
        delete = (ImageButton) findViewById(R.id.delete);
        upcomingTask = findViewById(R.id.recentTask);
        upcomingTaskTime = findViewById(R.id.recentTaskTime);
        upcomings = findViewById(R.id.upcomings);

        bg = (ConstraintLayout) findViewById(R.id.bg);

//show and set non editable text
        petName.setBackgroundResource(android.R.color.transparent);
        makeEditable(false, petName);

//        get shared prefs
        sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

//start service to detect light sensor value
        intent = new Intent(this, LightDarkMode.class);
        startService(intent);

//        boolean variable that is controlled by music player activity
//        auto play bg music when logging in
        if (start) {
            bgmusicPlayer = new Intent(this, MusicPlayer.class);
            startService(bgmusicPlayer);
        }

// creates new image views for the pet avatars
        createImageViews();
// set the dark and light mode using journal button as a placeholder
        setMode();

        if (container.getChildCount() <= 1) {
            delete.setVisibility(View.GONE);
        } else {
            delete.setVisibility(View.VISIBLE);
        }


        if (container.getChildCount() == 5) {
            addPet.setVisibility(View.GONE);
        } else {
            addPet.setVisibility(View.VISIBLE);
        }


//    image button listening on click
//    after click start intent to journal class
        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TipsAdvice.class);
                startActivity(intent);
            }
        });

// add image button listening to onclick
//    after click start intent to addPet class
        addPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddPet.class);
                startActivity(intent);
                stopService(bgmusicPlayer);
            }
        });

// journal button listens to onclick
//    after click start intent to journal class
        journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Journal.class);
                startActivity(intent);
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Schedule.class);
                intent.putExtra("petName", petName.getText().toString());
                startActivity(intent);
            }
        });

// settings image button listens to onclick
//    after click start intent to settings class
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Settings.class);
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
//for loop for all the imageviews inside the viewgroup
        images = new View[container.getChildCount()];
        for (int i = 0; i < container.getChildCount(); i++) {
            String s = mArrayList.get(i);

            images[i] = ((ViewGroup) container).getChildAt(i).findViewById(Integer.parseInt(s));
//            once each of them are clicked check whether the petID stored in the arraylist matches with the imageView
//            if so show it as the selected pet
            images[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == Integer.parseInt(s)) {
                        String selected = db.getSelectedData(s);
                        String[] results = selected.split(",");
                        String[] results2 = results[2].split("\n");
                        petName.setText(results[0]);
                        petName.setTag(s);
                        int breedSelected = selectedAvatar(results[1]);
                        int genderSelected = selectedGender(results2[0]);

                        petSelected.setImageResource(breedSelected);
                        petGender.setImageResource(genderSelected);

                        setUpcomingSchedule(petName);

                    }
                }
            });


        }


//delete button deletes the pet in db based on the selected pet
//the avatar on the left is also removed
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView img = findViewById(Integer.valueOf(petName.getTag().toString()));

                db.deletePet(petName.getTag().toString());
                container.removeView(img);

//                    get the last child from the container to show as the selected pet avatar
                View nextChild = ((ViewGroup) container).getChildAt(container.getChildCount()-1);

                String selected = db.getSelectedData(String.valueOf(nextChild.getId()));
                String[] results = selected.split(",");
                String[] results2 = results[1].split("\n");

                int breedSelected = selectedAvatar(results2[0]);
                petSelected.setImageResource(breedSelected);

                petName.setText(results[0]);
                petName.setTag(nextChild.getId());



//                    delete button is only visible when there is more than 1
                if (container.getChildCount() <= 1) {
                    delete.setVisibility(View.GONE);
                } else {
                    delete.setVisibility(View.VISIBLE);
                }

            }
        });

        //edit button after click make edittext clickable for user to edit the text
        //update the text based on user input
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.editButton) {
//                        change save to edit button and change the editability of pet name to true
                    makeEditable(true, petName);
                    edit.setId(R.id.saveButton);
                    edit.setText("Save");
                } else if (v.getId() == R.id.saveButton) {
//                        change edit to save button and change the petname non editable
                    makeEditable(false, petName);
                    edit.setId(R.id.editButton);
                    edit.setText("Edit");

//                        update database
                    db.updatePetNameData(petName.getText().toString(), petName.getTag().toString());

                }
            }
        });

        setUpcomingSchedule(petName);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }

    // sets the dark, light, or auto mode based on user preferences
// this method is called in the beginning to set up the UI and called in the service for any updates to user preferences.
    public static void setMode(){
        if(sharedPrefs!=null) {
            float light_val = sharedPrefs.getFloat("lightSensor", 0);
            float threshold = LightDarkMode.getThreshold();

            if (light_val < threshold) {
                journal.setBackgroundResource(R.drawable.primary_buttonnight);
                bg.setBackgroundResource(R.drawable.background_dark);
                addPet.setImageResource(R.drawable.addnight);
                settings.setImageResource(R.drawable.settingnight);
                tips.setBackgroundResource(R.drawable.primary_buttonnight);
                upcomings.setBackgroundResource(R.drawable.upcoming_schedule_dark);
                edit.setBackgroundResource(R.drawable.primary_buttonnight);

            } else {
                journal.setBackgroundResource(R.drawable.primary_button);
                bg.setBackgroundResource(R.drawable.background_light);
                addPet.setImageResource(R.drawable.add);
                settings.setImageResource(R.drawable.setting);
                tips.setBackgroundResource(R.drawable.primary_button);
                upcomings.setBackgroundResource(R.drawable.upcoming_schedule);
                edit.setBackgroundResource(R.drawable.primary_button);

            }
        }
    }

//    handles the string to see which gender the pet is
    private Integer selectedGender(String text) {
        switch (text) {
            case "Female":
                genderSelected = R.drawable.female_vector;
                break;

            case "Male":
                genderSelected = R.drawable.male_vector;
                break;
        }
        return genderSelected;
    }

    //handles the string to see which dog breed is selected
    private Integer selectedAvatar(String text) {

        switch (text) {
            case "German Shepherd":
                breedSelected = R.drawable.germansheperd;
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

    //    avatars for users to click on
    private Integer createAvatars(String text) {

        switch (text) {
            case "German Shepherd":
                breedSelected = R.drawable.germanavatar;
                break;

            case "Poodle":
                breedSelected = R.drawable.poodleavatar;
                break;

            case "Golden Retriever":
                breedSelected = R.drawable.retrieveravatar;
                break;
        }

        return breedSelected;
    }


    //function to determine whether the edit text for the dog name is editable
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

    //function to set upcoming schedule to the most recent schedule based on the current time
    private void setUpcomingSchedule(EditText name){

        Cursor cursor = db.getEventData(name.getText().toString());

        if (cursor.getCount() > 0) {
            int index1 = cursor.getColumnIndex(Constants.TASK);
            int index2 = cursor.getColumnIndex(Constants.START_TIME);
            int index3 = cursor.getColumnIndex(Constants.END_TIME);

            //obtaining the current time
            currentDate = Calendar.getInstance().getTime();

            String currentTimeString = currentDate.toString();
            String[] results = currentTimeString.split(" ");
            String[] timeResults = results[3].split(":");


            //compare the current time with the obtain task's startTime
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String startTime = cursor.getString(index2);
                String endTime = cursor.getString(index3);
                String s = startTime + " - " + endTime;
                String task = cursor.getString(index1);
                if ((timeResults[0] + ":" + timeResults[1]).compareTo(startTime) < 0) {
                    //set upcoming schedule's text to most recet task's information
                    upcomingTaskTime.setText(s);
                    upcomingTask.setText(task);
                    break;
                }
                cursor.moveToNext();

            }

        }else{
            upcomingTaskTime.setText("No Upcoming Task");
            upcomingTask.setText("");
        }


    }


    //    creates new image views based on all the pet data
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

//for loop to set each of the imageviews avatars based on the dog breed
        for (int i = 0; i < mArrayList.size(); i++) {
            String s = mArrayList.get(i);
            String[] split = s.split(",");
            String petBreed = split[1];
            int breedSelected = createAvatars(petBreed);
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(breedSelected);

            imageView.setId(Integer.valueOf(split[2]));

//            set the view group to match the parent and wrap the content h x w
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 30);

//            view group container variable adds a new view based on the newly created imageview and the layout
            container.addView(imageView, params);

        }

//     if there's over 0 in the database then you show the default selected first pet on the list
        if(cursor.getCount()>0) {
            String s = null;
            if(mArrayList.size()>1) {
                s = mArrayList.get(mArrayList.size() - 1);
            }
            else if (mArrayList.size()==1){
                s = mArrayList.get(0);
            }
            String[] split = s.split(",");
            String selected = db.getSelectedData(split[2]);
            String[] results = selected.split(",");
            String[] gendertype = results[2].split("\n");
            int breedSelected = selectedAvatar(results[1]);
            petSelected.setImageResource(breedSelected);

            int gender = selectedGender(gendertype[0]);
            petGender.setImageResource(gender);

            petName.setText(results[0]);
            petName.setTag(Integer.parseInt(split[2]));
        }

    }

    // sets the boolean of start
    public static void setStart(Boolean edit){
        start = edit;
    }


}