package com.example.juego2d;

import android.content.Context;

public class SuperJump extends PowerUp {
    public SuperJump(int x, int y, Context context) {
        super(x, y, context);
    }

    @Override
    public void applyEffect(Player dino) {
        dino.activateSuperJump();
    }
}
