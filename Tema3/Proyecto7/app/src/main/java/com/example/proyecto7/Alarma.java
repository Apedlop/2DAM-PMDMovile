package com.example.proyecto7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Alarma extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Acción a realizar cuando se dispare la alarma
        Toast.makeText(context, "¡Alarma activada!", Toast.LENGTH_SHORT).show();
    }
}