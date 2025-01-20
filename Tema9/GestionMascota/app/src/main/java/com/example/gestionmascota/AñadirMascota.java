package com.example.gestionmascota;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AñadirMascota extends AppCompatActivity {

    private EditText etNombre, etRaza, etEdad, etPeso;
    private RadioGroup rgVacunada, rgDesparacitada, rgEsterilizada;
    private Button btnGuardar, btnCancelar;
    private Spinner spinnerTipoMascota;
    private ImageView imgMascota;

    // Lista de mascotas usando Contenido.Lista_entrada
    private static ArrayList<Contenido.Lista_entrada> listaMascotas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mascota);

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre);
        etRaza = findViewById(R.id.etRaza);
        etEdad = findViewById(R.id.etEdad);
        etPeso = findViewById(R.id.etPeso);
        rgVacunada = findViewById(R.id.grupo_vacunado);
        rgDesparacitada = findViewById(R.id.grupo_desparasitado);
        rgEsterilizada = findViewById(R.id.grupo_esterilizado);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);
        spinnerTipoMascota = findViewById(R.id.spinnerTipoMascota);
        imgMascota = findViewById(R.id.imgMascota);

        // Configurar el Spinner con tipos de mascotas
        String[] tiposMascota = {"Perro", "Gato", "Conejo", "Hamster"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tiposMascota);
        spinnerTipoMascota.setAdapter(adapter);

        // Cambiar la imagen según el tipo de mascota seleccionado
        spinnerTipoMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                imgMascota.setImageResource(obtenerImagenSeleccionada());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                imgMascota.setImageResource(R.drawable.imagen_perro);
            }
        });

        btnGuardar.setOnClickListener(view -> {
            // Obtener datos del formulario
            String nombre = etNombre.getText().toString().trim();
            String raza = etRaza.getText().toString().trim();
            String edadStr = etEdad.getText().toString().trim();
            String pesoStr = etPeso.getText().toString().trim();

            // Validar campos
            if (nombre.isEmpty() || raza.isEmpty() || edadStr.isEmpty() || pesoStr.isEmpty()) {
                mostrarToast("Todos los campos son obligatorios");
                return;
            }

            int edad;
            float peso;
            try {
                edad = Integer.parseInt(edadStr);
                peso = Float.parseFloat(pesoStr);
            } catch (NumberFormatException e) {
                mostrarToast("Edad y peso deben ser números válidos");
                return;
            }

            boolean vacunada = rgVacunada.getCheckedRadioButtonId() == R.id.radio_vacunado_si;
            boolean desparacitada = rgDesparacitada.getCheckedRadioButtonId() == R.id.radio_desparasitado_si;
            boolean esterilizada = rgEsterilizada.getCheckedRadioButtonId() == R.id.radio_esterilizado_si;

            int imagen = obtenerImagenSeleccionada();

            // Crear nueva entrada en Contenido
            Contenido.Lista_entrada nuevaMascota = new Contenido.Lista_entrada(
                    String.valueOf(listaMascotas.size()), imagen, nombre, raza, edad, peso, vacunada, desparacitada, esterilizada
            );

            // Agregar la mascota a la lista
            listaMascotas.add(nuevaMascota);

            // Volver a la actividad anterior con el resultado
            Intent intent = new Intent(AñadirMascota.this, Actividad.class);
            setResult(RESULT_OK, intent);
            finish();
        });

        btnCancelar.setOnClickListener(v -> finish());
    }

    private int obtenerImagenSeleccionada() {
        switch (spinnerTipoMascota.getSelectedItem().toString()) {
            case "Perro":
                return R.drawable.imagen_perro;
            case "Gato":
                return R.drawable.imagen_gato;
            case "Conejo":
                return R.drawable.imagen_conejo;
            case "Hamster":
                return R.drawable.imagen_hamster;
        }
        return 0;
    }

    @SuppressLint("MissingInflatedId")
    private void mostrarToast(String mensaje) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.layout_toast));
        TextView textoToast = layout.findViewById(R.id.textoToast);
        textoToast.setText(mensaje);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
