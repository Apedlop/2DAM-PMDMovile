package com.example.prueba_ex1ev2;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragmento2 extends Fragment {

    private TextView textRecibido;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.activity_fragmento2, container, false);

        // Obtener el TextView donde se mostrará el texto
        textRecibido = view.findViewById(R.id.texto2);

        // Obtener el argumento enviado desde Fragmento1
        Bundle args = getArguments();
        if (args != null) {
            String texto = args.getString("texto");
            textRecibido.setText(texto); // Mostrar el texto recibido
        }

        return view;
    }
}

