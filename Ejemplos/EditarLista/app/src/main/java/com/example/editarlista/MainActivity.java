package com.example.editarlista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.editarlista.CustomAdapter;
import com.example.editarlista.ListItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ListItem> items;
    CustomAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa los elementos de la lista
        items = new ArrayList<>();
        items.add(new ListItem("Elemento 1", 0));
        items.add(new ListItem("Elemento 2", 0));
        items.add(new ListItem("Elemento 3", 0));

        // Configura el adaptador
        adapter = new CustomAdapter(this, items);  // Debería ser solo 'items', sin la referencia 'resource'
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // Al hacer clic en un ítem de la lista, abrir EditActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Crear un intent para abrir la EditActivity
                Intent intent = new Intent(MainActivity.this, EditActivity.class);

                // Pasar los datos
                intent.putExtra("position", position);
                intent.putExtra("text", items.get(position).getText());
                intent.putExtra("option", items.get(position).getSelecedOption());

                // Iniciar EditActivity
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            int position = data.getIntExtra("position", -1);
            String newText = data.getStringExtra("text");
            int newOption = data.getIntExtra("option", 0);

            if (position != -1) {
                items.get(position).setText(newText);  // Asegúrate de que setText esté implementado
                items.get(position).setSelecedOption(newOption);  // Asegúrate de que setSelecedOption esté implementado correctamente
                adapter.notifyDataSetChanged();  // Actualiza la lista
            }
        }
    }

}
