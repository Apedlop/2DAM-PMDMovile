package com.example.angelapedrera_pruebatema9;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Fragmento1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmento1);

        // Mostrar primer fragmento al iniciar la aplicación
        Fragment fragmento1 = new Fragmento2();
        cargarFragmento(fragmento1);

        // Configurar botón
        Button boton = findViewById(R.id.enviar);
        boton.setOnClickListener(v -> {
            Fragment fragmento2 = new Fragmento2();
            String textoEnviado = ((EditText) findViewById(R.id.textoEnviado)).getText().toString(); // Obtener texto del EditText

            Bundle enviar = new Bundle();
            enviar.putString("texto", textoEnviado); // Pasar el texto al fragmento2
            fragmento2.setArguments(enviar);

            cargarFragmento(fragmento2);
        });

    }

    // Método para cargar fragmentos en el contenedor
    private void cargarFragmento(Fragment fragmento) {
        FragmentManager FM = getSupportFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.replace(R.id.contenedor, fragmento); // Reemplazar el fragmento actual
        FT.commit();
    }

}