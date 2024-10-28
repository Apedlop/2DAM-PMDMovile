package com.example.ejemploscheck;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class ProgressBarActivity extends AppCompatActivity {
    private ProgressBar circularProgressBar;
    private ProgressBar linearProgressBar;
    private Button startCircularProgressButton;
    private Button startLinearProgressButton;
    private Handler handler = new Handler();
    private int circularProgressStatus = 0;
    private int linearProgressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);

        circularProgressBar = findViewById(R.id.circular_progress_bar);
        linearProgressBar = findViewById(R.id.linear_progress_bar);
        startCircularProgressButton = findViewById(R.id.start_circular_progress_button);
        startLinearProgressButton = findViewById(R.id.start_linear_progress_button);

        startCircularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (circularProgressStatus < 100) {
                            circularProgressStatus += 1;

                            // Actualiza el ProgressBar circular en el hilo principal
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    circularProgressBar.setProgress(circularProgressStatus);
                                }
                            });

                            try {
                                // Simula un proceso
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });

        startLinearProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (linearProgressStatus < 100) {
                            linearProgressStatus += 1;

                            // Actualiza el ProgressBar lineal en el hilo principal
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    linearProgressBar.setProgress(linearProgressStatus);
                                }
                            });

                            try {
                                // Simula un proceso
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
    }
}
