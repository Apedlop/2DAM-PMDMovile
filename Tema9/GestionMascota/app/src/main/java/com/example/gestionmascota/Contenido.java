package com.example.gestionmascota;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Contenido {

    public static ArrayList<Lista_entrada> ENT_LISTA = new ArrayList<Lista_entrada>();

    public static Map<String, Lista_entrada> ENT_LISTA_HASHMAP = new HashMap<String, Lista_entrada>();

    public static class Lista_entrada {

        public String id;
        public int idImagen;
        public String nombre;
        public String raza;
        public int edad;
        public float peso;
        public boolean vacunada;
        public boolean desparacitada;
        public boolean esterilizada;

        public Lista_entrada(String id, int idImagen, String nombre, String raza, int edad, float peso, boolean vacunada, boolean desparacitada, boolean esterilizada) {
            this.id = id;
            this.idImagen = idImagen;
            this.nombre = nombre;
            this.raza = raza;
            this.edad = edad;
            this.peso = peso;
            this.vacunada = vacunada;
            this.desparacitada = desparacitada;
            this.esterilizada = esterilizada;
        }

        public String getId() {
            return id;
        }

        public int getIdImagen() {
            return idImagen;
        }

        public String getNombre() {
            return nombre;
        }

        public String getRaza() {
            return raza;
        }

        public int getEdad() {
            return edad;
        }

        public float getPeso() {
            return peso;
        }

        public boolean isVacunada() {
            return vacunada;
        }

        public boolean isDesparacitada() {
            return desparacitada;
        }

        public boolean isEsterilizada() {
            return esterilizada;
        }
    }

    private static void ponerEntrada(Lista_entrada entrada) {
        ENT_LISTA.add(entrada);
        ENT_LISTA_HASHMAP.put(entrada.id, entrada);
    }

    static {
        // Inicialización automática de datos
        ponerEntrada(new Lista_entrada("0", R.drawable.imagen_perro, "Max", "Bulldog Francés", 2, 8.5f, true, true, false));
        ponerEntrada(new Lista_entrada("1", R.drawable.imagen_gato, "Luna", "Persa", 3, 25.0f, true, true, true));
        ponerEntrada(new Lista_entrada("2", R.drawable.imagen_perro, "Rocky", "Labrador", 4, 30.5f, false, true, true));
        ponerEntrada(new Lista_entrada("3", R.drawable.imagen_perro, "Bella", "Beagle", 1, 10.0f, true, false, false));
   }

}
