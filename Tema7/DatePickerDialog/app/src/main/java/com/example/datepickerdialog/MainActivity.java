package com.example.datepickerdialog;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView textViewFecha;
    private Button buttonFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener las referencias de los componentes
        textViewFecha = findViewById(R.id.textViewFecha);
        buttonFecha = findViewById(R.id.buttonFecha);

        // Inicializar el calendario para obtener la fecha actual
        final Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear el DatePickerDialog
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                MainActivity.this,
                (view, year, month, dayOfMonth) -> {
                    // Formatear la fecha seleccionada
                    String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                    textViewFecha.setText(fechaSeleccionada); // Mostrar la fecha en el TextView
                },
                ano, mes, dia); // Fecha por defecto (actual)

        // Configurar el evento click del botón
        buttonFecha.setOnClickListener(v -> {
            // Mostrar el DatePickerDialog
            datePickerDialog.show();
        });
    }
}
