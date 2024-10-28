package com.example.ejemploscheck;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SeekBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar); // Asegúrate de que este es el nombre correcto del layout

        // Referencias a los elementos del layout
        SeekBar mySeekBar = findViewById(R.id.mySeekBar);
        TextView seekBarValue = findViewById(R.id.seekBarValue);

        // Configurar el listener para el SeekBar
        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Actualizar el TextView con el valor actual del SeekBar
                seekBarValue.setText("Valor: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Aquí puedes agregar lógica si lo necesitas cuando comienza el seguimiento
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Aquí puedes agregar lógica si lo necesitas cuando se detiene el seguimiento
            }
        });
    }
}
