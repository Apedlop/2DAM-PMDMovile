package com.example.sqlite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText usernameField;
    private Button loginButton;
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameField = findViewById(R.id.usernameField);
        loginButton = findViewById(R.id.loginButton);

        dbHelper = new SQLiteHelper(this);

        // Load the last logged-in username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String lastUsername = sharedPreferences.getString("last_username", "");
        usernameField.setText(lastUsername);

        loginButton.setOnClickListener(v -> {
            String username = usernameField.getText().toString().trim();

            if (username.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa un nombre de usuario", Toast.LENGTH_SHORT).show();
            } else {
                // Save the username to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("last_username", username);
                editor.apply();

                // Check if the user exists or create a new user
                if (!dbHelper.userExists(username)) {
                    dbHelper.insertUser(username);
                }

                // Navigate to HomeActivity
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}
