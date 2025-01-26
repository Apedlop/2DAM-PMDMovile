package com.example.gestionmascotas;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Informacion extends AppCompatActivity {

    private TextView infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        infoText = findViewById(R.id.infoText);

        // Load the file from resources
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.uso_app);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            infoText.setText(stringBuilder.toString());
            reader.close();
        } catch (IOException e) {
            infoText.setText("Error al cargar la información.");
        }

    }
}