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

public class Login extends AppCompatActivity {
    public static final String DEFAULT = "not available";
    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        user = (EditText) findViewById(R.id.editTextUsername);
        pass = (EditText) findViewById(R.id.editTextPassword);
    }


    public void login(View view) {

        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String username = sharedPrefs.getString("username", DEFAULT);
        String password = sharedPrefs.getString("password", DEFAULT);

        if (username.equals(user.getText().toString()) && password.equals(pass.getText().toString())) {
            Toast.makeText(this, "Data retrieve success", Toast.LENGTH_LONG).show();
            gotoMainActivity();
        } else {
            Toast.makeText(this, "No data found", Toast.LENGTH_LONG).show();
            gotoSignup();
        }
    }

    protected void gotoMainActivity () {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void gotoSignup () {
        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("username", DEFAULT);
        editor.putString("password", DEFAULT);
        editor.apply();

        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }


}
