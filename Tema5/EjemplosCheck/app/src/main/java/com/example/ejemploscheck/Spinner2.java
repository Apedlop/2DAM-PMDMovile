package com.example.ejemploscheck;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner; // Importa correctamente Spinner

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Spinner2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spinner2);

        Spinner spinner = (Spinner) findViewById(R.id.miSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.valores, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Leer elemento seleccionado
        String value = spinner.getSelectedItem().toString();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adaptador, View view, int posicion, long id) {
                adaptador.getItemAtPosition(posicion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}