package com.example.aplicaciongestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lista;
    private TextView texto;
    private RadioButton radioButton_pulsado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Mascota> listaMascotas = new ArrayList<>();
        lista = findViewById(R.id.lista);

        // Crear los datos para el adaptador
        listaMascotas.add(new Mascota("Yoyo", "Bodeguero", R.drawable.bodeguero, 3, 25.0f, true, true, false));
        listaMascotas.add(new Mascota("Max", "Labrador", R.drawable.bodeguero, 5, 30.5f, true, false, true));

        // Configurar el adaptador
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
            // Obtener el objeto Mascota seleccionado
            Mascota mascotaSeleccionada = (Mascota) parent.getItemAtPosition(position);

            // Crear un Intent para abrir la Activity InfoDetallada
            Intent intent = new Intent(MainActivity.this, InfoDetallada.class);

            // Pasar los datos de la mascota a la nueva Activity
            intent.putExtra("nombre", mascotaSeleccionada.getNombre());
            intent.putExtra("raza", mascotaSeleccionada.getRaza());
            intent.putExtra("imagen", mascotaSeleccionada.getImagen());
            intent.putExtra("edad", mascotaSeleccionada.getEdad());
            intent.putExtra("peso", mascotaSeleccionada.getPeso());
            intent.putExtra("vacunada", mascotaSeleccionada.isVacunada());
            intent.putExtra("desparacitada", mascotaSeleccionada.isDesparacitada());
            intent.putExtra("esterilizada", mascotaSeleccionada.isEsterilizada());

            // Iniciar la Activity InfoDetallada
            startActivity(intent);
        });

    }
}
