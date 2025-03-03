package com.example.juego2d;

public class CrazyScenario extends PowerUp {
    public CrazyScenario(int x, int y) {
        super(x, y);
    }

    @Override
    public void applyEffect(Dinosaurio dino) {
        dino.crazyScenario(); // Activamos el power-up de Crazy Scenario
    }
}