package com.example.sqlite;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private TextView welcomeText;
    private Button infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcomeText = findViewById(R.id.welcomeText);
        infoButton = findViewById(R.id.infoButton);

        // Get the username from the intent
        String username = getIntent().getStringExtra("username");
        welcomeText.setText("Bienvenido, " + username + "!");

        infoButton.setOnClickListener(v -> {
            // Navigate to InfoActivity
            Intent intent = new Intent(HomeActivity.this, InfoActivity.class);
            startActivity(intent);
        });
    }
}