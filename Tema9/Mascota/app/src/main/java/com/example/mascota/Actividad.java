package com.example.mascota;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.FragmentActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class Actividad extends FragmentActivity implements Fragmento1.Callbacks {

    private boolean dosFragmentos;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad);

        if (findViewById(R.id.frame_contenedor) != null) {
            dosFragmentos = true;
        }

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        mostarAnimacion();
        botonFlotante();
        funcBotonFecha();
        funcBotonHora();

        // Obtener nombre del usuario
        Intent obtenerUsuario = getIntent();
        String usuario = obtenerUsuario.getStringExtra("usuario");

    }

    @Override
    public void onEntradaSeleccionada(String id) {
        if (dosFragmentos) {
            Bundle arguments = new Bundle();
            arguments.putString(Fragmento3.ARG_ID_ENTRADA_SELECCIONADA, id);
            Fragmento3 fragment = new Fragmento3();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_contenedor, fragment).commit();
        } else {
            Intent detalleIntent = new Intent(this, Fragmento2.class);
            detalleIntent.putExtra(Fragmento3.ARG_ID_ENTRADA_SELECCIONADA, id);
            startActivity(detalleIntent);
        }
    }

    /*
    * Métodos del Menú
    */
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
            Collections.sort(Contenido.ENT_LISTA, new Comparator<Contenido.Lista_entrada>() {
                @Override
                public int compare(Contenido.Lista_entrada o1, Contenido.Lista_entrada o2) {
                    return o1.getNombre().compareToIgnoreCase(o2.getNombre());
                }
            });
            // Notificar al adaptador
//            ((Adaptador) Contenido.ENT_LISTA.getAdapter()).notifyDataSetChanged();
            mostrarToast("Lista ordenada por nombre");
            return true;
        } else if (item.getItemId() == R.id.ordenarRaza) {
            // Ordenar por raza
            Collections.sort(Contenido.ENT_LISTA, new Comparator<Contenido.Lista_entrada>() {
                @Override
                public int compare(Contenido.Lista_entrada o1, Contenido.Lista_entrada o2) {
                    return o1.getRaza().compareToIgnoreCase(o2.getRaza());
                }
            });
            // Notificar al adaptador
//            ((Adaptador) lista.getAdapter()).notifyDataSetChanged();
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

    /*
    * Menú contextual
    */

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_contextual, menu);
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        int posicion = info.position;
//
//        if (item.getItemId() == R.id.opEditar) {
//            Contenido.Lista_entrada editarItem = Contenido.ENT_LISTA.get(posicion);
//            Intent intent = new Intent(this, EditarMascota.class);
//            intent.putExtra("mascota", editarItem);
//            intent.putExtra("position", posicion);  // Pasamos la posición para actualizarla después
//            startActivityForResult(intent, 1);
//            return true;
//        } else if (item.getItemId() == R.id.opEliminar) {
//            // Crear el cuadro de diálogo de confirmación
//            new AlertDialog.Builder(this) // O usa 'getContext()' si estás en un fragmento
//                    .setTitle("Confirmar eliminación")
//                    .setMessage("¿Estás seguro de que deseas eliminar esta mascota?")
//                    .setPositiveButton("Eliminar", (dialog, which) -> {
//                        // Lógica para eliminar la mascota
//                        if (posicion >= 0 && posicion < Contenido.ENT_LISTA.size()) {
//                            Contenido.ENT_LISTA.remove(posicion);
//
//                            // Notificar al adaptador que los datos han cambiado
//                            ((Adaptador) lista.getAdapter()).notifyDataSetChanged();
//
//                            // Mostrar mensaje de éxito
//                            mostrarToast("Mascota eliminada");
//                        } else {
//                            // Mostrar mensaje de error si la posición no es válida
//                            mostrarToast("Error al eliminar la mascota");
//                        }
//                    })
//                    .setNegativeButton("Cancelar", (dialog, which) -> {
//                        // Cerrar el diálogo sin hacer nada
//                        dialog.dismiss();
//                    })
//                    .show();
//        }
//
//        return super.onContextItemSelected(item);
//    }

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

    // Método para mostrar toast
    public void mostrarToast(String mensaje) {
        // Toast personalizado
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.layout_toast));
        TextView textoToast = layout.findViewById(R.id.textoToast);
        textoToast.setText(mensaje);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    // Método para mostrar animación
    public void mostarAnimacion() {
        FrameLayout listadoMascotas = findViewById(R.id.listadoMascotas);
        Animation animacion = AnimationUtils.loadAnimation(this, R.anim.aparecer);
        listadoMascotas.startAnimation(animacion);
    }

    // Método para el botón flotante
    public void botonFlotante() {
        FloatingActionButton fab = findViewById(R.id.botonFlotante);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Actividad.this, AñadirMascota.class);
                startActivity(intent);
            }
        });
    }

    // Método para la funcionalidad del boton de la fecha
    public void funcBotonFecha() {
        TextView textoFecha = findViewById(R.id.textoFecha);
        Button botonFecha = findViewById(R.id.botonFecha);
        // Calendario
        final Calendar calendario = Calendar.getInstance();
        int ano = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        // Crear el DatePickerDialog con el estilo personalizado
        final DatePickerDialog fecha = new DatePickerDialog(Actividad.this, R.style.CustomDatePickerDialog, (view, year, month, dayOfMonth) -> {
            // Formatear la fecha seleccionada
            String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
            textoFecha.setText(fechaSeleccionada); // Mostrar la fecha en el TextView
        }, ano, mes, dia); // Fecha por defecto (actual)

        botonFecha.setOnClickListener(v -> {
            fecha.show();
        });
    }

    public void funcBotonHora() {
        TextView textoHora = findViewById(R.id.textoHora);
        Button botonHora = findViewById(R.id.botonHora);

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
}