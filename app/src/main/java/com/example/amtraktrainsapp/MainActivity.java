package com.example.amtraktrainsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get objects
        TextView txtHours = findViewById(R.id.txtHours);
        TextView txtMinutes = findViewById(R.id.txtMinutes);
        TextView txtLengthOfTrip = findViewById(R.id.txtLengthOfTrip);
        Button btnCalculate = findViewById(R.id.btnCalculate);

        final int MAX_HOURS = 23;
        final int MAX_MINUTES = 59;
        final int MAX_LENGTH_TRAVEL = 1500;

        HideActionBar();

        //Calculate Arrive Time Click Event
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //Check all required inputs
                    String hours = txtHours.getText().toString();
                    String minutes = txtMinutes.getText().toString();
                    String lengthOfTrip = txtLengthOfTrip.getText().toString();

                    if(CheckRequiredInput(hours) && CheckRequiredInput(minutes) && CheckRequiredInput(lengthOfTrip)){
                        if(CheckMaxConstraint(Integer.parseInt(hours), MAX_HOURS) && CheckMaxConstraint(Integer.parseInt(minutes), MAX_MINUTES) && CheckMaxConstraint(Integer.parseInt(lengthOfTrip), MAX_LENGTH_TRAVEL)){
                            // Open File and Stream to write data into file
                            FileOutputStream fi = openFileOutput("myData.txt", Context.MODE_PRIVATE);
                            OutputStreamWriter ow = new OutputStreamWriter(fi);
                            ow.write(hours + "\n" + minutes + "\n" + lengthOfTrip);
                            ow.close();
                            //Toast.makeText(MainActivity.this,"Writing data", Toast.LENGTH_SHORT).show();

                            //Open second screen to show the arrival time
                            startActivity(new Intent(MainActivity.this, ShowArrivalTimeActivity.class));

                        }else{
                            Toast.makeText(MainActivity.this, "You've entered an unrelevant number. Hours: 0 - 23, Minutes: 0 - 59!, Length Of Trip: 0 - 1500", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(MainActivity.this, "You've not entered a required input! Please enter all of required inputs!", Toast.LENGTH_LONG).show();
                    }



                }catch(Exception e){
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    //Hide Action bar for this particular activity
    private void HideActionBar(){
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    //Check to see if a required input has been entered
    private boolean CheckRequiredInput(String str){
        if(str.length() > 0)
            return true;
        else
            return false;
    }

    //Check constraint for required inputs
    private boolean CheckMaxConstraint(int i, int upperLimit){
        if(i > upperLimit)
            return false;
        else
            return true;
    }
}

