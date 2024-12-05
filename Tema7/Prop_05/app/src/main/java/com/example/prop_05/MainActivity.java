package com.example.prop_05;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnDate = findViewById(R.id.btnDatePickerDialog);
        Button btnTime = findViewById(R.id.btnTimePicker);

        btnDate.setOnClickListener(v -> {
            mostrarFecha();
        });

        btnTime.setOnClickListener(v -> {
            mostrarHora();
        });
    }

    private void mostrarFecha() {
        final Calendar calendario = Calendar.getInstance();
        int año = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
            Toast.makeText(this, "Fecha seleccionada " + fechaSeleccionada, Toast.LENGTH_SHORT).show();
        }, año, mes, dia);
        datePickerDialog.show();
    }

    private void mostrarHora() {
        final Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            String horaSeleccionada = hourOfDay + ":" + minute;
            Toast.makeText(this, "Hora seleccionada: " + horaSeleccionada, Toast.LENGTH_SHORT).show();
        }, hora, minuto, true);
        timePickerDialog.show();
    }
}

