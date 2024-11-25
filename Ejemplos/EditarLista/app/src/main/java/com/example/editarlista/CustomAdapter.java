package com.example.editarlista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<ListItem> {

    private Context context;
    private ArrayList<ListItem> items;

    // Constructor corregido
    public CustomAdapter(Context context, ArrayList<ListItem> items) {
        super(context, 0, items);  // Aquí solo pasamos el contexto y la lista
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // Inflamos el layout de cada elemento de la lista
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        ListItem currentItem = items.get(position);
        RadioGroup radioGroup = convertView.findViewById(R.id.radioGroupItem);
        RadioButton radioButton1 = convertView.findViewById(R.id.radioButton1Item);
        RadioButton radioButton2 = convertView.findViewById(R.id.radioButton2Item);
        TextView textView = convertView.findViewById(R.id.textViewItem);

        textView.setText(currentItem.getText());

        // Seleccionar el RadioButton adecuado
        radioGroup.clearCheck();
        if (currentItem.getSelecedOption() == 1) {
            radioButton1.setChecked(true);
        } else if (currentItem.getSelecedOption() == 2) {
            radioButton2.setChecked(true);
        }

        return convertView;
    }
}
