package com.example.mascota;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class EditarMascota extends AppCompatActivity {

    private EditText etNombre, etRaza, etEdad, etPeso;
    private RadioGroup rgVacunada, rgDesparacitada, rgEsterilizada;
    private Button btnGuardar, btnCancelar;
    private Spinner spinnerTipoMascota;
    private ImageView imgMascota;
    private Contenido.Lista_entrada mascota;
    private int posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mascota);

        // Obtener datos del Intent
        Intent intent = getIntent();
        mascota = (Contenido.Lista_entrada) intent.getSerializableExtra("mascota");
        posicion = intent.getIntExtra("position", -1);

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

        // Rellenar los campos con los datos de la mascota
        etNombre.setText(mascota.getNombre());
        etRaza.setText(mascota.getRaza());
        etEdad.setText(String.valueOf(mascota.getEdad()));
        etPeso.setText(String.valueOf(mascota.getPeso()));

        // Asignar los valores de vacunada, desparacitada y esterilizada a los RadioButtons
        rgVacunada.check(mascota.isVacunada() ? R.id.radio_vacunado_si : R.id.radio_vacunado_no);
        rgDesparacitada.check(mascota.isDesparacitada() ? R.id.radio_desparasitado_si : R.id.radio_desparasitado_no);
        rgEsterilizada.check(mascota.isEsterilizada() ? R.id.radio_esterilizado_si : R.id.radio_esterilizado_no);

        // Configurar el evento para el Spinner
        spinnerTipoMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, android.view.View selectedItemView, int position, long id) {
                String tipoSeleccionado = parentView.getItemAtPosition(position).toString();
                // Cambiar la imagen según el tipo de mascota seleccionado
                cambiarImagen(tipoSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                imgMascota.setImageResource(R.drawable.imegen_defecto);
            }
        });

        // Guardar los cambios cuando se haga clic en el botón
        btnGuardar.setOnClickListener(v -> {
            // Obtener los valores de los campos
            mascota.setNombre(etNombre.getText().toString().trim());
            mascota.setRaza(etRaza.getText().toString().trim());
            mascota.setEdad(Integer.parseInt(etEdad.getText().toString().trim()));
            mascota.setPeso(Float.parseFloat(etPeso.getText().toString().trim()));

            // Obtener el ID de la imagen seleccionada y guardarlo
            mascota.setIdImagen(getImageResource(imgMascota));  // Pasar el recurso de la imagen

            // Obtener los valores de los RadioButtons
            mascota.setVacunada(rgVacunada.getCheckedRadioButtonId() == R.id.radio_vacunado_si);
            mascota.setDesparacitada(rgDesparacitada.getCheckedRadioButtonId() == R.id.radio_desparasitado_si);
            mascota.setEsterilizada(rgEsterilizada.getCheckedRadioButtonId() == R.id.radio_esterilizado_si);

            // Devolver la mascota modificada y la posición a ListadoMascotasAdmin
            Intent resultIntent = new Intent();
            resultIntent.putExtra("mascota", (CharSequence) mascota);
            resultIntent.putExtra("position", posicion);

            setResult(RESULT_OK, resultIntent);
            finish();
        });

        // Acción de cancelar
        btnCancelar.setOnClickListener(v -> {
            setResult(RESULT_CANCELED); // Indicar que el usuario canceló la edición
            finish();
        });
    }

    // Método para cambiar la imagen de la mascota según el tipo seleccionado
    private void cambiarImagen(String tipoSeleccionado) {
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

    // Método para obtener el recurso de la imagen de la mascota seleccionada
    private int getImageResource(ImageView imageView) {
        if (imageView.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.imagen_perro).getConstantState())) {
            return R.drawable.imagen_perro;
        } else if (imageView.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.imagen_gato).getConstantState())) {
            return R.drawable.imagen_gato;
        } else if (imageView.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.imagen_conejo).getConstantState())) {
            return R.drawable.imagen_conejo;
        } else if (imageView.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.imagen_hamster).getConstantState())) {
            return R.drawable.imagen_hamster;
        } else {
            return R.drawable.imegen_defecto;
        }
    }
}
