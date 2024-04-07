package com.example.iat359_petjournal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TipsAdvice extends Activity {
    //declaring variables
    private Button button1, button2, button3, button4, button5, button6, button7;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tips_advice);

        //intializing variables
        button1 = findViewById(R.id.tableBut1);
        button2 = findViewById(R.id.tableBut2);
        button3 = findViewById(R.id.tableBut3);
        button4 = findViewById(R.id.tableBut4);
        button5 = findViewById(R.id.tableBut5);
        button6 = findViewById(R.id.tableBut6);
        button7 = findViewById(R.id.tableBut7);

        returnButton = findViewById(R.id.returnButton);

        //set onClick listeners for all buttons
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating an implicit intent for guiding users to an external resource
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.preventivevet.com/resources/new-dog-puppy"));
                startActivity(i);
            }
        });
        button2 = findViewById(R.id.tableBut2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.preventivevet.com/resources/new-dog-puppy"));
                startActivity(i);
            }
        });
        button3 = findViewById(R.id.tableBut3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.preventivevet.com/resources/new-dog-puppy"));
                startActivity(i);
            }
        });
        button4 = findViewById(R.id.tableBut4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.preventivevet.com/resources/new-dog-puppy"));
                startActivity(i);
            }
        });
        button5 = findViewById(R.id.tableBut5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.preventivevet.com/resources/new-dog-puppy"));
                startActivity(i);
            }
        });
        button6 = findViewById(R.id.tableBut6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.preventivevet.com/resources/new-dog-puppy"));
                startActivity(i);
            }
        });
        button7 = findViewById(R.id.tableBut7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.preventivevet.com/resources/new-dog-puppy"));
                startActivity(i);
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
