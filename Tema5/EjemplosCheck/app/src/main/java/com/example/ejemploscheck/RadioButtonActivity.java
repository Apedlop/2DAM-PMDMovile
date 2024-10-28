package com.example.ejemploscheck;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class RadioButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_button); // Asegúrate de que el nombre coincida

        RadioGroup grupo = findViewById(R.id.grupo);

        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                // Haz algo con el RadioButton seleccionado
                String selectedText = radioButton.getText().toString();
                // Por ejemplo, puedes mostrar un Toast (opcional)
                // Toast.makeText(RadioButtonActivity.this, "Seleccionado: " + selectedText, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
