package com.example.ejemploscheck;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class Spinner extends AppCompatActivity { // Cambiamos el nombre de la clase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        android.widget.Spinner spinner = findViewById(R.id.miSpinner); // Esto ahora se refiere al widget Spinner
        String[] valores = {"Valor1", "Valor2", "Valor3", "Valor4", "Valor5"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valores));
    }
}
