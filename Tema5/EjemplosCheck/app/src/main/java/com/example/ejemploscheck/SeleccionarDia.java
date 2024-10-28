package com.example.ejemploscheck;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SeleccionarDia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_dia);

        // Referencias a los widgets
        RadioGroup grupo = findViewById(R.id.grupo);
        TextView statusTextView = findViewById(R.id.statusTextView);

        // Listener para cambios en el RadioGroup
        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                // Cambia el texto del TextView según el RadioButton seleccionado
                statusTextView.setText("Pulsado " + radioButton.getText());
            }
        });
    }
}
