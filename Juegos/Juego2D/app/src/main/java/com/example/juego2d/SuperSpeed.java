package com.example.juego2d;

import android.content.Context;

public class SuperSpeed extends PowerUp {
    public SuperSpeed(int x, int y, Context context) {
        super(x, y, context);
    }

    @Override
    public void applyEffect(Player dino) {
        dino.activateSuperSpeed();
    }
}
