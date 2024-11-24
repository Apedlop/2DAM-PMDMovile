package com.example.prop6_05;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView textView; // Declaramos el TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView); // Inicializamos el TextView desde el XML

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones, menu); // Inflamos el menú
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String mensaje = "";

        if (item.getItemId() == R.id.lunes) {
            mensaje = "Pulsado LUNES";
        } else if (item.getItemId() == R.id.martes) {
            mensaje = "Pulsado MARTES";
        } else if (item.getItemId() == R.id.miercoles) {
            mensaje = "Pulsado MIÉRCOLES";
        } else if (item.getItemId() == R.id.jueves) {
            mensaje = "Pulsado JUEVES";
        } else if (item.getItemId() == R.id.viernes) {
            mensaje = "Pulsado VIERNES";
        } else if (item.getItemId() == R.id.sabado) {
            mensaje = "Pulsado SÁBADO";
        } else if (item.getItemId() == R.id.domingo) {
            mensaje = "Pulsado DOMINGO";
        } else if (item.getItemId() == R.id.enero) {
            mensaje = "Pulsado ENERO";
        } else if (item.getItemId() == R.id.febrero) {
            mensaje = "Pulsado FEBRERO";
        } else if (item.getItemId() == R.id.marzo) {
            mensaje = "Pulsado MARZO";
        } else if (item.getItemId() == R.id.abril) {
            mensaje = "Pulsado ABRIL";
        } else if (item.getItemId() == R.id.mayo) {
            mensaje = "Pulsado MAYO";
        } else if (item.getItemId() == R.id.junio) {
            mensaje = "Pulsado JUNIO";
        } else if (item.getItemId() == R.id.julio) {
            mensaje = "Pulsado JULIO";
        } else if (item.getItemId() == R.id.agosto) {
            mensaje = "Pulsado AGOSTO";
        } else if (item.getItemId() == R.id.septiembre) {
            mensaje = "Pulsado SEPTIEMBRE";
        } else if (item.getItemId() == R.id.octubre) {
            mensaje = "Pulsado OCTUBRE";
        } else if (item.getItemId() == R.id.noviembre) {
            mensaje = "Pulsado NOVIEMBRE";
        } else if (item.getItemId() == R.id.diciembre) {
            mensaje = "Pulsado DICIEMBRE";
        } else {
            return super.onOptionsItemSelected(item);
        }

        // Establecer el texto en el TextView
        textView.setText(mensaje);
        return true;

    }
}
