package com.example.iat359_petjournal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Settings extends Activity {

//    declare variables

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
//      initialize variables


    }

//    when back button is clicked go to home page
    protected void gotoHomePage(View v){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
    }

//    when logout button is clicked
    protected void gotoLogin(View v){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
