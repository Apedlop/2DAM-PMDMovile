package com.example.aplicaciongestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public abstract class Adaptador extends BaseAdapter {

    private ArrayList<Mascota> listaMascotas;
    private int layoutId;
    private Context contexto;

    public Adaptador(ArrayList<Mascota> listaMascotas, int layoutId, Context contexto) {
        this.listaMascotas = listaMascotas;
        this.layoutId = layoutId;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return listaMascotas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaMascotas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layoutId, null);
        }

        onEntrada(listaMascotas.get(position), view);
        return view;
    }

    public abstract void onEntrada(Object entrada, View view);
}
