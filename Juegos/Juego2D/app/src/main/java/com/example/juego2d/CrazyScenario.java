package com.example.juego2d;

import android.content.Context;

public class CrazyScenario extends PowerUp {
    public CrazyScenario(int x, int y, Context context) {
        super(x, y, context);
    }

    @Override
    public void applyEffect(Player dino) {
        dino.activateCrazyScenario();  // Activa el efecto de temblor en el dinosaurio
    }
}
