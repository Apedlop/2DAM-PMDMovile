package com.example.act_05_prop_04;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragmento3 extends Fragment {

    public static final String ARG_ID_ENTRADA_SELECCIONADA = "item_id";

    private Contenido.Lista_entrada mItem;

    public Fragmento3() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ID_ENTRADA_SELECCIONADA)) {
            mItem = Contenido.ENT_LISTA_HASHMAP.get(getArguments().getString(ARG_ID_ENTRADA_SELECCIONADA));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_detalle, container, false);
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.textoTitulo)).setText(mItem.textoEncima);
            ((TextView) rootView.findViewById(R.id.textoContenido)).setText(mItem.textoDebajo);
            ((ImageView) rootView.findViewById(R.id.imagen)).setImageResource(mItem.idImagen);
        }
        return rootView;
    }
}
