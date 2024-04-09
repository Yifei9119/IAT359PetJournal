package com.example.iat359_petjournal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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
    Button buttonStartCamera;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journal);
        buttonStartCamera = findViewById(R.id.Addpost);
        back = findViewById(R.id.backButton);

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


        ArrayList<byte[]> bitmapArray = new ArrayList<byte[]>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            bitmapArray.add(cursor.getBlob(index2));
            cursor.moveToNext();
        }

        customAdapter = new JournalCustomAdapter(bitmapArray);

        myRecycler.setAdapter(customAdapter);

        // use a grid layout manager
        mLayoutManager = new GridLayoutManager(getApplicationContext(),3,LinearLayoutManager.VERTICAL,false);
        myRecycler.setLayoutManager(mLayoutManager);

    }

}
