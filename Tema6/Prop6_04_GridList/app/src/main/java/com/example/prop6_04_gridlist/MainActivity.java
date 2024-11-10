package com.example.prop6_04_gridlist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView lista;
    private TextView texto;
    private RadioButton radioButton_pulsado;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Encapsulador> datos = new ArrayList<Encapsulador>();

        lista = findViewById(R.id.lista);
        texto = findViewById(R.id.textoInfo);

        // Añadir datos al ArrayList
        // Asegúrate de que las imágenes existen en tu carpeta res/drawable
        datos.add(new Encapsulador(R.drawable.cerdo, "Título 1", "Texto 1", false));
        datos.add(new Encapsulador(R.drawable.perro, "Título 2", "Texto 2", false));
        datos.add(new Encapsulador(R.drawable.perro, "Título 3", "Texto 3", false));
        datos.add(new Encapsulador(R.drawable.oso, "Título 4", "Texto 4", false));

        // Configurar el adaptador
        lista.setAdapter(new Adaptador(datos, R.layout.entrada, this) {
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null && view != null) {
                    TextView texto_superior_entrada = view.findViewById(R.id.texto_titulo);
                    TextView texto_inferior_entrada = view.findViewById(R.id.texto_datos);
                    ImageView imagen_entrada = view.findViewById(R.id.imagen);
                    RadioButton miRadio = view.findViewById(R.id.boton);

                    if (texto_superior_entrada != null && texto_inferior_entrada != null && imagen_entrada != null && miRadio != null) {
                        // Setear datos en las vistas
                        texto_superior_entrada.setText(((Encapsulador) entrada).getTitulo());
                        texto_inferior_entrada.setText(((Encapsulador) entrada).getContenido());
                        imagen_entrada.setImageResource(((Encapsulador) entrada).getImagen());

                        // Escuchador del RadioButton
                        miRadio.setChecked(((Encapsulador) entrada).isDato1());
                        miRadio.setOnClickListener(v -> {
                            if (radioButton_pulsado != null) radioButton_pulsado.setChecked(false);
                            radioButton_pulsado = (RadioButton) v;
                            texto.setText("MARCADA UNA OPCIÓN");
                        });
                    }
                }
            }
        });

        // Configurar el evento de clic en los ítems del GridView
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el objeto seleccionado
                Encapsulador elegido = (Encapsulador) parent.getItemAtPosition(position);
                CharSequence textoElegido = "Seleccionado: " + elegido.getContenido();
                // Mostrar el contenido en el TextView
                texto.setText(textoElegido);
            }
        });

    }

    // Clase POJO de Encapsulador
    public class Encapsulador {
        private int imagen;
        private String titulo, contenido;
        private boolean dato1;

        public Encapsulador(int imagen, String titulo, String contenido, boolean dato1) {
            this.imagen = imagen;
            this.titulo = titulo;
            this.contenido = contenido;
            this.dato1 = dato1;
        }

        public int getImagen() {
            return imagen;
        }

        public String getTitulo() {
            return titulo;
        }

        public String getContenido() {
            return contenido;
        }

        public boolean isDato1() {
            return dato1;
        }
    }
}
