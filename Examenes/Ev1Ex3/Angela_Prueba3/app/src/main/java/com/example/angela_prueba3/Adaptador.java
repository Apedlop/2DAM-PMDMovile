package com.example.angela_prueba3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public abstract class Adaptador extends BaseAdapter {

    private ArrayList<Encapsulador> entrada;
    private int R_layout_idView;
    private Context context;

    public Adaptador(ArrayList<Encapsulador> entrada, int r_layout_idView, Context context) {
        this.entrada = entrada;
        R_layout_idView = r_layout_idView;
        this.context = context;
    }

    @Override
    public int getCount() {
        return entrada.size();
    }

    @Override
    public Object getItem(int position) {
        return entrada.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R_layout_idView, null);
        }
        onEntrada(entrada.get(position), view);  // Usar el tipo específico
        return view;
    }

    public abstract void onEntrada(Object entrada, View view);
}
