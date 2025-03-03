package com.example.juego2d;

public class SuperJump extends PowerUp {
    public SuperJump(int x, int y) {
        super(x, y);
    }

    @Override
    public void applyEffect(Dinosaurio dino) {
        dino.superJump(); // Activamos el power-up de Super Jump
    }
}
