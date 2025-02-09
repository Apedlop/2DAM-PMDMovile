package com.example.gestionmascotas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class ListadoMascotasAdmin extends AppCompatActivity {

    private ListView lista;
    private Mascota mascotaSeleccionada;
    private ArrayList<Mascota> listaMascotas;
    private TextView textoFecha, textoHora;
    private Button botonFecha, botonHora;
    private Adaptador adaptador;
    private MascotaDBHelper bdMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_mascotas);

        mostarAnimacion();
        botonFlotante();

        textoFecha = findViewById(R.id.textoFecha);
        botonFecha = findViewById(R.id.botonFecha);
        fecha(textoFecha, botonFecha);

        textoHora = findViewById(R.id.textoHora);
        botonHora = findViewById(R.id.botonHora);
        hora(textoHora, botonHora);

        // Inicializar la base de datos
        bdMascota = new MascotaDBHelper(this);

        // Obtener nombre del usuario
        Intent obtenerUsuario = getIntent();
        String usuario = obtenerUsuario.getStringExtra("usuario");

        // Cambiar nombre del usuario
        TextView nomUsuario = findViewById(R.id.nomUsuario);
        nomUsuario.setText(usuario);

        // Añadir menu personalizado
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inicializar la lista de mascotas
        listaMascotas = new ArrayList<>();
        lista = findViewById(R.id.lista);

        // Cargar mascotas de la base de datos
        listaMascotas = bdMascota.obtenerMascotas();

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
                        if (mascota.getImagenByteArray() != null) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(mascota.getImagenByteArray(), 0, mascota.getImagenByteArray().length);
                            imagen.setImageBitmap(bitmap);
                        } else {
                            imagen.setImageResource(mascota.getImagen()); // Mostrar imagen de recurso
                        }
                    }
                }
            }
        });

        // Configurar el evento de click en los ítems del ListView
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
                            String nombreMascota = listaMascotas.get(posicion).getNombre();
                            bdMascota.eliminarMascota(nombreMascota);  // Pasar el ID real a eliminar
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
                bdMascota.actualizarMascota(mascotaModificada);

                // Notifica al adaptador que los datos han cambiado
                ((Adaptador) lista.getAdapter()).notifyDataSetChanged();
                mostrarToast("Mascota modificada con éxito");
            }
        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            ArrayList<Mascota> listaMascotasRecibida = (ArrayList<Mascota>) data.getSerializableExtra("mascotas");

            if (listaMascotasRecibida != null && !listaMascotasRecibida.isEmpty()) {
                // Obtener la última mascota de la lista
                Mascota ultimaMascota = listaMascotasRecibida.get(listaMascotasRecibida.size() - 1);

                // Asegúrate de no añadir la misma mascota si ya está en la lista
                if (!listaMascotas.contains(ultimaMascota)) {
                    bdMascota.insertarMascota(ultimaMascota);
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
        } else if (item.getItemId() == R.id.idioma_es) {
            // Cambiar idioma a español
            cambiarIdioma("es");
            mostrarToast("Idioma cambiado a Español");
            return true;
        } else if (item.getItemId() == R.id.idioma_en) {
            // Cambiar idioma a inglés
            cambiarIdioma("en");
            mostrarToast("Idioma cambiado a Inglés");
            return true;
        } else if (item.getItemId() == R.id.idioma_fr) {
            // Cambiar idioma a francés
            cambiarIdioma("fr");
            mostrarToast("Idioma cambiado a Francés");
            return true;
        }

        // Si no es ninguna de las anteriores opciones, delega el resto a la implementación por defecto
        return super.onOptionsItemSelected(item);
    }

    private void cambiarIdioma(String idioma) {
        Locale locale;
        switch (idioma) {
            case "es":
                locale = new Locale("es"); // Español
                break;
            case "en":
                locale = new Locale("en"); // Inglés
                break;
            case "fr":
                locale = new Locale("fr"); // Francés
                break;
            default:
                locale = Locale.getDefault(); // Por defecto
                break;
        }

        // Cambiar la configuración de la aplicación a la nueva localidad
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);  // Usa setLocale en lugar de config.locale = locale
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        recreate(); // Esto recrea la actividad actual con la configuración de idioma cambiada
    }

    // Método para mostrar animación
    public void mostarAnimacion() {
        FrameLayout listadoMascotas = findViewById(R.id.listadoMascotas);
        Animation animacion = AnimationUtils.loadAnimation(this, R.anim.aparecer);
        listadoMascotas.startAnimation(animacion);
    }

    // Método para botón flotante
    public void botonFlotante() {
        FloatingActionButton fab = findViewById(R.id.botonFlotante);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListadoMascotasAdmin.this, AñadirMascota.class);
                startActivityForResult(intent, 2);
            }
        });
    }

    // Método para la funcionalidad del DatePicker
    public void fecha(TextView textoFecha, Button botonFecha) {
        final Calendar calendario = Calendar.getInstance();
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        // Crear el DatePickerDialog con el estilo personalizado
        final DatePickerDialog fecha = new DatePickerDialog(ListadoMascotasAdmin.this, R.style.CustomDatePickerDialog, (view, year, month, dayOfMonth) -> {
            // Formatear la fecha seleccionada
            String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
            textoFecha.setText(fechaSeleccionada); // Mostrar la fecha en el TextView
        }, ano, mes, dia); // Fecha por defecto (actual)

        botonFecha.setOnClickListener(v -> {
            fecha.show();
        });
    }

    // Método funcionalidad TimePicker
    public void hora(TextView textoHora, Button botonHora) {
        final Calendar calendar = Calendar.getInstance();
        int horas = calendar.get(Calendar.HOUR_OF_DAY);
        int minutos = calendar.get(Calendar.MINUTE);

        // Crear el TimePickerDialog con el estilo personalizado
        TimePickerDialog hora = new TimePickerDialog(this, R.style.CustomTimePicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Formatear la hora y los minutos para que siempre tengan dos dígitos
                String horaSeleccionada = String.format("%02d:%02d", hourOfDay, minute);
                textoHora.setText(horaSeleccionada);
            }
        }, horas, minutos, false);

        botonHora.setOnClickListener(v -> {
            hora.show();
        });
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
