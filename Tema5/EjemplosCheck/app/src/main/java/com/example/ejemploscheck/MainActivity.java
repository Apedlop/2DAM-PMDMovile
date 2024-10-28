package com.example.ejemploscheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button spinner = findViewById(R.id.boton1);
        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Spinner.class);
                startActivity(intent);
            }
        });

        Button spinner2 = findViewById(R.id.boton2);
        spinner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Spinner2.class);
                startActivity(intent);
            }
        });

        Button checkBox = findViewById(R.id.boton3);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheckBoxActivity.class);
                startActivity(intent);
            }
        });

        Button radioButton = findViewById(R.id.boton4);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RadioButtonActivity.class); // Cambiar aquí
                startActivity(intent);
            }
        });

        Button seleccionarDia = findViewById(R.id.boton5);
        seleccionarDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SeleccionarDia.class); // Cambiar aquí
                startActivity(intent);
            }
        });

        Button switchA = findViewById(R.id.boton6);
        switchA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SwitchActivity.class); // Cambiar aquí
                startActivity(intent);
            }
        });

        Button seekBar = findViewById(R.id.boton7);
        seekBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SeekBarActivity.class); // Cambiar aquí
                startActivity(intent);
            }
        });

        Button seekBarFraccionado = findViewById(R.id.boton8);
        seekBarFraccionado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SeekBarFraccionado.class); // Cambiar aquí
                startActivity(intent);
            }
        });

        Button ratingBar = findViewById(R.id.boton9);
        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RatingBarActivity.class); // Cambiar aquí
                startActivity(intent);
            }
        });

        Button progressBar = findViewById(R.id.boton10);
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProgressBarActivity.class); // Cambiar aquí
                startActivity(intent);
            }
        });
    }
}
