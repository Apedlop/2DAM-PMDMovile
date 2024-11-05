package com.example.arrayadapter;

import android.content.Context;
import android.widget.ArrayAdapter;

public class Adaptador extends ArrayAdapter<Datos> {

    private Datos[] datos;

    public Adaptador(Context context, Datos[] datos) {
        super(context, R.layout.elemento, datos);
        this.datos = datos;
    }

}
