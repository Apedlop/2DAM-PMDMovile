package com.example.prop_03;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ToastEditText extends AppCompatActivity {

    EditText entradaTexto;
    Button botonMostrarToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast_edit_text);

        // Inicializar componentes
        entradaTexto = findViewById(R.id.entradaTexto);
        botonMostrarToast = findViewById(R.id.botonMostrarToast);

        // Configurar el evento del botón
        botonMostrarToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = entradaTexto.getText().toString();
                if (!mensaje.isEmpty()) {
                    Toast.makeText(ToastEditText.this, mensaje, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ToastEditText.this, "Por favor, escribe un mensaje", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
