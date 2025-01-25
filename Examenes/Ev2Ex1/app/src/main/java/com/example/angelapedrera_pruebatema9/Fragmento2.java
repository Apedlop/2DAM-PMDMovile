package com.example.angelapedrera_pruebatema9;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragmento2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout para el fragmento
        View view = inflater.inflate(R.layout.activity_fragmento2, container, false);

        TextView textoRecibido = view.findViewById(R.id.textoRecibido);

        // Obtener el texto enviado desde Fragmento1
        Bundle recibido = getArguments();
        if (recibido != null) {
            String texto = recibido.getString("texto");
            textoRecibido.setText(texto); // Mostrar el texto recibido
        }

        return view;
    }
}