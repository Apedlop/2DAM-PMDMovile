package com.example.prueba_ex1ev2;

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
        setContentView(R.layout.activity_main);

        // Mostrar primer fragmento al iniciar la aplicación
        Fragment fragmento1 = new Fragmento2();
        cargarFragmento(fragmento1);

        // Configurar botón
        Button boton = findViewById(R.id.enviar);
        boton.setOnClickListener(v -> {
            Fragment fragmento;
            String textoEnviado = ((EditText) findViewById(R.id.textoEnviado)).getText().toString(); // Obtener texto

            fragmento = new Fragmento2();
            Bundle bundle = new Bundle();
            bundle.putString("texto", textoEnviado); // Pasar el texto como argumento
            fragmento.setArguments(bundle);

            cargarFragmento(fragmento);
        });

    }

    // Método para cargar fragmentos en el contenedor
    private void cargarFragmento(Fragment fragmento) {
        FragmentManager FM = getSupportFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.replace(R.id.contenedor, fragmento); // Reemplaza el fragmento actual
        FT.commit();
    }

}