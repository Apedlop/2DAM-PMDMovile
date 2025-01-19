package com.example.gestionmascota;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;

public class Fragmento2 extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_unpanel);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(Fragmento3.ARG_ID_ENTRADA_SELECCIONADA, getIntent().getStringExtra(Fragmento3.ARG_ID_ENTRADA_SELECCIONADA));
            Fragmento3 fragment = new Fragmento3();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.frame_contenedor, fragment).commit();
        }
    }
}