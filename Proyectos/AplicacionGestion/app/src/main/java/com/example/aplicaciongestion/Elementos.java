package com.example.aplicaciongestion;

public class Elementos {

    String nombre, raza;
    int imagen, edad;

    public Elementos(int imagen, String nombre, int edad, String raza) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.edad = edad;
        this.raza = raza;
    }

    public int getImagen() {
        return imagen;
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
}
