package com.example.proyecto5;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enviarDatos(View v) {
        String myString = "Hola, mundo!";
        double myDouble = 123.45;

        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        intent.putExtra("myStringKey", myString);
        intent.putExtra("myDoubleKey", myDouble);
        startActivity(intent);
    }

}