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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddPet extends AppCompatActivity implements AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener{
//  initialize variables
    private EditText dogNameEditText;
    private EditText yearEditText, monthEditText, dayEditText;
    private RadioGroup gender;
    private Button addPetButton;

    private ImageButton cancelButton;

    private String dogBreed;

    private String genderType;
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
        cancelButton = findViewById(R.id.cancelButton);
        gender = findViewById(R.id.gender);
        gender.setOnCheckedChangeListener(this);
        RadioButton female = (RadioButton) findViewById(R.id.female);
        female.setChecked(true);

//        after clicking on add pet retrieve the name of the dog and dog breed and insert to db
        addPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dogName = dogNameEditText.getText().toString();

                if(dogBreed != null && !dogName.isEmpty() && !genderType.isEmpty()) {
                    db.insertPetData(dogName, dogBreed, genderType);
                    Intent intent= new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }
                else {
//                    if missing either dogbreed or dogname selection show toast
                    Toast.makeText(v.getContext(), "Please enter a dog name and select a breed", Toast.LENGTH_SHORT).show();
                }


            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }

        });


//        dropdown button for breed selection
        Spinner dropdown = findViewById(R.id.spinner);
        String[] items = new String[]{"German Shepherd", "Golden Retriever", "Poodle"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);




    }
// the dogbreed would be the selected item of the dropdown
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        dogBreed = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == R.id.female) {
            genderType = "Female";
        }
        else if(checkedId == R.id.male){
            genderType = "Male";
        }
    }
}
