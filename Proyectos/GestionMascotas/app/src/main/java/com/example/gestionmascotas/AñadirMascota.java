package com.example.gestionmascotas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AñadirMascota extends AppCompatActivity {

    private EditText etNombre, etRaza, etEdad, etPeso;
    private RadioGroup rgVacunada, rgDesparacitada, rgEsterilizada;
    private Button btnGuardar, btnCancelar;
    private ImageButton tomarFoto;
    private Spinner spinnerTipoMascota;
    private ImageView imgMascota;
    private int imagen;
    byte[] imagenByteArray;
    private  Mascota nuevaMascota;

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
        tomarFoto = findViewById(R.id.tomarFoto);
//        spinnerTipoMascota = findViewById(R.id.spinnerTipoMascota);
        imgMascota = findViewById(R.id.imgMascota);

        tomarFoto.setOnClickListener(v -> {
            abrirCamara();
        });

        // Configurar el Spinner con tipos de mascotas
//        String[] tiposMascota = {"Perro", "Gato", "Conejo", "Hamster", "Tomar foto"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tiposMascota);
//        spinnerTipoMascota.setAdapter(adapter);
//
//        // Configurar el evento para el Spinner
//        spinnerTipoMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, android.view.View selectedItemView, int position, long id) {
//                Bitmap bitmapMascota;
//                String tipoSeleccionado = parentView.getItemAtPosition(position).toString();
//                // Cambiar la imagen según el tipo de mascota seleccionado
//                switch (tipoSeleccionado) {
//                    case "Perro":
//                        imgMascota.setImageResource(R.drawable.imagen_perro);
//                        imagen = R.drawable.imagen_perro;
//                        break;
//                    case "Gato":
//                        imgMascota.setImageResource(R.drawable.imagen_gato);
//                        imagen = R.drawable.imagen_gato;
//                        break;
//                    case "Conejo":
//                        imgMascota.setImageResource(R.drawable.imagen_conejo);
//                        imagen = R.drawable.imagen_conejo;
//                        break;
//                    case "Hamster":
//                        imgMascota.setImageResource(R.drawable.imagen_hamster);
//                        imagen = R.drawable.imagen_hamster;
//                        break;
//                    case "Tomar foto":
//                        abrirCamara();
//                    default:
//                        imgMascota.setImageResource(R.drawable.imegen_defecto);
//                        imagen = R.drawable.imegen_defecto;
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                imgMascota.setImageResource(R.drawable.imegen_defecto);
//            }
//        });

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

            // Crear objeto Mascota
//            Mascota nuevaMascota = new Mascota(nombre, raza, imagen, edad, peso, vacunada, desparacitada, esterilizada);

            if (imagenByteArray != null) {
                nuevaMascota = new Mascota(nombre, raza, imagenByteArray, edad, peso, vacunada, desparacitada, esterilizada);
            } else {
                nuevaMascota = new Mascota(nombre, raza, imagen, edad, peso, vacunada, desparacitada, esterilizada);
            }

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

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");

            // Convertir Bitmap a byte[]
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            imgBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//            imagenByteArray = byteArrayOutputStream.toByteArray();

            imagenByteArray = Util.bitmapToByteArray(imgBitmap);

            imgMascota.setImageBitmap(imgBitmap);
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
