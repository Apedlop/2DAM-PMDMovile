package com.example.tomarfoto;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public abstract class FotoAdapter extends BaseAdapter {

    private ArrayList<Uri> listaFotos;
    private int layoutId;
    private Context contexto;

    public FotoAdapter(ArrayList<Uri> listaFotos, int layoutId, Context contexto) {
        this.listaFotos = listaFotos;
        this.layoutId = layoutId;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return listaFotos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaFotos.get(position);
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

        onEntrada(listaFotos.get(position), view);
        return view;
    }

    public abstract void onEntrada(Uri fotoUri, View view);
}
