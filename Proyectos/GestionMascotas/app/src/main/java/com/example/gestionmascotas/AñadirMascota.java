package com.example.gestionmascotas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AñadirMascota extends AppCompatActivity {

    private EditText etNombre, etRaza, etEdad, etPeso;
    private RadioGroup rgVacunada, rgDesparacitada, rgEsterilizada;
    private Button btnGuardar, btnCancelar;
    private Spinner spinnerTipoMascota;
    private ImageView imgMascota;
    private MascotaDBHelper dbMascota;

    // Lista de mascotas
    private static ArrayList<Mascota> listaMascotas = new ArrayList<>();

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

        // Configurar el evento para el Spinner
        spinnerTipoMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, android.view.View selectedItemView, int position, long id) {
                String tipoSeleccionado = parentView.getItemAtPosition(position).toString();
                // Cambiar la imagen según el tipo de mascota seleccionado
                switch (tipoSeleccionado) {
                    case "Perro":
                        imgMascota.setImageResource(R.drawable.imagen_perro);
                        break;
                    case "Gato":
                        imgMascota.setImageResource(R.drawable.imagen_gato);
                        break;
                    case "Conejo":
                        imgMascota.setImageResource(R.drawable.imagen_conejo);
                        break;
                    case "Hamster":
                        imgMascota.setImageResource(R.drawable.imagen_hamster);
                        break;
                    default:
                        imgMascota.setImageResource(R.drawable.imegen_defecto);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                imgMascota.setImageResource(R.drawable.imegen_defecto);
            }
        });

        btnGuardar.setOnClickListener(view -> {
            // Obtener datos del formulario
            String nombre = etNombre.getText().toString().trim();
            String raza = etRaza.getText().toString().trim();
            String edadStr = etEdad.getText().toString().trim();
            String pesoStr = etPeso.getText().toString().trim();

            // Verificar si los campos edad y peso están vacíos
            if (nombre.isEmpty() || raza.isEmpty() || edadStr.isEmpty() || pesoStr.isEmpty()) {
                mostrarToast("Todos los campos son obligatorios");
                return; // Salir si hay campos vacíos
            }

            int edad = 0;
            float peso = 0.0f;
            try {
                edad = Integer.parseInt(edadStr); // Intentar convertir a entero
                peso = Float.parseFloat(pesoStr); // Intentar convertir a float
            } catch (NumberFormatException e) {
                mostrarToast("Edad y peso deben ser números válidos");
                return; // Salir si la conversión falla
            }

            boolean vacunada = rgVacunada.getCheckedRadioButtonId() == R.id.radio_vacunado_si;
            boolean desparacitada = rgDesparacitada.getCheckedRadioButtonId() == R.id.radio_desparasitado_si;
            boolean esterilizada = rgEsterilizada.getCheckedRadioButtonId() == R.id.radio_esterilizado_si;

            // Obtener la imagen correspondiente según la selección
            int imagen = obtenerImagenSeleccionada();

//            long id = dbMascota.obtenerProximoIdLibre();

            // Crear objeto Mascota
            Mascota nuevaMascota = new Mascota(nombre, raza, imagen, edad, peso, vacunada, desparacitada, esterilizada);

            // Agregar la mascota a la lista
            listaMascotas.add(nuevaMascota);

            // Crear un Intent para regresar a ListadoMascotasAdmin con la lista actualizada
            Intent intent = new Intent(AñadirMascota.this, ListadoMascotasAdmin.class);
            intent.putExtra("mascotas", listaMascotas); // Aquí pasas la lista de mascotas
            setResult(RESULT_OK, intent);
            finish();
        });

        // Configurar el botón Cancelar
        btnCancelar.setOnClickListener(v -> {
            finish();
        });
    }

    // Método para obtener el recurso de la imagen según el tipo seleccionado
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
            default:
                return R.drawable.imegen_defecto;
        }
    }

    // Método para mostrar toast
    @SuppressLint("MissingInflatedId")
    public void mostrarToast(String mensaje) {
        // Toast personalizado
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.layotu_toast));
        TextView textoToast = layout.findViewById(R.id.textoToast);
        textoToast.setText(mensaje);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}
