package com.example.prueba_ex3_ev1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class OperacionAdapter extends ArrayAdapter<Operacion> {

    public OperacionAdapter(Context context, List<Operacion> operaciones) {
        super(context, 0, operaciones);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        Operacion operacion = getItem(position);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(operacion.getOperacion());

        return convertView;
    }
}
