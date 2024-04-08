package com.example.iat359_petjournal;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.nfc.FormatException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class AddEvent extends AppCompatActivity implements TextWatcher {
    //declear variables
    private EditText taskEdit;
    private String date, startTime, endTime;
    private String taskName;
    MyDatabase db;
    private Button addEventButton, cancelButton;

    private DatePickerDialog datePickerDialog;
    private Button selectDateButton, startTimeButton, endTimeButton;

    int hour, minute;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);
        //call date picker function
        initDatePicker();

        //defining variables
        taskEdit = findViewById(R.id.taskEditText);
        taskEdit.addTextChangedListener(this);
        selectDateButton = findViewById(R.id.selectDateButton);
        selectDateButton.setText(getTodaysDate());
        //set onclick listener to buttons
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            //show datepicker pop up when clicked
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        startTimeButton = findViewById(R.id.startTimeButton);
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            //initiate start time picker when clicked
            @Override
            public void onClick(View v) {
                initTimePicker();
            }
        });

        endTimeButton = findViewById(R.id.endTimeButton);
        endTimeButton.setOnClickListener(new View.OnClickListener() {
            //initiate end time picker when clicked
            @Override
            public void onClick(View v) {
                initTimePicker2();
            }
        });

        addEventButton = findViewById(R.id.addButton);
        //set onclick listener for addevent button
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String petName = getIntent().getStringExtra("petName");
                Log.d("myeventlog", "" + petName);
                //if the task name, date, start and end time isn't null, save data to SQLite
                if(taskName != null && date != null && startTime != null && endTime != null) {
                    db.insertEventData(taskName, date, startTime, endTime, petName);
                    Intent intent= new Intent(v.getContext(), Schedule.class);
                    intent.putExtra("petName", petName);
                    startActivity(intent);
                }
                else {
//                    if information missing, display toast message to inform user
                    Toast.makeText(v.getContext(), "Please enter a task, date and the start and end time", Toast.LENGTH_SHORT).show();
                }

            }
        });
        cancelButton = findViewById(R.id.cancelButton);
        //set onclick listener for cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            //if cancel button pressed, go back to home page
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), Schedule.class);
                startActivity(intent);
            }
        });
        db = new MyDatabase(this);

    }

    //function to obtain today's date
    private String getTodaysDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        date = makeDateString(day, month, year);
        return makeDateString(day, month, year);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month = month +1;
                date = makeDateString(day, month, year);
                selectDateButton.setText(date);
            }

        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

    }

    private void initTimePicker(){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                startTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                startTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("selected Time");
        timePickerDialog.show();
    }

    private void initTimePicker2(){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                endTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                endTime = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("selected Time");
        timePickerDialog.show();
    }

    private String makeDateString(int day, int month, int year){
        String dayString = "";
        if (day < 10) dayString = "0" + day;
        else dayString = "" + day;
        return getMonthFormat(month) + " " + dayString + " " + year;

    }

    private String getMonthFormat(int month){
        if (month == 1) return "Jan";
        if (month == 2) return "Feb";
        if (month == 3) return "Mar";
        if (month == 4) return "Apr";
        if (month == 5) return "May";
        if (month == 6) return "Jun";
        if (month == 7) return "Jul";
        if (month == 8) return "Aug";
        if (month == 9) return "Sept";
        if (month == 10) return "Oct";
        if (month == 11) return "Nov";
        if (month == 12) return "Dec";

        return "Jan";

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (taskEdit.isFocused()){
            taskName = s.toString();
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
