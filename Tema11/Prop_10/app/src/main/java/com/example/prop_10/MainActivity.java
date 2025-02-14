package com.example.prop_10;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listaSensores;
    private SensorManager miSensorManager;
    private List<Sensor> listaSensoresDisponibles;
    private ArrayList<String> nombresSensores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaSensores = findViewById(R.id.listaSensores);
        nombresSensores = new ArrayList<>();

        // Usando el código de la imagen
        miSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        listaSensoresDisponibles = miSensorManager.getSensorList(Sensor.TYPE_ALL);

        for (Sensor sensor : listaSensoresDisponibles) {
            nombresSensores.add(sensor.getName());
            Log.d("SENSOR", sensor.getName()); // Imprime en el Logcat
        }

        // Mostrar los nombres en el ListView
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, nombresSensores);
        listaSensores.setAdapter(adaptador);
    }
}