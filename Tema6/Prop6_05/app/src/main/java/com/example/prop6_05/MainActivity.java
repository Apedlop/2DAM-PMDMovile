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

        switch (item.getItemId()) {
            // Submenú de días de la semana
            case R.id.lunes:
                mensaje = "Pulsado LUNES";
                break;
            case R.id.martes:
                mensaje = "Pulsado MARTES";
                break;
            case R.id.miercoles:
                mensaje = "Pulsado MIÉRCOLES";
                break;
            case R.id.jueves:
                mensaje = "Pulsado JUEVES";
                break;
            case R.id.viernes:
                mensaje = "Pulsado VIERNES";
                break;
            case R.id.sabado:
                mensaje = "Pulsado SÁBADO";
                break;
            case R.id.domingo:
                mensaje = "Pulsado DOMINGO";
                break;

            // Submenú de meses del año
            case R.id.enero:
                mensaje = "Pulsado ENERO";
                break;
            case R.id.febrero:
                mensaje = "Pulsado FEBRERO";
                break;
            case R.id.marzo:
                mensaje = "Pulsado MARZO";
                break;
            case R.id.abril:
                mensaje = "Pulsado ABRIL";
                break;
            case R.id.mayo:
                mensaje = "Pulsado MAYO";
                break;
            case R.id.junio:
                mensaje = "Pulsado JUNIO";
                break;
            case R.id.julio:
                mensaje = "Pulsado JULIO";
                break;
            case R.id.agosto:
                mensaje = "Pulsado AGOSTO";
                break;
            case R.id.septiembre:
                mensaje = "Pulsado SEPTIEMBRE";
                break;
            case R.id.octubre:
                mensaje = "Pulsado OCTUBRE";
                break;
            case R.id.noviembre:
                mensaje = "Pulsado NOVIEMBRE";
                break;
            case R.id.diciembre:
                mensaje = "Pulsado DICIEMBRE";
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        // Establecer el texto en el TextView
        textView.setText(mensaje);
        return true;
    }
}
