package com.example.toast;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencia al botón en el layout
        Button btnShowToast = findViewById(R.id.btnShowToast);

        // Configurar la acción del botón
        btnShowToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar un Toast al hacer clic en el botón
                Toast.makeText(MainActivity.this, "Hola, esto es un Toast", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
