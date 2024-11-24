package com.example.gestionmascotas;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListadoMascotasAdmin extends AppCompatActivity {

    private ListView lista;
    private Mascota mascotaSeleccionada;
    private ArrayList<Mascota> listaMascotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_mascotas);

        // Obtener nombre del usuario
        Intent obtenerUsuario = getIntent();
        String usuario = obtenerUsuario.getStringExtra("usuario");

        // Inicializar la lista de mascotas
        listaMascotas = new ArrayList<>();
        lista = findViewById(R.id.lista);

        // Inflar la cabecera y añadirla en el ListView
        View cabecera = getLayoutInflater().inflate(R.layout.cabecera, null);
        lista.addHeaderView(cabecera);

        // Cambiar nombre del usuario en la cabecera
        TextView nomUsuario = cabecera.findViewById(R.id.nomUsuario);
        if (nomUsuario != null) {
            nomUsuario.setText(usuario);
        }

        // Agregar algunas mascotas de ejemplo a la lista
        listaMascotas.add(new Mascota("Yoyo", "Bodeguero", R.drawable.bodeguero, 3, 25.0f, true, true, false));
        listaMascotas.add(new Mascota("Leon", "Labrador", R.drawable.bodeguero, 5, 30.5f, true, false, true));

        // Configurar el adaptador para el ListView
        lista.setAdapter(new Adaptador(listaMascotas, R.layout.elementos, this) {
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null && view != null) {
                    TextView nombre = view.findViewById(R.id.nombre);
                    TextView edad = view.findViewById(R.id.edad);
                    TextView raza = view.findViewById(R.id.raza);
                    ImageView imagen = view.findViewById(R.id.imagen);

                    if (nombre != null && edad != null && raza != null && imagen != null) {
                        Mascota mascota = (Mascota) entrada;
                        nombre.setText(mascota.getNombre());
                        edad.setText("Edad: " + mascota.getEdad());
                        raza.setText("Raza: " + mascota.getRaza());
                        imagen.setImageResource(mascota.getImagen());
                    }
                }
            }
        });

        // Configurar el evento de clic en los ítems del ListView
        lista.setOnItemClickListener((parent, view, position, id) -> {
            mascotaSeleccionada = (Mascota) parent.getItemAtPosition(position);

            // Crear un Intent para abrir la Activity InfoDetallada
            Intent intent = new Intent(ListadoMascotasAdmin.this, InfoDetallada.class);

            // Pasar los datos de la mascota a la nueva Activity
            intent.putExtra("nombre", mascotaSeleccionada.getNombre());
            intent.putExtra("raza", mascotaSeleccionada.getRaza());
            intent.putExtra("imagen", mascotaSeleccionada.getImagen());
            intent.putExtra("edad", mascotaSeleccionada.getEdad());
            intent.putExtra("peso", mascotaSeleccionada.getPeso());
            intent.putExtra("vacunada", mascotaSeleccionada.isVacunada());
            intent.putExtra("desparacitada", mascotaSeleccionada.isDesparacitada());
            intent.putExtra("esterilizada", mascotaSeleccionada.isEsterilizada());
            intent.putExtra("usuario", usuario);

            startActivity(intent);
        });

        // Registrar el menú contextual para el ListView
        registerForContextMenu(lista);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int posicion = info.position - 1; // Ajustar posición por la cabecera

        if (item.getItemId() == R.id.opEditar) {
            Mascota editarItem = listaMascotas.get(posicion);
            Intent intent = new Intent(this, EditarMascota.class);
            intent.putExtra("nombre", editarItem.getNombre());
            intent.putExtra("raza", editarItem.getRaza());
            intent.putExtra("imagen", editarItem.getImagen());
            intent.putExtra("edad", editarItem.getEdad());
            intent.putExtra("peso", editarItem.getPeso());
            intent.putExtra("vacunada", editarItem.isVacunada());
            intent.putExtra("desparacitada", editarItem.isDesparacitada());
            intent.putExtra("esterilizada", editarItem.isEsterilizada());
            startActivityForResult(intent, RESULT_OK);
            return true;
        } else if (item.getItemId() == R.id.opEliminar) {
            if (posicion >= 0 && posicion < listaMascotas.size()) {
                // Eliminar la mascota
                listaMascotas.remove(posicion);

                // Obtener el adaptador envuelto y notificar el cambio
                Adaptador adaptador = (Adaptador) ((HeaderViewListAdapter) lista.getAdapter()).getWrappedAdapter();
                adaptador.notifyDataSetChanged();

                // Mostrar mensaje de éxito
                Toast.makeText(this, "Mascota eliminada", Toast.LENGTH_SHORT).show();
            } else {
                // Mostrar mensaje de error si la posición no es válida
                Toast.makeText(this, "Error al eliminar la mascota", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            // Obtener los datos de la mascota editada desde el Intent
            String nombre = data.getStringExtra("nombre");
            String raza = data.getStringExtra("raza");
            int imagen = data.getIntExtra("imagen", R.drawable.bodeguero); // Imagen por defecto
            int edad = data.getIntExtra("edad", 0);
            float peso = data.getFloatExtra("peso", 0.0f);
            boolean vacunada = data.getBooleanExtra("vacunada", false);
            boolean desparacitada = data.getBooleanExtra("desparacitada", false);
            boolean esterilizada = data.getBooleanExtra("esterilizada", false);

            Toast.makeText(this, "Datos recibidos: " + nombre + ", " + raza, Toast.LENGTH_SHORT).show();


            // Buscar la mascota seleccionada y actualizar sus datos
            if (mascotaSeleccionada != null) {
                mascotaSeleccionada.setNombre(nombre);
                mascotaSeleccionada.setRaza(raza);
                mascotaSeleccionada.setImagen(imagen);
                mascotaSeleccionada.setEdad(edad);
                mascotaSeleccionada.setPeso(peso);
                mascotaSeleccionada.setVacunada(vacunada);
                mascotaSeleccionada.setDesparacitada(desparacitada);
                mascotaSeleccionada.setEsterilizada(esterilizada);

                // Notificar al adaptador que los datos han cambiado
                Adaptador adaptador = (Adaptador) ((HeaderViewListAdapter) lista.getAdapter()).getWrappedAdapter();
                adaptador.notifyDataSetChanged();

                // Mostrar mensaje de éxito
                Toast.makeText(this, "Mascota actualizada", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
