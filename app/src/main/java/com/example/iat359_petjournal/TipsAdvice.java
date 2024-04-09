package com.example.iat359_petjournal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TipsAdvice extends Activity {
    //declaring variables
    private static Button button1;
    private static Button button2;
    private static Button button3;
    private static Button button4;
    private static Button button5;
    private static Button button6;
    private static Button button7;
    private static ImageButton returnButton;
    static LinearLayout bg, header;
    static TextView title;
    static SharedPreferences sharedPrefs;
    static ArrayList<Button> buttons;

    static ArrayList<TextView> headingText;

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

        buttons = new ArrayList<>();
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
        buttons.add(button7);

        headingText = new ArrayList<>();
        headingText.add(findViewById(R.id.tableText1));
        headingText.add(findViewById(R.id.tableText2));
        headingText.add(findViewById(R.id.tableText3));
        headingText.add(findViewById(R.id.tableText4));
        headingText.add(findViewById(R.id.tableText5));
        headingText.add(findViewById(R.id.tableText6));
        headingText.add(findViewById(R.id.tableText7));

        bg = findViewById(R.id.bg);
        title = findViewById(R.id.titleText);
        header = findViewById(R.id.header);

        returnButton = findViewById(R.id.returnButton);
        //        get shared prefs
        sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        setTipsAdviceMode();

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

    public static void setTipsAdviceMode(){
        if(sharedPrefs!=null) {
            float light_val = sharedPrefs.getFloat("lightSensor", 0);
            float threshold = LightDarkMode.getThreshold();

            if (light_val < threshold) {
                bg.setBackgroundColor(Color.parseColor("#4C4C4C"));
                title.setTextColor(Color.parseColor("#695C54"));
                header.setBackgroundColor(Color.parseColor("#BCBCBC"));
                returnButton.setImageResource(R.drawable.backbuttonnight);
                    for(int i = 0;i<buttons.size();i++) {
                        buttons.get(i).setBackgroundResource(R.drawable.primary_buttonnight);
                    }
                for(int i = 0;i<headingText.size();i++) {
                    headingText.get(i).setTextColor(Color.WHITE);
                }


            } else {
                bg.setBackgroundColor(Color.WHITE);
                title.setTextColor(Color.parseColor("#BC7245"));
                header.setBackgroundColor(Color.parseColor("#F7F2EE"));
                returnButton.setImageResource(R.drawable.backbutton);

                for(int i = 0;i<buttons.size();i++) {
                    buttons.get(i).setBackgroundResource(R.drawable.primary_button);
                }
                for(int i = 0;i<headingText.size();i++) {
                    headingText.get(i).setTextColor(Color.BLACK);
                }

            }
        }
    }
}
