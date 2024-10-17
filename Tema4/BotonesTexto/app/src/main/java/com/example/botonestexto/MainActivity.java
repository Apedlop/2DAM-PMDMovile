package com.example.botonestexto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        // Botón 1
        Button boton1 = (Button) findViewById(R.id.Boton1);
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bt1 = new Intent(MainActivity.this, Boton1.class);
                startActivity(bt1);
            }
        });

        // Botón 2
        Button boton2 = (Button) findViewById(R.id.Boton2);
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bt2 = new Intent(MainActivity.this, Boton2.class);
                startActivity(bt2);
            }
        });

        // Botón 3
        Button boton3 = (Button) findViewById(R.id.Boton3);
        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bt3 = new Intent(MainActivity.this, Boton3.class);
                startActivity(bt3);
            }
        });

        // Botón 4
        Button boton4 = (Button) findViewById(R.id.Boton4);
        boton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bt4 = new Intent(MainActivity.this, Boton4.class);
                startActivity(bt4);
            }
        });

        // Botón 5
        Button boton5 = (Button) findViewById(R.id.Boton5);
        boton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bt5 = new Intent(MainActivity.this, Boton5.class);
                startActivity(bt5);
            }
        });
    }

}