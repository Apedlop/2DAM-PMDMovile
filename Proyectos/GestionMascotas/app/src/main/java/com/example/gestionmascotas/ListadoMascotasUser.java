package com.example.gestionmascotas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListadoMascotasUser extends AppCompatActivity {

    private ListView lista;
    private Mascota mascotaSeleccionada;
    private ArrayList<Mascota> listaMascotas;
    private EditText fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_mascotas);

        // Obtener nombre del usuario
        Intent obtenerUsuario = getIntent();
        String usuario = obtenerUsuario.getStringExtra("usuario");

        // Añadir menu personalizado
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Inicializar la lista de mascotas
        listaMascotas = new ArrayList<>();
        lista = findViewById(R.id.lista);

        // Cambiar nombre del usuario
        TextView nomUsuario = findViewById(R.id.nomUsuario);
        nomUsuario.setText(usuario);

        // Crear los datos para el adaptador
        listaMascotas.add(new Mascota("Yoyo", "Bodeguero", R.drawable.imagen_perro, 3, 25.0f, true, true, false));
        listaMascotas.add(new Mascota("Max", "Labrador", R.drawable.imagen_perro, 5, 30.5f, true, false, true));

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
                        edad.setText("Edad: " + String.valueOf(mascota.getEdad()));
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
            Intent intent = new Intent(ListadoMascotasUser.this, InfoDetallada.class);

            // Pasar el objeto Mascota completo a la nueva Activity
            intent.putExtra("mascota", mascotaSeleccionada);
            intent.putExtra("usuario", usuario);

            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú (tres puntitos)
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Manejo del menú normal
        if (item.getItemId() == R.id.ordenarNombre) {
            // Ordenar por nombre
            Collections.sort(listaMascotas, new Comparator<Mascota>() {
                @Override
                public int compare(Mascota o1, Mascota o2) {
                    return o1.getNombre().compareToIgnoreCase(o2.getNombre());
                }
            });
            // Notificar al adaptador
            ((Adaptador) lista.getAdapter()).notifyDataSetChanged();
            mostrarToast("Lista ordenada por nombre");
            return true;

        } else if (item.getItemId() == R.id.ordenarRaza) {
            // Ordenar por raza
            Collections.sort(listaMascotas, new Comparator<Mascota>() {
                @Override
                public int compare(Mascota o1, Mascota o2) {
                    return o1.getRaza().compareToIgnoreCase(o2.getRaza());
                }
            });
            // Notificar al adaptador
            ((Adaptador) lista.getAdapter()).notifyDataSetChanged();
            mostrarToast("Lista ordenada por raza");
            return true;
        }

        // Si no es ninguna de las anteriores opciones, delega el resto a la implementación por defecto
        return super.onOptionsItemSelected(item);
    }

    // Método para mostrar toast
    @SuppressLint("MissingInflatedId")
    public void mostrarToast(String mensaje) {
        // Toast personalizado
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.layotu_toast));
        TextView textoToast = layout.findViewById(R.id.textoToast);
        textoToast.setText(mensaje);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}
