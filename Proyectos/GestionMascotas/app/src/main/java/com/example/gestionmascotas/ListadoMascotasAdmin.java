package com.example.gestionmascotas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class ListadoMascotasAdmin extends AppCompatActivity {

    private ListView lista;
    private Mascota mascotaSeleccionada;
    private ArrayList<Mascota> listaMascotas;
    private TextView textoFecha, textoHora;
    private Button botonFecha, botonHora;
    private ImageButton themeToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_mascotas);

        textoFecha = findViewById(R.id.textoFecha);
        botonFecha = findViewById(R.id.botonFecha);
        textoHora = findViewById(R.id.textoHora);
        botonHora = findViewById(R.id.botonHora);

        // Calendario
        final Calendar calendario = Calendar.getInstance();
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        // Crear el DatePickerDialog
        final DatePickerDialog fecha = new DatePickerDialog(ListadoMascotasAdmin.this, (view, year, month, dayOfMonth) -> {
            // Formatear la fecha seleccionada
            String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
            textoFecha.setText(fechaSeleccionada); // Mostrar la fecha en el TextView
        }, ano, mes, dia); // Fecha por defecto (actual)

        botonFecha.setOnClickListener(v -> {
            Log.d("MainActivity", "BOTON");
            fecha.show();
        });

        // Hora
        final Calendar calendar = Calendar.getInstance();
        int horas = calendario.get(Calendar.HOUR_OF_DAY);
        int minutos = calendario.get(Calendar.MINUTE);

        TimePickerDialog hora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String horaSeleccionada = hourOfDay + ":" + minute;
                textoHora.setText(horaSeleccionada);
            }
        }, horas, minutos, false);

        botonHora.setOnClickListener(v -> {
            hora.show();
        });

        // Obtener nombre del usuario
        Intent obtenerUsuario = getIntent();
        String usuario = obtenerUsuario.getStringExtra("usuario");

        // Añadir menu personalizado
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Cambiar del tema claro al oscuro
        themeToggle = findViewById(R.id.themeToggle);

        // Leer el estado del tema desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE);

        themeToggle.setOnClickListener(v -> {
            // Cambiar el modo de tema
            boolean darkMode = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;

            if (darkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);  // Modo claro
                themeToggle.setImageResource(R.drawable.mod_oscuro);  // Icono para cambiar a modo oscuro
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);  // Modo oscuro
                themeToggle.setImageResource(R.drawable.mod_claro);  // Icono para cambiar a modo claro
            }

            // Guardar el nuevo estado en SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isDarkMode", !darkMode);  // Cambiar el valor en SharedPreferences
            editor.apply();
        });

        // Inicializar la lista de mascotas
        listaMascotas = new ArrayList<>();
        lista = findViewById(R.id.lista);

        // Cambiar nombre del usuario
        TextView nomUsuario = findViewById(R.id.nomUsuario);
        nomUsuario.setText(usuario);

        // Agregar algunas mascotas de ejemplo a la lista
        listaMascotas.add(new Mascota("Yoyo", "Bodeguero", R.drawable.imagen_perro, 3, 25.0f, true, true, false));
        listaMascotas.add(new Mascota("Leon", "Labrador", R.drawable.imagen_perro, 5, 30.5f, true, false, true));

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

            // Pasar el objeto Mascota completo a la nueva Activity
            intent.putExtra("mascota", mascotaSeleccionada);
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
        int posicion = info.position;

        if (item.getItemId() == R.id.opEditar) {
            Mascota editarItem = listaMascotas.get(posicion);
            Intent intent = new Intent(this, EditarMascota.class);
            intent.putExtra("mascota", editarItem);
            intent.putExtra("position", posicion);  // Pasamos la posición para actualizarla después
            startActivityForResult(intent, 1);
            return true;
        } else if (item.getItemId() == R.id.opEliminar) {
            // Crear el cuadro de diálogo de confirmación
            new AlertDialog.Builder(this) // O usa 'getContext()' si estás en un fragmento
                    .setTitle("Confirmar eliminación")
                    .setMessage("¿Estás seguro de que deseas eliminar esta mascota?")
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        // Lógica para eliminar la mascota
                        if (posicion >= 0 && posicion < listaMascotas.size()) {
                            listaMascotas.remove(posicion);

                            // Notificar al adaptador que los datos han cambiado
                            ((Adaptador) lista.getAdapter()).notifyDataSetChanged();

                            // Mostrar mensaje de éxito
                            mostrarToast("Mascota eliminada");
                        } else {
                            // Mostrar mensaje de error si la posición no es válida
                            mostrarToast("Error al eliminar la mascota");
                        }
                    })
                    .setNegativeButton("Cancelar", (dialog, which) -> {
                        // Cerrar el diálogo sin hacer nada
                        dialog.dismiss();
                    })
                    .show();
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Mascota mascotaModificada = (Mascota) data.getSerializableExtra("mascota");
            int posicion = data.getIntExtra("position", -1);

            if (posicion >= 0 && posicion < listaMascotas.size()) {
                listaMascotas.set(posicion, mascotaModificada);  // Reemplaza el objeto en la lista

                // Notifica al adaptador que los datos han cambiado
                ((Adaptador) lista.getAdapter()).notifyDataSetChanged();
                mostrarToast("Mascota modificada con éxito");
            }
        } else  if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            ArrayList<Mascota> listaMascotasRecibida = (ArrayList<Mascota>) data.getSerializableExtra("mascotas");

            if (listaMascotasRecibida != null && !listaMascotasRecibida.isEmpty()) {
                // Obtener la última mascota de la lista
                Mascota ultimaMascota = listaMascotasRecibida.get(listaMascotasRecibida.size() - 1);

                // Asegúrate de no añadir la misma mascota si ya está en la lista
                if (!listaMascotas.contains(ultimaMascota)) {
                    listaMascotas.add(ultimaMascota);  // Añadir solo la última mascota
                    ((Adaptador) lista.getAdapter()).notifyDataSetChanged();
                    mostrarToast("Mascota añadida");
                }
            }
        }


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
        } else if (item.getItemId() == R.id.añadirMascota) {
            // Añadir una mascota nueva
            Intent intent = new Intent(this, AñadirMascota.class);
            startActivityForResult(intent, 2);
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
