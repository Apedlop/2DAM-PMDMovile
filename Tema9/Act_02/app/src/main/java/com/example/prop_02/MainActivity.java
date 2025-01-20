package com.example.prop_02;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private boolean mostrandoFragmento1 = true; // Indica cuál fragmento se está mostrando

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mostrar el primer fragmento al iniciar
        Fragment fragmentoInicial = new Fragmento1();
        cargarFragmento(fragmentoInicial);

        // Configurar el botón flotante
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Fragment siguienteFragmento = mostrandoFragmento1 ? new Fragmento2() : new Fragmento1();
            cargarFragmento(siguienteFragmento);
            mostrandoFragmento1 = !mostrandoFragmento1; // Cambiar el estado
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
