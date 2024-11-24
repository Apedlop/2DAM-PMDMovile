package com.example.editarlista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    private EditText editText;
    private int itemPosition;
    private String oldItemData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Inicializamos el EditText
        editText = findViewById(R.id.editText);

        // Obtenemos la posición del item y el dato a editar desde la Intent
        itemPosition = getIntent().getIntExtra("item_position", -1);
        oldItemData = getIntent().getStringExtra("item_data");

        // Mostramos el dato que se va a editar en el EditText
        editText.setText(oldItemData);

        // Configuramos el botón para guardar los cambios
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveItem());
    }

    private void saveItem() {
        // Obtener el nuevo dato editado
        String newItemData = editText.getText().toString();

        // Validar si el dato no está vacío
        if (newItemData.isEmpty()) {
            editText.setError("El campo no puede estar vacío");
            return;
        }

        // Crear un Intent para devolver el dato editado
        Intent resultIntent = new Intent();
        resultIntent.putExtra("item_position", itemPosition); // Pasar la posición
        resultIntent.putExtra("new_item_data", newItemData);  // Pasar el nuevo dato
        setResult(RESULT_OK, resultIntent);

        // Finalizar la actividad y regresar a la actividad principal
        finish();
    }
}
