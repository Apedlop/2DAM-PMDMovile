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
        datos.add(new Encapsulador(R.drawable.ima1, "DONUTS", "El 15 de septiembre de 2009, fue lanzado el SDK de Android 1.6 Donut, basado en el núcleo Linux 2.6.29. En la actualización se incluyen numerosas características nuevas.", true));
        datos.add(new Encapsulador(R.drawable.ima2, "FROYO", "El 20 de mayo de 2010, El SDK de Android 2.2 Froyo (Yogur helado) fue lanzado, basado en el núcleo Linux 2.6.32.", false));
        datos.add(new Encapsulador(R.drawable.ima3, "GINGERBREAD", "El 6 de diciembre de 2010, el SDK de Android 2.3 Gingerbread (Pan de Jengibre) fue lanzado, basado en el núcleo Linux 2.6.35.", false));
        datos.add(new Encapsulador(R.drawable.ima4, "HONEYCOMB", "El 22 de febrero de 2011, sale el SDK de Android 3.0 Honeycomb (Panal de Miel). Fue la primera actualización exclusiva para TV y tableta, lo que quiere decir que sólo es apta para TV y tabletas y no para teléfonos Android.", false));
        datos.add(new Encapsulador(R.drawable.ima5, "ICE CREAM", "El SDK para Android 4.0.0 Ice Cream Sandwich (Sándwich de Helado), basado en el núcleo de Linux 3.0.1, fue lanzado públicamente el 12 de octubre de 2011.", false));
        datos.add(new Encapsulador(R.drawable.ima6, "JELLY BEAN", "Google anunció Android 4.1 Jelly Bean (Gomita Confitada o Gominola) en la conferencia del 30 de junio de 2012. Basado en el núcleo de linux 3.0.31, Bean fue una actualización incremental con el enfoque primario de mejorar la funcionalidad y el rendimiento de la interfaz de usuario.", false));
        datos.add(new Encapsulador(R.drawable.ima7, "KITKAT", "Su nombre se debe a la chocolatina KitKat, de la empresa internacional Nestlé. Posibilidad de impresión mediante WIFI. WebViews basadas en el motor de Chromium.", false));
        datos.add(new Encapsulador(R.drawable.ima8, "LOLLIPOP", "Incluye Material Design, un diseño intrépido, colorido, y sensible interfaz de usuario para las experiencias coherentes e intuitivos en todos los dispositivos. Movimiento de respuesta natural, iluminación y sombras realistas y familiares elementos visuales hacen que sea más fácil de navegar su dispositivo.", false));

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
