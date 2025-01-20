package com.example.gestionmascotas;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;


public class Fragmento1 extends ListFragment {

    private Callbacks mCallbacks = Callbacks.CallbacksVacios;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new Adaptador(getActivity(), R.layout.elementos, Contenido.ENT_LISTA) {
            @Override
            public void onEntrada(Object entrada, View view) {
                TextView titulo = view.findViewById(R.id.textoTitulo);
                titulo.setText(((Contenido.Lista_entrada) entrada).nombre);

                TextView edad = view.findViewById(R.id.textoEdad);
                edad.setText("Edad: " + String.valueOf(((Contenido.Lista_entrada) entrada).edad) + " años");

                TextView raza = view.findViewById(R.id.textoRaza);
                raza.setText("Raza: " + ((Contenido.Lista_entrada) entrada).raza);

                ImageView imagen_entrada = view.findViewById(R.id.imagenLista);
                imagen_entrada.setImageResource(((Contenido.Lista_entrada) entrada).idImagen);
            }
        });
    }

    public interface Callbacks {
        Callbacks CallbacksVacios = new Callbacks() {
            @Override
            public void onEntradaSeleccionada(String id) {
                // Implementación vacía
            }
        };

        void onEntradaSeleccionada(String id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = Callbacks.CallbacksVacios;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mCallbacks.onEntradaSeleccionada(Contenido.ENT_LISTA.get(position).id);
    }
}
