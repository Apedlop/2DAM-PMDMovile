package com.example.editarlista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    private EditText editText;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editText = findViewById(R.id.editText);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        Button saveButton = findViewById(R.id.buttonSave);

        // Recibir los datos enviados desde MainActivity
        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        String text = intent.getStringExtra("text");
        int selectedOption = intent.getIntExtra("option", 0);

        // Configurar los campos para que el usuario pueda editarlos
        editText.setText(text);

        if (selectedOption == 1) {
            radioButton1.setChecked(true);
        } else if (selectedOption == 2) {
            radioButton2.setChecked(true);
        }

        // Configurar el botón de guardar
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos editados
                String newText = editText.getText().toString();
                int newOption = radioGroup.getCheckedRadioButtonId() == R.id.radioButton1 ? 1 : 2;

                // Devolver los datos modificados a MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("position", position);
                resultIntent.putExtra("text", newText);  // El texto editado
                resultIntent.putExtra("option", selectedOption);  // La opción seleccionada en el RadioGroup
                setResult(RESULT_OK, resultIntent);
                finish();  // Finaliza la actividad y regresa a MainActivity

            }
        });
    }
}
