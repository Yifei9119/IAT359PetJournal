package com.example.iat359_petjournal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class Journal extends AppCompatActivity {
//    declare variables
    private RecyclerView myRecycler;
    private MyDatabase db;
    private JournalCustomAdapter customAdapter;
    private MyHelper helper;
    private GridLayoutManager mLayoutManager;

    static SharedPreferences sharedPrefs;
    static LinearLayout bg, header;

    static Button buttonStartCamera;
    static ImageButton back;

    static TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journal);
        buttonStartCamera = findViewById(R.id.Addpost);
        back = findViewById(R.id.backButton);
        bg = findViewById(R.id.bg);
        title = findViewById(R.id.titleText);
        header = findViewById(R.id.header);

        //        get shared prefs
        sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();

        setJournalMode();

//        explicit intent to add photo interface
        buttonStartCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), AddPhoto.class);
                startActivity(i);
            }
        });

//        back button to go to previous page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MainActivity.class);
                startActivity(i);
            }
        });


        myRecycler = (RecyclerView) findViewById(R.id.recyclerView);

        db = new MyDatabase(this);
        helper = new MyHelper(this);

//        gets all the query from the photo table
        Cursor cursor = db.getPhotoData();

        int index2 = cursor.getColumnIndex("Photo");
        int index3 = cursor.getColumnIndex("Size");


        ArrayList<byte[]> bitmapArray = new ArrayList<byte[]>();
        ArrayList<String> sizeArray = new ArrayList<String>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            bitmapArray.add(cursor.getBlob(index2));
            sizeArray.add(cursor.getString(index3));

            cursor.moveToNext();
        }

        customAdapter = new JournalCustomAdapter(bitmapArray, sizeArray);

        myRecycler.setAdapter(customAdapter);

        // use a grid layout manager
        mLayoutManager = new GridLayoutManager(getApplicationContext(),3,LinearLayoutManager.VERTICAL,false);
        myRecycler.setLayoutManager(mLayoutManager);

    }

    public static void setJournalMode(){
        if(sharedPrefs!=null) {
            float light_val = sharedPrefs.getFloat("lightSensor", 0);
            float threshold = LightDarkMode.getThreshold();

            if (light_val < threshold) {
                bg.setBackgroundColor(Color.parseColor("#4C4C4C"));
                back.setImageResource(R.drawable.backbuttonnight);
                title.setTextColor(Color.parseColor("#695C54"));
                header.setBackgroundColor(Color.parseColor("#BCBCBC"));
                buttonStartCamera.setBackgroundResource(R.drawable.primary_buttonnight);

            } else {
                bg.setBackgroundColor(Color.WHITE);
                title.setTextColor(Color.parseColor("#BC7245"));
                back.setImageResource(R.drawable.backbutton);
                header.setBackgroundColor(Color.parseColor("#F7F2EE"));
                buttonStartCamera.setBackgroundResource(R.drawable.primary_button);

            }
        }
    }

}
