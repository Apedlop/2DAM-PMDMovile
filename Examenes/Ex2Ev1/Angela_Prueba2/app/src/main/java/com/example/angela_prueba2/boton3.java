package com.example.angela_prueba2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class boton3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton3);

        // Crear una lista de versiones de Android
        String[] versionesAndroid = {
                "APPLE PIE", "BANANA BREAD", "CUPCAKE", "DONUT", "ECLAIR",
                "FROYO", "GINGERBREAD", "HONEYCOMB", "ICE CREAM SANDWICH",
                "JELLY BEAN", "KITKAT", "LOLLIPOP", "MARSHMALLOW", "NOUGAT",
                "OREO", "PIE", "ANDROID 10"
        };

        // Obtener la referencia del AutoCompleteTextView
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        // Crear un ArrayAdapter usando la lista de versiones de Android
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, versionesAndroid);

        // Configurar el adaptador en el AutoCompleteTextView
        autoCompleteTextView.setAdapter(adaptador);
    }
}