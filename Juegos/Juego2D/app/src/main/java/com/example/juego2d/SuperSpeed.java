package com.example.juego2d;

public class SuperSpeed extends PowerUp {
    public SuperSpeed(int x, int y) {
        super(x, y);
    }

    @Override
    public void applyEffect(Dinosaurio dino) {
        dino.superSpeed(); // Activamos el power-up de Super Speed
    }
}