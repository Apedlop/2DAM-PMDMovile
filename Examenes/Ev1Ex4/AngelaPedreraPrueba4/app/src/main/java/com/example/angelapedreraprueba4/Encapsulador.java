package com.example.angelapedreraprueba4;

public class Encapsulador {

    private int imagen;
    private String titulo, contenido, url;

    public Encapsulador(int imagen, String titulo, String contenido, String url) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.contenido = contenido;
        this.url = url;
    }

    public int getImagen() {
        return imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public String getUrl() {
        return url;
    }
}
