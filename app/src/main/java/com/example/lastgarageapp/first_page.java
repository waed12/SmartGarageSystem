package com.example.lastgarageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class first_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
       final first_page_loading load = new first_page_loading(first_page.this);
       load.startLoading();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                load.dismissDialoge();
                Intent intent  = new Intent(first_page.this, login.class);
                startActivity(intent);            }
                            },
        5000);
    }
}

   /* int progressInt = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        ProgressBar progressBar = findViewById(R.id.progressBar4);
        TextView textView9 = findViewById(R.id.textView9);


        Activity activity = first_page.this;


        progressBar.setProgress(progressInt);
        progressBar.setMax(10);

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                progressInt = progressInt+5;
                progressBar.setProgress(progressInt);

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView9.setText(String.valueOf(progressInt));


                    }
                });

                if (progressBar.getProgress()>=10){

                    timer.cancel();


                    Intent intent = new Intent(first_page.this,login.class);
                    activity.startActivity(intent);
                    finish();




                }





            }
        },1,5);




    }
}
*/