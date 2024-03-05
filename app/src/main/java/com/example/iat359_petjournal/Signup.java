package com.example.iat359_petjournal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {
// declare variables
    EditText usernameEditText, passwordEditText;
    public static final String DEFAULT = "not available";

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.signup);
            usernameEditText = (EditText)findViewById(R.id.editTextUsername);
            passwordEditText = (EditText)findViewById(R.id.editTextPassword);
            retrieveData();
        }

//        retrieving data from sharedPref
        protected void retrieveData(){
            SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
            String username = sharedPrefs.getString("username", DEFAULT);
            String password = sharedPrefs.getString("password", DEFAULT);

//    if the username or password isn't equal to the default string then login info exists. Go to login page
            if (!username.equals(DEFAULT)||!password.equals(DEFAULT))
            {
                Toast.makeText(this, "Login information already exist", Toast.LENGTH_LONG).show();
                gotoLoginPage();
            }
        }

//        onclick submit button, get the sharedPrefs and place data inside SharedPrefs. Head to Add Pet page
        public void submit (View view){

            SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("username", usernameEditText.getText().toString());
            editor.putString("password", passwordEditText.getText().toString());
            Toast.makeText(this, "Username and password saved to Preferences", Toast.LENGTH_LONG).show();
            editor.commit();
            gotoAddPetPage();
        }
//intent to add pet class
        protected void gotoAddPetPage(){
            Intent intent= new Intent(this, AddPet.class);
            startActivity(intent);
        }
//intent to login class
        protected void gotoLoginPage(){
            Intent intent= new Intent(this, Login.class);
            startActivity(intent);
        }


}
