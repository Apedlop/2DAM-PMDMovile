package com.example.act_05_prop_04;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public abstract class Adaptador extends BaseAdapter {

    private Context context;
    private int resourceLayout;
    private List<Contenido.Lista_entrada> items;

    public Adaptador(Context context, int resource, List<Contenido.Lista_entrada> items) {
        this.context = context;
        this.resourceLayout = resource;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(resourceLayout, null);
        }
        onEntrada(items.get(position), view);  // Usar el tipo específico
        return view;
    }

    public abstract void onEntrada(Object entrada, View view);
}
