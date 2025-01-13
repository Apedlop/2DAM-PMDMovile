package com.example.act_05_prop_04;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Contenido {

    public static ArrayList<Lista_entrada> ENT_LISTA = new ArrayList<Lista_entrada>();

    public static Map<String, Lista_entrada> ENT_LISTA_HASHMAP = new HashMap<String, Lista_entrada>();

    public static class Lista_entrada {

        public String id;
        public int idImagen;
        public String textoEncima;
        public String textoDebajo;

        public Lista_entrada(String id, int idImagen, String textoEncima, String textoDebajo) {
            this.id = id;
            this.idImagen = idImagen;
            this.textoEncima = textoEncima;
            this.textoDebajo = textoDebajo;
        }

    }

    private static void ponerEntrada(Lista_entrada entrada) {
        ENT_LISTA.add(entrada);
        ENT_LISTA_HASHMAP.put(entrada.id, entrada);
    }

    static {
        // Inicialización automática de datos
        ponerEntrada(new Lista_entrada("0", R.drawable.ima1, "DONUTS", "Descripción de DONUTS"));
        ponerEntrada(new Lista_entrada("1", R.drawable.ima2, "FROYO", "Descripción de FROYO"));
        ponerEntrada(new Lista_entrada("2", R.drawable.ima3, "GINGERBREAD", "Descripción de GINGERBREAD"));
        ponerEntrada(new Lista_entrada("3", R.drawable.ima4, "HONEYCOMB", "Descripción de HONEYCOMB"));
        ponerEntrada(new Lista_entrada("4", R.drawable.ima5, "ICE CREAM", "Descripción de ICE CREAM"));
        ponerEntrada(new Lista_entrada("5", R.drawable.ima6, "JELLY BEAN", "Descripción de JELLY BEAN"));
        ponerEntrada(new Lista_entrada("6", R.drawable.ima7, "KITKAT", "Descripción de KITKAT"));
        ponerEntrada(new Lista_entrada("7", R.drawable.ima8, "LOLLIPOP", "Descripción de LOLLIPOP"));
    }

}
