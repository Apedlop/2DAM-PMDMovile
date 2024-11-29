package com.example.prop_03;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button boton1 = findViewById(R.id.botonToastEditText);
        Button boton2 = findViewById(R.id.botonToastSeekBar);

        boton1.setOnClickListener(view -> {
            Intent intent = new Intent(this, ToastEditText.class);
            startActivity(intent);
        });

        boton2.setOnClickListener(view -> {
            Intent intent= new Intent(this, ToastSeekBar.class);
            startActivity(intent);
        });

    }
}