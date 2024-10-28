package com.example.alarma;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSetAlarm = findViewById(R.id.btnSetAlarm);

        // Configurar el OnClickListener para el botón
        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para el AlarmReceiver
                Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);

                // Crear el PendingIntent
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // Obtener el AlarmManager
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                // Configurar la alarma para que se dispare en 5 segundos
                long triggerTime = System.currentTimeMillis() + 5000; // 5 segundos en milisegundos
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            }
        });
    }
}
