package com.example.prop_03;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ToastSeekBar extends AppCompatActivity {

    SeekBar seekBarPosicion;
    Button botonMostrarSeekbarToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast_seek_bar);

        // Inicializar componentes
        seekBarPosicion = findViewById(R.id.seekBarPosicion);
        botonMostrarSeekbarToast = findViewById(R.id.botonMostrarSeekbarToast);

        // Configurar el evento del botón
        botonMostrarSeekbarToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicion = seekBarPosicion.getProgress();

                // Crear y posicionar el Toast
                Toast toast = Toast.makeText(ToastSeekBar.this, "Toast personalizado", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, posicion * 20);
                toast.show();
            }
        });
    }
}
