package com.example.gestionmascotas;

import java.io.Serializable;
import java.util.UUID;

public class Mascota implements Serializable {

    private long id;
    private String nombre;
    private String raza;
    private int imagen;
    private int edad;
    private float peso;
    private boolean vacunada;
    private boolean desparacitada;
    private boolean esterilizada;

    public Mascota( String nombre, String raza, int imagen, int edad, float peso, boolean vacunada, boolean desparacitada, boolean esterilizada) {
//        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.imagen = imagen;
        this.edad = edad;
        this.peso = peso;
        this.vacunada = vacunada;
        this.desparacitada = desparacitada;
        this.esterilizada = esterilizada;
    }

    // Métodos getter y setter para cada propiedad
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public boolean isVacunada() {
        return vacunada;
    }

    public void setVacunada(boolean vacunada) {
        this.vacunada = vacunada;
    }

    public boolean isDesparacitada() {
        return desparacitada;
    }

    public void setDesparacitada(boolean desparacitada) {
        this.desparacitada = desparacitada;
    }

    public boolean isEsterilizada() {
        return esterilizada;
    }

    public void setEsterilizada(boolean esterilizada) {
        this.esterilizada = esterilizada;
    }
}
