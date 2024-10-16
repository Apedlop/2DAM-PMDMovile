package com.example.botonestexto;

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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void boton1(View v) {
        Intent bt1 = new Intent(this, Boton1.class);
        startActivity(bt1);
    }

    public void boton2(View v) {
        Intent bt2 = new Intent(this, Boton2.class);
        startActivity(bt2);
    }

    public void boton3(View v) {
        Intent bt3 = new Intent(this, Boton3.class);
        startActivity(bt3);
    }

    public void boton4(View v) {
        Intent bt4 = new Intent(this, Boton4.class);
        startActivity(bt4);
    }

    public void boton5(View v) {
        Intent bt5 = new Intent(this, Boton5.class);
        startActivity(bt5);
    }
}