package com.example.gestionmascotas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class Adaptador extends BaseAdapter {

    private ArrayList<Mascota> listaMascotas;
    private int layoutId;
    private Context contexto;
    private List<Contenido.Lista_entrada> items;

    public Adaptador(Context context, int resource, List<Contenido.Lista_entrada> items) {
        this.contexto = context;
        this.layoutId = resource;
        this.items = items;
    }
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
//        onEntrada(items.get(position), view);  // Usar el tipo específico
        return view;
    }

    public abstract void onEntrada(Object entrada, View view);
}
