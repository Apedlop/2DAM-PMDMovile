package com.example.maps;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.maps.MapaVeterianriosActivity;
import com.example.maps.R;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_MAPA_VETERINARIOS = 1;
    private TextView textViewDireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnBuscarVeterinarios = findViewById(R.id.btnBuscarVeterinarios);
        textViewDireccion = findViewById(R.id.textViewDireccion);

        btnBuscarVeterinarios.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapaVeterianriosActivity.class);
            startActivityForResult(intent, REQUEST_MAPA_VETERINARIOS);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MAPA_VETERINARIOS && resultCode == RESULT_OK && data != null) {
            String direccionVeterinario = data.getStringExtra("direccion");
            textViewDireccion.setText(direccionVeterinario);
        }
    }
}
