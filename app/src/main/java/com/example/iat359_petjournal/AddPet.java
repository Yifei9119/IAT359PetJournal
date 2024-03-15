package com.example.iat359_petjournal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddPet extends AppCompatActivity implements TextWatcher, AdapterView.OnItemSelectedListener {
    private EditText dogNameEditText;
    private String dogName;
    private EditText yearEditText, monthEditText, dayEditText;
    private String year, month, day;
    private Button addPetButton;
    MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpet);

        db = new MyDatabase(this);

        dogNameEditText = findViewById(R.id.dogNameEditText);
        dogNameEditText.addTextChangedListener(this);
        yearEditText = findViewById(R.id.yearEditText);
        yearEditText.addTextChangedListener(this);
        monthEditText = findViewById(R.id.monthEditText);
        dayEditText = findViewById(R.id.dayEditText);
        addPetButton = findViewById(R.id.addPetButton);
        addPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mylog", "onClick");
                long id = db.insertPetData(dogName, year);
            }
        });
        Spinner dropdown = findViewById(R.id.spinner);
        String[] items = new String[]{"German Shepherd", "Golden Retriever", "Poodle"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

//        Button homePage = findViewById(R.id.addPetButton);
//        homePage.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(v.getContext(), MainActivity.class);
//                startActivity(intent);
//            }
//        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (dogNameEditText.isFocused()){
            dogName = s.toString();
        }
        if (yearEditText.isFocused()){
            year = s.toString();
        }
        if (monthEditText.isFocused()){
            month = s.toString();
        }
        if (dayEditText.isFocused()){
            day = s.toString();
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
