package com.example.angelapedreraprueba4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public abstract class Adaptador extends BaseAdapter {

    private ArrayList<Encapsulador> entradas;
    private int R_layout_idView;
    private Context contexto;

    public Adaptador(ArrayList<Encapsulador> entradas, int r_layout_idView, Context contexto) {
        this.entradas = entradas;
        R_layout_idView = r_layout_idView;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return entradas.size();
    }

    @Override
    public Object getItem(int position) {
        return entradas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R_layout_idView, null);
        }
        onEntrada(entradas.get(position), view);  // Usar el tipo específico
        return view;
    }

    public abstract void onEntrada(Object entrada, View view);


}
