package com.example.gestionmascotas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AñadirMascota extends AppCompatActivity {

    private EditText etNombre, etRaza, etEdad, etPeso;
    private RadioGroup rgVacunada, rgDesparacitada, rgEsterilizada;
    private Button btnGuardar, btnCancelar;
    private Mascota mascota;
    private int posicion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mascota);

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre);
        etRaza = findViewById(R.id.etRaza);
        etEdad = findViewById(R.id.etEdad);
        etPeso = findViewById(R.id.etPeso);
        rgVacunada = findViewById(R.id.grupo_vacunado); // Aquí corriges la referencia
        rgDesparacitada = findViewById(R.id.grupo_desparasitado);
        rgEsterilizada = findViewById(R.id.grupo_esterilizado);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);


    }
}
