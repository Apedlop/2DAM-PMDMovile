package com.example.modbotones;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
    }

    public void pantalla1 (View v) {
        Intent pantalla1 = new Intent(this, Pantalla1.class);
        startActivity(pantalla1);
    }

    public void pantalla2 (View v) {
        Intent pantalla2 = new Intent(this, Pantalla2.class);
        startActivity(pantalla2);
    }
}