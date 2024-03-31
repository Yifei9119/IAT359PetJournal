package com.example.iat359_petjournal;

import android.content.Intent;
import android.nfc.FormatException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEvent extends AppCompatActivity implements TextWatcher {
    private EditText taskEdit, startTimeEdit, endTimeEdit;
    private int startTime, endTime;
    private String taskName;
    MyDatabase db;
    private Button addEventButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        taskEdit = findViewById(R.id.taskEditText);
        taskEdit.addTextChangedListener(this);
        startTimeEdit = findViewById(R.id.startTimeEdit);
        startTimeEdit.addTextChangedListener(this);
        endTimeEdit = findViewById(R.id.endTimeEdit);
        endTimeEdit.addTextChangedListener(this);
        addEventButton = findViewById(R.id.addButton);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskName != null && startTime != 0 && endTime != 0) {
                    db.insertEventData(taskName, startTime, endTime);
//                    Log.d("mylog", "inserted data:" + taskName + startTime + endTime);
                    Intent intent= new Intent(v.getContext(), Schedule.class);
                    startActivity(intent);
                }
                else {
//                    if missing either
                    Toast.makeText(v.getContext(), "Please enter a task and the start and end time", Toast.LENGTH_SHORT).show();
                }


            }
        });
        db = new MyDatabase(this);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (taskEdit.isFocused()){
            taskName = s.toString();
        }

        if (startTimeEdit.isFocused()){
            try {
                startTime = (int) Double.parseDouble(s.toString());
            }
            catch (NumberFormatException e){
                startTime = 0;
            }
        }

        if (endTimeEdit.isFocused()){
            try {
                endTime = (int) Double.parseDouble(s.toString());
            }
            catch (NumberFormatException e){
                startTime = 0;
            }
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
