package com.example.prop_02;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class Adaptador extends FragmentStatePagerAdapter {

    int numTab;

    public Adaptador(@NonNull FragmentManager fm, int numTab) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);  // Correcto uso del constructor
        this.numTab = numTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Retorna el fragmento correspondiente a la posición
        if (position == 0) {
            return new Primero();  // Fragmento para la primera pestaña
        } else if (position == 1) {
            return new Segundo();  // Fragmento para la segunda pestaña
        } else if (position == 2) {
            return new Tercero();  // Fragmento para la tercera pestaña
        } else {
            throw new IllegalArgumentException("Posición no válida: " + position);  // Manejo de error para posiciones no válidas
        }
    }

    @Override
    public int getCount() {
        return numTab;  // Retorna el número de tabs
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Devuelve el título de cada tab
        if (position == 0) {
            return "PRIMERO";
        } else if (position == 1) {
            return "SEGUNDO";
        } else if (position == 2) {
            return "TERCERO";
        } else {
            return null;
        }
    }
}
