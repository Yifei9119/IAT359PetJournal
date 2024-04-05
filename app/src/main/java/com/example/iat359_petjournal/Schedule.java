package com.example.iat359_petjournal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Schedule extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private RecyclerView myRecycler;
    private MyDatabase db;
    private CustomAdapter customAdapter;
    private MyHelper helper;
    private LinearLayoutManager mLayoutManager;
    private Button addButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        myRecycler = findViewById(R.id.scheduleRecycler);

        db = new MyDatabase(this);
        helper = new MyHelper(this);

        Cursor cursor = db.getEventData();

        int index1 = cursor.getColumnIndex(Constants.TASK);
        int index2 = cursor.getColumnIndex(Constants.START_TIME);
        int index3 = cursor.getColumnIndex(Constants.END_TIME);
        int index0 = cursor.getColumnIndex(Constants.TASKID);

        ArrayList<String> mArrayList = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String task = cursor.getString(index1);
            String startTime = cursor.getString(index2);
            String endTime = cursor.getString(index3);
            String taskId = cursor.getString(index0);
            String s = taskId + "," + task +"," + startTime +"," +endTime;
            mArrayList.add(s);
            cursor.moveToNext();
        }

        customAdapter = new CustomAdapter(mArrayList);
        myRecycler.setAdapter(customAdapter);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        myRecycler.setLayoutManager(mLayoutManager);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddEvent.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
