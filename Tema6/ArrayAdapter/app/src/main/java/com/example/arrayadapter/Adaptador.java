package com.example.arrayadapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class Adaptador extends ArrayAdapter<Datos> {

    private Datos[] datos;

    public Adaptador(Context context, Datos[] datos) {
        super(context, R.layout.elemento, datos);
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mostrado = LayoutInflater.from(getContext());
        View elemento = mostrado.inflate(R.layout.elemento, parent, false);

        TextView texto1 = elemento.findViewById(R.id.miTexto1);
        texto1.setText(datos[position].getTexto1());

        TextView texto2 = elemento.findViewById(R.id.miTexto2);
        texto2.setText(datos[position].getTexto2());

        ImageView imagen = elemento.findViewById(R.id.miImagen);
        imagen.setImageResource(datos[position].getImagenResId());  // Cargar la imagen desde drawable

        TextView enlace = elemento.findViewById(R.id.miEnlace);
        enlace.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(datos[position].getUrlEnlace()));
            getContext().startActivity(intent);
        });

        CheckBox checkBox = elemento.findViewById(R.id.miCheckBox);
        RadioButton radioButton = elemento.findViewById(R.id.miRadioButton);

        return elemento;
    }
}
