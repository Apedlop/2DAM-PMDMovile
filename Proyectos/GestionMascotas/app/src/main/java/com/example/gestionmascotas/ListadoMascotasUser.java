package com.example.gestionmascotas;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class ListadoMascotasUser extends AppCompatActivity {

    private ListView lista;
    private Mascota mascotaSeleccionada;
    private ArrayList<Mascota> listaMascotas;
    private TextView textoFecha, textoHora;
    private Button botonFecha, botonHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_mascotas);

        mostarAnimacion();

        // Boton flotante
        FloatingActionButton fab = findViewById(R.id.botonFlotante);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarToast("Solo se puede usar en el modo Admin.");
            }
        });

        textoFecha = findViewById(R.id.textoFecha);
        botonFecha = findViewById(R.id.botonFecha);
        textoHora = findViewById(R.id.textoHora);
        botonHora = findViewById(R.id.botonHora);

        // Calendario
        final Calendar calendario = Calendar.getInstance();
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        // Crear el DatePickerDialog con el estilo personalizado
        final DatePickerDialog fecha = new DatePickerDialog(ListadoMascotasUser.this, R.style.CustomDatePickerDialog, (view, year, month, dayOfMonth) -> {
            // Formatear la fecha seleccionada
            String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
            textoFecha.setText(fechaSeleccionada); // Mostrar la fecha en el TextView
        }, ano, mes, dia); // Fecha por defecto (actual)

        botonFecha.setOnClickListener(v -> {
            fecha.show();
        });

        // Hora
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
