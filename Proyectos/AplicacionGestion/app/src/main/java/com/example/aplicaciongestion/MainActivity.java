package com.example.aplicaciongestion;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<Elementos> listaElementos = new ArrayList<>();
    private Adaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar el ListView
        listView = findViewById(R.id.lista);

        // Crear elementos
        listaElementos.add(new Elementos("Título 1", "Contenido 1"));
        listaElementos.add(new Elementos("Título 2", "Contenido 2"));

        // Crear y asignar el adaptador
        adaptador = new Adaptador(this, listaElementos);
        listView.setAdapter(adaptador);
    }
}
