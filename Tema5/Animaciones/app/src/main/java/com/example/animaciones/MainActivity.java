package com.example.animaciones;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.animaciones.alfa.*;
import com.example.animaciones.escala.*;
import com.example.animaciones.rotar.*;
import com.example.animaciones.mueve.*;
import com.example.animaciones.varios.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rotarBoton1 = (Button) findViewById(R.id.Boton1);
        rotarBoton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Rotar1.class);
                startActivity(intent);
            }
        });

        Button rotarBoton2 = (Button) findViewById(R.id.Boton2);
        rotarBoton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Rotar2.class);
                startActivity(intent);
            }
        });

        Button rotarBoton3 = (Button) findViewById(R.id.Boton3);
        rotarBoton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Rotar3.class);
                startActivity(intent);
            }
        });

        Button alfaBoton1 = (Button) findViewById(R.id.Boton4);
        alfaBoton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Alfa2.class);
                startActivity(intent);
            }
        });

        Button alfaBoton2 = (Button) findViewById(R.id.Boton5);
        alfaBoton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Alfa2.class);
                startActivity(intent);
            }
        });

        Button escalaBoton1 = (Button) findViewById(R.id.Boton6);
        escalaBoton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Escala1.class);
                startActivity(intent);
            }
        });

        Button escalaBoton2 = (Button) findViewById(R.id.Boton7);
        escalaBoton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Escala2.class);
                startActivity(intent);
            }
        });

        Button mueveBoton1 = (Button) findViewById(R.id.Boton8);
        mueveBoton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Mueve1.class);
                startActivity(intent);
            }
        });

        Button mueveBoton2 = (Button) findViewById(R.id.Boton9);
        mueveBoton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Mueve2.class);
                startActivity(intent);
            }
        });

        Button variosBoton1 = (Button) findViewById(R.id.Boton10);
        variosBoton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Varios1.class);
                startActivity(intent);
            }
        });

        Button variosBoton2 = (Button) findViewById(R.id.Boton11);
        variosBoton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Varios2.class);
                startActivity(intent);
            }
        });


    }

}