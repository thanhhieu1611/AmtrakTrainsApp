package com.example.amtraktrainsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ShowArrivalTimeActivity extends AppCompatActivity {
    //Declare global variables
    EditText txtArrivalHours;
    EditText txtArrivalMinutes;
    LinearLayout ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_arrival_time);

        //Get objects
        txtArrivalHours = findViewById(R.id.txtArrivalHours);
        txtArrivalMinutes = findViewById(R.id.txtArrivalMinutes);
        ll = (LinearLayout) findViewById(R.id.ll2);

        try{
            //Open file to read data
            FileInputStream fi = openFileInput("myData.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fi));

            String buffer = "";
            ArrayList<String> items = new ArrayList<>();

            while((buffer = br.readLine()) != null){
                items.add(buffer);
            }

            CalculateArrivalHours(items);

        }catch(Exception e){
            Toast.makeText(ShowArrivalTimeActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
        }

        HideActionBar();


    }

    //Hide Action bar for this particular activity
    private void HideActionBar(){
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }//androidx.constraintlayout.widget.ConstraintLayout

    //Calculate arrival time
    private void CalculateArrivalHours(ArrayList<String> items){
        int tmpHrs = Integer.parseInt(items.get(0));
        int tmpMins = Integer.parseInt(items.get(1));
        int tmpLens = Integer.parseInt(items.get(2));

        int tmpArrivalHrs = ((tmpMins + tmpLens) /60 + tmpHrs);
        if(tmpArrivalHrs > 23){
            tmpArrivalHrs -= 24;
            //Toast.makeText(ShowArrivalTimeActivity.this, "Red-Eye Arrival", Toast.LENGTH_SHORT).show();

            AddNewText(ll);
            AddNewImage(ll);


        }
        txtArrivalHours.setText(String.valueOf(tmpArrivalHrs));

        int tmpArrivalMins = (tmpMins + tmpLens) % 60;
        txtArrivalMinutes.setText(String.valueOf(tmpArrivalMins));
    }

    //Dynamically add new image in Red-Eye Arrival
    private void AddNewImage(LinearLayout ll){
        //Toast.makeText(ShowArrivalTimeActivity.this, "In Add Image", Toast.LENGTH_SHORT).show();
        //LinearLayout ll = (LinearLayout) findViewById(R.id.ll2);

        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.red_eye_arrival);
        iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 800, 1.0f));

        ll.addView(iv);

    }
    @SuppressLint("ResourceAsColor")
    private void AddNewText(LinearLayout ll){
        //Toast.makeText(ShowArrivalTimeActivity.this, "In Add Text", Toast.LENGTH_SHORT).show();
        //LinearLayout ll = (LinearLayout) findViewById(R.id.ll2);

        TextView et = new TextView(this);
        et.setText("Red Eye Arrival");
        et.setTextColor(R.color.teal_700);
        et.setGravity(17);
        et.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        et.setTextSize(20);
        ll.addView(et);

    }



}