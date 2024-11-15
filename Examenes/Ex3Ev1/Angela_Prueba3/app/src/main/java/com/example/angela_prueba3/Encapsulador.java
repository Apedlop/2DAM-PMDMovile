package com.example.angela_prueba3;

public class Encapsulador {

    private int imagen;
    private String titulo, datos;

    public Encapsulador(int imagen, String titulo, String datos) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.datos = datos;
    }

    public int getImagen() {
        return imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDatos() {
        return datos;
    }
}
