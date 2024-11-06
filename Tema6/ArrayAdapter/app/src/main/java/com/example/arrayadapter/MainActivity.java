package com.example.arrayadapter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crear los datos de ejemplo con IDs de recursos de imágenes en drawable
        Datos[] datos = new Datos[]{
                new Datos("Título 1", "Subtítulo 1", R.drawable.oso, "https://www.google.com"),
                new Datos("Título 2", "Subtítulo 2", R.drawable.mono, "https://www.google.com"),
                new Datos("Título 3", "Subtítulo 3", R.drawable.cerdo, "https://www.google.com"),
                new Datos("Título 4", "Subtítulo 4", R.drawable.perro, "https://www.google.com")
        };

        ListView listado = findViewById(R.id.miLista);

        // Inflar la cabecera y añadirla al ListView
        View miCabecera = getLayoutInflater().inflate(R.layout.cabecera, null);
        listado.addHeaderView(miCabecera);

        // Configurar el adaptador
        Adaptador miAdaptador = new Adaptador(this, datos);
        listado.setAdapter(miAdaptador);

        // Configurar el listener de clics en los elementos de la lista
        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adaptador, View v, int position, long id) {
                String seleccionado = ((Datos) adaptador.getItemAtPosition(position)).getTexto1();
                Toast.makeText(MainActivity.this, "Seleccionado: " + seleccionado, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
