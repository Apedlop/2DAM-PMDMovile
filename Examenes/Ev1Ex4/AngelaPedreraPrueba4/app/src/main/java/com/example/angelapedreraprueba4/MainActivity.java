package com.example.angelapedreraprueba4;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Spinner lista;
    private TextView titulo, texto;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Encapsulador> datos = new ArrayList<>();

        lista = findViewById(R.id.lista);
        webView = findViewById(R.id.webView);

        // Configurar WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Habilitar JavaScript
        webView.setWebViewClient(new WebViewClient()); // Configurar para que las URLs se abran en el WebView y no en el navegador

        // Añadir datos al ArrayList
        datos.add(new Encapsulador(R.drawable.caravaggio, "CARAVAGGIO", "Pintor italiano entre los años de 1593 y 1610. Es considerado como el primer gran exponente de la pintura del Barroco.", "http://es.wikipedia.org/wiki/Caravaggio"));
        datos.add(new Encapsulador(R.drawable.rafael, "RAFAEL SANZIO", "Pintor y arquitecto italiano del Renacimiento, realizó importantes aportes en la arquitectura y, como inspector de antigüedades.", "http://es.wikipedia.org/wiki/Rafael_Sanzio"));
        datos.add(new Encapsulador(R.drawable.velazquez, "VELAZQUEZ" , "Pintor Barroco nacido en Sevilla en 1599, es considerado uno de los máximos exponentes de la pintura española y maestro de la pintura universal.", "http://es.wikipedia.org/wiki/Diego_Vel%C3%A1zquez"));
        datos.add(new Encapsulador(R.drawable.miguelangel, "MIGUEL ANGEL", "Arquitecto, escultor y pintor italiano renacentista, considerado uno de los más grandes artistas de la historia.", "http://es.wikipedia.org/wiki/Miguel_%C3%81ngel"));
        datos.add(new Encapsulador(R.drawable.rembrant, "REMBRANDT", "Pintor y grabador holandés. La historia del arte le considera uno de los mayores maestros barrocos de la pintura y el grabado.", "http://es.wikipedia.org/wiki/Rembrandt"));
        datos.add(new Encapsulador(R.drawable.boticelli, "BOTICELLI", "Apodado Sandro Botticelli, fue un pintor cuatrocentista italiano.su obra se ha considerado representativa de la pintura del primer Renacimiento.", "http://es.wikipedia.org/wiki/Sandro_Botticelli"));
        datos.add(new Encapsulador(R.drawable.leonardo, "LEONARDO DA VINCI", "Notable polímata del Renacimiento italiano (a la vez anatomista, arquitecto, artista, botánico, científico, escritor, escultor, filósofo,ingeniero...)", "http://es.wikipedia.org/wiki/Leonardo_da_Vinci"));
        datos.add(new Encapsulador(R.drawable.renoir, "RENOIR", "Pintor francés impresionista, interesado por la pintura de cuerpos femeninos en paisajes, inspirados a menudo en pinturas clásicas renacentistas y barrocas.", "http://es.wikipedia.org/wiki/Pierre-Auguste_Renoir"));

        lista.setAdapter(new Adaptador(datos, R.layout.entrada, this) {
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null && view != null) {
                    TextView titulo = view.findViewById(R.id.titulo);
                    TextView texto = view.findViewById(R.id.texto);
                    ImageView imagen = view.findViewById(R.id.imagen);

                    if (texto != null && titulo != null && imagen != null) {
                        titulo.setText(((Encapsulador) entrada).getTitulo());
                        texto.setText(((Encapsulador) entrada).getContenido());
                        imagen.setImageResource(((Encapsulador) entrada).getImagen());
                    }
                }
            }
        });

        lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el objeto seleccionado
                Encapsulador elegido = (Encapsulador) parent.getItemAtPosition(position);
                String url = elegido.getUrl().trim();
                webView.loadUrl(url);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Si no hay selección, no hacer nada
            }
        });

    }
}