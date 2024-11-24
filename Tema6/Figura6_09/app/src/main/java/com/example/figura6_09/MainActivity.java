package com.example.figura6_09;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] datos = new String[] {"OPCIÓN DE MENÚ A", "OPCIÓN DE MENÚ B", "OPCIÓN DE MENÚ C", "OPCIÓN DE MENÚ D", "OPCIÓN DE MENÚ E"};
        lista = (ListView) findViewById(R.id.listado);
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos);
        lista.setAdapter(adaptador);
        registerForContextMenu(lista);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(lista.getAdapter().getItem(info.position).toString());

        switch (info.position) {
            case 0:
                inflater.inflate(R.menu.menu_lista1, menu);
                return;
            case 1:
                inflater.inflate(R.menu.menu_lista2, menu);
                return;
            case 2:
                inflater.inflate(R.menu.menu_lista3, menu);
                return;
            default:
                return;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String itemSeleccionado = lista.getAdapter().getItem(info.position).toString();

        // Usamos if-else en lugar de switch
        if (item.getItemId() == R.id.accion1) {
            // Acción para la primera opción de cada menú
            Toast.makeText(this, "Seleccionaste " + item.getTitle() + " para " + itemSeleccionado, Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.accion2) {
            // Acción para la segunda opción de cada menú
            Toast.makeText(this, "Seleccionaste " + item.getTitle() + " para " + itemSeleccionado, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

}
