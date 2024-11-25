package com.example.gestionmascotas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InfoDetallada extends AppCompatActivity {

    // Declaración de variables de clase para evitar múltiples llamadas a findViewById
    private TextView nombreTextView, razaTextView, edadTextView, pesoTextView;
    private ImageView imagenImageView;
    private RadioButton vacunadaSi, vacunadaNo, desparacitadaSi, desparacitadaNo, esterilizadaSi, esterilizadaNo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detallada);

        // Obtener el objeto Mascota del Intent
        Mascota mascota = (Mascota) getIntent().getSerializableExtra("mascota");
        String usuario = getIntent().getStringExtra("usuario");

        // Inflar y agregar la cabecera
        LinearLayout contenedor = findViewById(R.id.infoDetallada);
        LayoutInflater inflater = getLayoutInflater();
        View cabeceraView = inflater.inflate(R.layout.cabecera, contenedor, false);
        contenedor.addView(cabeceraView, 0);  // Agregar la cabecera al inicio

        // Configurar el TextView dentro de la cabecera
        TextView nomUsuario = cabeceraView.findViewById(R.id.nomUsuario);
        nomUsuario.setText(usuario != null ? usuario : "Usuario desconocido");

        // Inicializar las vistas
        inicializarVistas();

        // Asignar los valores a la interfaz desde el objeto Mascota
        if (mascota != null) {
            actualizarVistaConDatosMascota(mascota);
        }
    }

    // Método para inicializar las vistas
    private void inicializarVistas() {
        nombreTextView = findViewById(R.id.texto_nombre);
        razaTextView = findViewById(R.id.raza_valor);
        imagenImageView = findViewById(R.id.imagen_mascota);
        edadTextView = findViewById(R.id.edad_valor);
        pesoTextView = findViewById(R.id.peso_valor);

        vacunadaSi = findViewById(R.id.radio_vacunado_si);
        vacunadaNo = findViewById(R.id.radio_vacunado_no);
        desparacitadaSi = findViewById(R.id.radio_desparasitado_si);
        desparacitadaNo = findViewById(R.id.radio_desparasitado_no);
        esterilizadaSi = findViewById(R.id.radio_esterilizado_si);
        esterilizadaNo = findViewById(R.id.radio_esterilizado_no);
    }

    // Método para actualizar las vistas con los datos de la mascota
    private void actualizarVistaConDatosMascota(Mascota mascota) {
        nombreTextView.setText(mascota.getNombre());
        razaTextView.setText(mascota.getRaza());
        imagenImageView.setImageResource(mascota.getImagen());
        edadTextView.setText(mascota.getEdad() + " años");
        pesoTextView.setText(mascota.getPeso() + " kg");

        vacunadaSi.setChecked(mascota.isVacunada());
        vacunadaNo.setChecked(!mascota.isVacunada());
        desparacitadaSi.setChecked(mascota.isDesparacitada());
        desparacitadaNo.setChecked(!mascota.isDesparacitada());
        esterilizadaSi.setChecked(mascota.isEsterilizada());
        esterilizadaNo.setChecked(!mascota.isEsterilizada());

        // Deshabilitar los RadioButton para evitar cambios
        deshabilitarRadioButtons();
    }

    // Método para deshabilitar los RadioButtons
    private void deshabilitarRadioButtons() {
        vacunadaSi.setEnabled(false);
        vacunadaNo.setEnabled(false);
        desparacitadaSi.setEnabled(false);
        desparacitadaNo.setEnabled(false);
        esterilizadaSi.setEnabled(false);
        esterilizadaNo.setEnabled(false);
    }

    // Manejo del resultado cuando la actividad regresa datos
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Mascota mascotaModificada = (Mascota) data.getSerializableExtra("mascota");
            if (mascotaModificada != null) {
                actualizarVistaConDatosMascota(mascotaModificada);
            }
        }
    }
}
