package com.example.editarlista;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar el ListView y los datos
        listView = findViewById(R.id.listView);
        items = new ArrayList<>();
        items.add("Elemento 1");
        items.add("Elemento 2");
        items.add("Elemento 3");

        // Configurar el adaptador
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        // Registrar el ListView para el menú contextual
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // Inflar el menú contextual
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position; // Obtener la posición del elemento seleccionado

        // Usamos un if-else en lugar de switch
        if (item.getItemId() == R.id.edit_option) {
            // Llamar a la actividad para editar el item
            String itemToEdit = items.get(position);
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            intent.putExtra("item_position", position); // Pasar la posición del ítem
            intent.putExtra("item_data", itemToEdit);   // Pasar el dato a editar
            startActivityForResult(intent, 1);  // Iniciar la actividad para editar
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            int position = data.getIntExtra("item_position", -1);
            String newItemData = data.getStringExtra("new_item_data");

            if (position != -1 && newItemData != null) {
                // Actualizar el item en la lista
                items.set(position, newItemData);

                // Notificar al adaptador para refrescar el ListView
                adapter.notifyDataSetChanged();
            }
        }
    }
}
