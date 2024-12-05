package com.example.prop_04;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnDialogoBotones = findViewById(R.id.btnDialogoBotones);
        Button btnDialogoListado = findViewById(R.id.btnDialogoListado);

        // Diálogo con botones
        btnDialogoBotones.setOnClickListener(v -> mostrarDialogoConBotones());

        // Diálogo con listado
        btnDialogoListado.setOnClickListener(v -> mostrarDialogoConListado());
    }

    // Método para el diálogo con botones
    private void mostrarDialogoConBotones() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Actividad de Diálogo")
                .setMessage("Toca uno de los botones")
                .setPositiveButton("POSITIVO", (dialog, which) ->
                        mostrarToast("Seleccionaste Positivo"))
                .setNegativeButton("NEGATIVO", (dialog, which) ->
                        mostrarToast("Seleccionaste Negativo"))
                .setNeutralButton("NEUTRO", (dialog, which) ->
                        mostrarToast("Seleccionaste Neutro"))
                .show();
    }

    // Método para el diálogo con listado
    private void mostrarDialogoConListado() {
        final String[] diasSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona un día")
                .setItems(diasSemana, (dialog, which) ->
                        mostrarToast("Seleccionaste: " + diasSemana[which]))
                .show();
    }

    // Método auxiliar para mostrar un Toast
    private void mostrarToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
