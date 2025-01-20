package com.example.mascota;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Fragmento3 extends Fragment {

    public static final String ARG_ID_ENTRADA_SELECCIONADA = "item_id";

    private Contenido.Lista_entrada mItem;

    public Fragmento3() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ID_ENTRADA_SELECCIONADA)) {
            mItem = Contenido.ENT_LISTA_HASHMAP.get(getArguments().getString(ARG_ID_ENTRADA_SELECCIONADA));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_detalle, container, false);

        if (mItem != null) {
            // Imagen de la mascota
            ImageView imagen = rootView.findViewById(R.id.imagen);
            imagen.setImageResource(mItem.idImagen);

            // Nombre de la mascota
            TextView textoNombre = rootView.findViewById(R.id.texto_nombre);
            textoNombre.setText(mItem.nombre);

            // Edad de la mascota
            TextView edadValor = rootView.findViewById(R.id.edad_valor);
            edadValor.setText(mItem.edad + " años");

            // Raza de la mascota
            TextView razaValor = rootView.findViewById(R.id.raza_valor);
            razaValor.setText(mItem.raza);

            // Peso de la mascota
            TextView pesoValor = rootView.findViewById(R.id.peso_valor);
            pesoValor.setText(mItem.peso + " kg");

            // Vacunación
            RadioButton radioVacunadoSi = rootView.findViewById(R.id.radio_vacunado_si);
            RadioButton radioVacunadoNo = rootView.findViewById(R.id.radio_vacunado_no);
            if (mItem.vacunada) {
                radioVacunadoSi.setChecked(true);
            } else {
                radioVacunadoNo.setChecked(true);
            }

            // Desparasitación
            RadioButton radioDesparasitadoSi = rootView.findViewById(R.id.radio_desparasitado_si);
            RadioButton radioDesparasitadoNo = rootView.findViewById(R.id.radio_desparasitado_no);
            if (mItem.desparacitada) {
                radioDesparasitadoSi.setChecked(true);
            } else {
                radioDesparasitadoNo.setChecked(true);
            }

            // Esterilización
            RadioButton radioEsterilizadoSi = rootView.findViewById(R.id.radio_esterilizado_si);
            RadioButton radioEsterilizadoNo = rootView.findViewById(R.id.radio_esterilizado_no);
            if (mItem.esterilizada) {
                radioEsterilizadoSi.setChecked(true);
            } else {
                radioEsterilizadoNo.setChecked(true);
            }
        }

        return rootView;
    }
}