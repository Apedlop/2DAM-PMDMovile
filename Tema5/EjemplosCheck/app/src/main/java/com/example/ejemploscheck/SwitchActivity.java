package com.example.ejemploscheck;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SwitchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch); // Asegúrate de que el nombre del layout sea correcto

        // Referencia al Switch en el layout
        Switch miSwitch = findViewById(R.id.miSwitch);

        // Configurar el listener para cambios de estado del Switch
        miSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Mostrar un mensaje dependiendo del estado del Switch
                if (isChecked) {
                    Toast.makeText(SwitchActivity.this, "Switch activado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SwitchActivity.this, "Switch desactivado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}