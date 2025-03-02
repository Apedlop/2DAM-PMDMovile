package com.example.dinosaurio;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView dino;
    private Button btnJump, btnDuck;
    private boolean isJumping = false;
    private boolean isDucking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dino = findViewById(R.id.dino);
        btnJump = findViewById(R.id.btnJump);
        btnDuck = findViewById(R.id.btnDuck);

        btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isJumping) {
                    jump();
                }
            }
        });

        btnDuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDucking) {
                    duck();
                }
            }
        });
    }

    private void jump() {
        isJumping = true;
        dino.animate().translationYBy(-200).setDuration(500).withEndAction(new Runnable() {
            @Override
            public void run() {
                dino.animate().translationYBy(200).setDuration(500).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        isJumping = false;
                    }
                }).start();
            }
        }).start();
    }

    private void duck() {
        isDucking = true;
        dino.animate().scaleY(0.5f).setDuration(300).withEndAction(new Runnable() {
            @Override
            public void run() {
                dino.animate().scaleY(1f).setDuration(300).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        isDucking = false;
                    }
                }).start();
            }
        }).start();
    }
}