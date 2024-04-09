package com.example.iat359_petjournal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private static Button addButton;


    private static ImageButton returnButton;
    static LinearLayout bg, header;
    static TextView title;
    private String petName;
    static SharedPreferences sharedPrefs;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        myRecycler = findViewById(R.id.scheduleRecycler);
        bg = findViewById(R.id.bg);
        title = findViewById(R.id.titleText);
        header = findViewById(R.id.header);
//        get shared prefs
        sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPrefs.edit();
        db = new MyDatabase(this);
        helper = new MyHelper(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            petName = bundle.getString("petName");
            Log.d("mylog", "Schedule " + petName);
        }

        title.setText(petName + "'s Schedule");

        Cursor cursor = db.getEventData(petName);

        int index0 = cursor.getColumnIndex(Constants.TASKID);
        int index1 = cursor.getColumnIndex(Constants.TASK);
        int index2 = cursor.getColumnIndex(Constants.START_TIME);
        int index3 = cursor.getColumnIndex(Constants.END_TIME);


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
                intent.putExtra("petName", petName);
                startActivity(intent);
            }
        });

        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        setScheduleMode();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public static void setScheduleMode(){
        if(sharedPrefs!=null) {
            float light_val = sharedPrefs.getFloat("lightSensor", 0);
            float threshold = LightDarkMode.getThreshold();

            if (light_val < threshold) {
                bg.setBackgroundColor(Color.parseColor("#4C4C4C"));
                title.setTextColor(Color.parseColor("#695C54"));
                header.setBackgroundColor(Color.parseColor("#BCBCBC"));
                returnButton.setImageResource(R.drawable.backbuttonnight);
                addButton.setBackgroundResource(R.drawable.primary_buttonnight);



            } else {
                bg.setBackgroundColor(Color.WHITE);
                title.setTextColor(Color.parseColor("#BC7245"));
                header.setBackgroundColor(Color.parseColor("#F7F2EE"));
                returnButton.setImageResource(R.drawable.backbutton);
                addButton.setBackgroundResource(R.drawable.primary_button);

            }
        }
    }
}
