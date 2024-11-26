package com.example.gestionmascotas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class EditarMascota extends AppCompatActivity {

    private EditText etNombre, etRaza, etEdad, etPeso;
    private RadioGroup rgVacunada, rgDesparacitada, rgEsterilizada;
    private Button btnGuardar, btnCancelar;
    private Mascota mascota;
    private int posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mascota);

        // Obtener datos del Intent
        Intent intent = getIntent();
        mascota = (Mascota) intent.getSerializableExtra("mascota");
        posicion = intent.getIntExtra("position", -1);

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

        // Rellenar los campos con los datos de la mascota
        etNombre.setText(mascota.getNombre());
        etRaza.setText(mascota.getRaza());
        etEdad.setText(String.valueOf(mascota.getEdad()));
        etPeso.setText(String.valueOf(mascota.getPeso()));

        // Asignar los valores de vacunada, desparacitada y esterilizada a los RadioButtons
        rgVacunada.check(mascota.isVacunada() ? R.id.radio_vacunado_si : R.id.radio_vacunado_no);
        rgDesparacitada.check(mascota.isDesparacitada() ? R.id.radio_desparasitado_si : R.id.radio_desparasitado_no);
        rgEsterilizada.check(mascota.isEsterilizada() ? R.id.radio_esterilizado_si : R.id.radio_esterilizado_no);

        // Guardar los cambios cuando se haga clic en el botón
        btnGuardar.setOnClickListener(v -> {
            // Obtener los valores de los campos
            mascota.setNombre(etNombre.getText().toString().trim());
            mascota.setRaza(etRaza.getText().toString().trim());
            mascota.setEdad(Integer.parseInt(etEdad.getText().toString().trim()));
            mascota.setPeso(Float.parseFloat(etPeso.getText().toString().trim()));

            // Obtener los valores de los RadioButtons
            mascota.setVacunada(rgVacunada.getCheckedRadioButtonId() == R.id.radio_vacunado_si);
            mascota.setDesparacitada(rgDesparacitada.getCheckedRadioButtonId() == R.id.radio_desparasitado_si);
            mascota.setEsterilizada(rgEsterilizada.getCheckedRadioButtonId() == R.id.radio_esterilizado_si);

            // Devolver la mascota modificada y la posición a ListadoMascotasAdmin
            Intent resultIntent = new Intent();
            resultIntent.putExtra("mascota", mascota);
            resultIntent.putExtra("position", posicion);

            setResult(RESULT_OK, resultIntent);
            finish();
        });

        btnCancelar.setOnClickListener(v -> {
            setResult(RESULT_CANCELED); // Indicar que el usuario canceló la edición
            finish();
        });

    }
}
