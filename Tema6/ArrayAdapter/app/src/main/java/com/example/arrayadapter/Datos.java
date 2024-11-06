package com.example.arrayadapter;

public class Datos {

    private String texto1;
    private String texto2;
    private int imagenResId;  // ID del recurso de imagen en drawable
    private String urlEnlace;

    public Datos(String texto1, String texto2, int imagenResId, String urlEnlace) {
        this.texto1 = texto1;
        this.texto2 = texto2;
        this.imagenResId = imagenResId;
        this.urlEnlace = urlEnlace;
    }

    public String getTexto1() {
        return texto1;
    }

    public String getTexto2() {
        return texto2;
    }

    public int getImagenResId() {
        return imagenResId;
    }

    public String getUrlEnlace() {
        return urlEnlace;
    }
}
