package com.example.prop6_01;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int selectedPosition = -1; // Posición del elemento seleccionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.miLista);
        ArrayList<String> items = new ArrayList<>();
        items.add("España");
        items.add("Francia");
        items.add("Italia");
        items.add("Portugal");
        items.add("Alemania");
        items.add("Grecia");
        items.add("Países Bajos");
        items.add("Suiza");
        items.add("Bélgica");
        items.add("Suecia");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        TextView textView = findViewById(R.id.miTextView);

        // Evento al hacer clic en un elemento de la lista para seleccionarlo
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position; // Guardar la posición del elemento seleccionado
                String selectedItem = adapter.getItem(position);
                textView.setText(selectedItem); // Mostrar en un TextView
            }
        });

        // Botón para añadir un nuevo elemento
        EditText inputText = findViewById(R.id.inputText);
        Button boton1 = findViewById(R.id.añadir);
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newItem = inputText.getText().toString();
                if (!newItem.isEmpty()) {
                    items.add(newItem);
                    adapter.notifyDataSetChanged(); // Actualizar la vista
                    inputText.setText(""); // Limpiar el campo de texto
                } else {
                    Toast.makeText(MainActivity.this, "Escribe algo para añadir", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Botón para eliminar el elemento seleccionado
        Button boton2 = findViewById(R.id.eliminar);
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPosition != -1) { // Verificar si se ha seleccionado un elemento
                    String removedItem = items.get(selectedPosition); // Obtener el elemento a eliminar
                    items.remove(selectedPosition); // Eliminar de la lista
                    adapter.notifyDataSetChanged(); // Actualizar el adaptador
                    textView.setText(""); // Limpiar el TextView
                    selectedPosition = -1; // Reiniciar la posición seleccionada
                    Toast.makeText(MainActivity.this, "Eliminado: " + removedItem, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Selecciona un elemento para eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
