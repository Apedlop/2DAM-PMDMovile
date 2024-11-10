package com.example.aplicaciongestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Adaptador extends ArrayAdapter<Elementos> {

    private List<Elementos> elementos;

    public Adaptador(Context contexto, List<Elementos> elementos) {
        super(contexto, R.layout.elementos, elementos);
        this.elementos = elementos;
    }

    @Override
    public View getView(int posicion, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.elementos, parent, false);
        }

        Elementos elemento = elementos.get(posicion);

        TextView titulo = convertView.findViewById(R.id.titulo);
        TextView contenido = convertView.findViewById(R.id.contenido);

        titulo.setText(elemento.getTitulo());
        contenido.setText(elemento.getContenido());

        return convertView;
    }

}
