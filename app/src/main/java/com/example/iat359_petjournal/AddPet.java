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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddPet extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText dogNameEditText;
    private EditText yearEditText, monthEditText, dayEditText;
//    private String year, month, day;
    private Button addPetButton;

    private String dogBreed;
    MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpet);

        db = new MyDatabase(this);

        dogNameEditText = findViewById(R.id.dogNameEditText);
        yearEditText = findViewById(R.id.yearEditText);
        monthEditText = findViewById(R.id.monthEditText);
        dayEditText = findViewById(R.id.dayEditText);
        addPetButton = findViewById(R.id.addPetButton);
        addPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dogName = dogNameEditText.getText().toString();

                if(dogBreed != null && !dogName.isEmpty()) {
                    db.insertPetData(dogName, dogBreed);
                    Intent intent= new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }


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
        dogBreed = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
