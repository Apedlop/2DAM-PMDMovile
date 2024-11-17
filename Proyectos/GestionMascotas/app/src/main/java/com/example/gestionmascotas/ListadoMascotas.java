package com.example.gestionmascotas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListadoMascotas extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_mascotas);

        // Obtener nombre del usuario
        Intent obtenerUsuario = getIntent();
        String usuario = obtenerUsuario.getStringExtra("usuario");

        // Inflar la cabecera y añadirla en el Activity
        ArrayList<Mascota> listaMascotas = new ArrayList<>();
        lista = findViewById(R.id.lista);
        View cabecera = getLayoutInflater().inflate(R.layout.cabecera, null);
        lista.addHeaderView(cabecera);

        // Cambiar nombre del usuario en la cabecera
        TextView nomUsuario = cabecera.findViewById(R.id.nomUsuario);
        if (nomUsuario != null) {
            nomUsuario.setText(usuario);  // Establecer el nombre del usuario en el TextView
        } else {
            System.out.println("No se encontró el TextView 'nomUsuario'");
        }

        // Crear los datos para el adaptador
        listaMascotas.add(new Mascota("Yoyo", "Bodeguero", R.drawable.bodeguero, 3, 25.0f, true, true, false));
        listaMascotas.add(new Mascota("Max", "Labrador", R.drawable.bodeguero, 5, 30.5f, true, false, true));

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
            Mascota mascotaSeleccionada = (Mascota) parent.getItemAtPosition(position);

            // Crear un Intent para abrir la Activity InfoDetallada
            Intent intent = new Intent(ListadoMascotas.this, InfoDetallada.class);

            // Pasar los datos de la mascota a la nueva Activity
            intent.putExtra("nombre", mascotaSeleccionada.getNombre());
            intent.putExtra("raza", mascotaSeleccionada.getRaza());
            intent.putExtra("imagen", mascotaSeleccionada.getImagen());
            intent.putExtra("edad", mascotaSeleccionada.getEdad());
            intent.putExtra("peso", mascotaSeleccionada.getPeso());
            intent.putExtra("vacunada", mascotaSeleccionada.isVacunada());
            intent.putExtra("desparacitada", mascotaSeleccionada.isDesparacitada());
            intent.putExtra("esterilizada", mascotaSeleccionada.isEsterilizada());
            intent.putExtra("usuario", usuario);  // Pasar el usuario a la actividad de detalles

            // Iniciar la Activity InfoDetallada
            startActivity(intent);
        });
    }
}
