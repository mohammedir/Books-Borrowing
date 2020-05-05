package com.example.booksapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class SplachScreen extends AppCompatActivity {


    int progressbarstatus=0;
    private Handler progressBarbHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        final ProgressBar pb =  findViewById(R.id.progressBar);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressbarstatus < 100) {
                    progressbarstatus = progressbarstatus + 50;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBarbHandler.post(new Runnable() {

                        public void run() {
                            pb.setProgress(progressbarstatus);
                        }

                    });
                }
                if (progressbarstatus >= 100) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent i = new Intent(SplachScreen.this, MainActivity.class);
                    startActivity(i);
                    SplachScreen.this.finish();

                }
            }
        }).start();
    }
    }

