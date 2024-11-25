package com.example.gestionmascotas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditarMascota extends AppCompatActivity {

    private EditText etNombre, etRaza, etEdad, etPeso;
    private Button btnGuardar, btnCancelar;
    private RadioGroup rgVacunada, rgDesparacitada, rgEsterilizada;

    private String nombre, raza;
    private int edad;  // Edad de la mascota
    private float peso;  // Peso de la mascota
    private boolean vacunada, desparacitada, esterilizada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mascota);

        // Obtener los datos pasados desde ListadoMascotasAdmin
        Intent intent = getIntent();
        nombre = intent.getStringExtra("nombre");
        raza = intent.getStringExtra("raza");
        edad = intent.getIntExtra("edad", -1);
        peso = intent.getFloatExtra("peso", -1);
        vacunada = intent.getBooleanExtra("vacunada", false);
        desparacitada = intent.getBooleanExtra("desparacitada", false);
        esterilizada = intent.getBooleanExtra("esterilizada", false);

        // Inicializar los elementos de la UI
        etNombre = findViewById(R.id.etNombre);
        etRaza = findViewById(R.id.etRaza);
        etEdad = findViewById(R.id.etEdad);
        etPeso = findViewById(R.id.etPeso);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Inicializar los RadioGroups (variables de clase)
        rgVacunada = findViewById(R.id.grupo_vacunado);
        rgDesparacitada = findViewById(R.id.grupo_desparasitado);
        rgEsterilizada = findViewById(R.id.grupo_esterilizado);

        // Rellenar los campos con los datos existentes
        etNombre.setText(nombre);
        etRaza.setText(raza);
        etEdad.setText(String.valueOf(edad));
        etPeso.setText(String.valueOf(peso));

        // Seleccionar el RadioButton correcto basado en los datos existentes
        rgVacunada.check(vacunada ? R.id.radio_vacunado_si : R.id.radio_vacunado_no);
        rgDesparacitada.check(desparacitada ? R.id.radio_desparasitado_si : R.id.radio_desparasitado_no);
        rgEsterilizada.check(esterilizada ? R.id.radio_esterilizado_si : R.id.radio_esterilizado_no);

        // Guardar los cambios cuando se presiona el botón Guardar
        btnGuardar.setOnClickListener(v -> {
            // Obtener los datos para editarlos
            String nombreInput = etNombre.getText().toString().trim();
            String razaInput = etRaza.getText().toString().trim();
            String edadText = etEdad.getText().toString().trim();
            String pesoText = etPeso.getText().toString().trim();

            // Verificar si hay campos vacíos
            if (nombreInput.isEmpty() || razaInput.isEmpty() || edadText.isEmpty() || pesoText.isEmpty()) {
                Toast.makeText(EditarMascota.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // Convertir los valores de edad y peso
                edad = Integer.parseInt(edadText);
                peso = Float.parseFloat(pesoText);
            } catch (NumberFormatException e) {
                Toast.makeText(EditarMascota.this, "Edad y peso deben ser números válidos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Actualizar las variables con los nuevos valores
            nombre = nombreInput;
            raza = razaInput;

            // Obtener los valores seleccionados de los RadioButton
            vacunada = rgVacunada.getCheckedRadioButtonId() == R.id.radio_vacunado_si;
            desparacitada = rgDesparacitada.getCheckedRadioButtonId() == R.id.radio_desparasitado_si;
            esterilizada = rgEsterilizada.getCheckedRadioButtonId() == R.id.radio_esterilizado_si;

            // Enviar los datos modificados de vuelta a la actividad principal
            Intent resultIntent = new Intent();
            resultIntent.putExtra("nombre", nombre);
            resultIntent.putExtra("raza", raza);
            Toast.makeText(EditarMascota.this, "Guardando cambios: " + nombre + ", " + raza, Toast.LENGTH_SHORT).show();
            resultIntent.putExtra("edad", edad);
            resultIntent.putExtra("peso", peso);
            resultIntent.putExtra("vacunada", vacunada);
            resultIntent.putExtra("desparacitada", desparacitada);
            resultIntent.putExtra("esterilizada", esterilizada);

            setResult(RESULT_OK, resultIntent);  // Devolver los datos a la actividad anterior
            finish();  // Cerrar la actividad actual
        });

        btnCancelar.setOnClickListener(v -> {
            finish();
        });
    }
}
