package com.example.gestionmascotas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InfoDetallada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detallada);

        // Cabecera
        LinearLayout contenedor = findViewById(R.id.infoDetallada);
        View cabecera = getLayoutInflater().inflate(R.layout.cabecera, null);
        contenedor.addView(cabecera, 0); // El 0 asegura que la cabecera esté al principio

        // Obtener los datos del Intent
        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String raza = intent.getStringExtra("raza");
        int imagen = intent.getIntExtra("imagen", 0);
        int edad = intent.getIntExtra("edad", 0);
        float peso = intent.getFloatExtra("peso", 0.0f);
        boolean vacunada = intent.getBooleanExtra("vacunada", false);
        boolean desparacitada = intent.getBooleanExtra("desparacitada", false);
        boolean esterilizada = intent.getBooleanExtra("esterilizada", false);

        // Nombre de usuario
        String usuario = intent.getStringExtra("usuario");
        TextView nomUsuario = cabecera.findViewById(R.id.nomUsuario);
        nomUsuario.setText(usuario);

        // Asignar los datos a los elementos de la vista
        TextView nombreTextView = findViewById(R.id.texto_nombre);
        TextView razaTextView = findViewById(R.id.raza_valor);
        ImageView imagenImageView = findViewById(R.id.imagen_mascota);
        TextView edadTextView = findViewById(R.id.edad_valor);
        TextView pesoTextView = findViewById(R.id.peso_valor);

        RadioButton vacunadaSi = findViewById(R.id.radio_vacunado_si);
        RadioButton vacunadaNo = findViewById(R.id.radio_vacunado_no);
        RadioButton desparacitadaSi = findViewById(R.id.radio_desparasitado_si);
        RadioButton desparacitadaNo = findViewById(R.id.radio_desparasitado_no);
        RadioButton esterilizadaSi = findViewById(R.id.radio_esterilizado_si);
        RadioButton esterilizadaNo = findViewById(R.id.radio_esterilizado_no);

        // Establecer los valores en los TextViews e ImageView
        nombreTextView.setText(nombre);
        razaTextView.setText(raza);
        imagenImageView.setImageResource(imagen);
        edadTextView.setText(edad + " años");
        pesoTextView.setText(peso + " kg");

        // Asignar estado a los RadioButton
        vacunadaSi.setChecked(vacunada);
        vacunadaNo.setChecked(!vacunada);
        desparacitadaSi.setChecked(desparacitada);
        desparacitadaNo.setChecked(!desparacitada);
        esterilizadaSi.setChecked(esterilizada);
        esterilizadaNo.setChecked(!esterilizada);
    }

}