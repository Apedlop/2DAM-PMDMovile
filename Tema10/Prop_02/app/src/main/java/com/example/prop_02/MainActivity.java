package com.example.prop_02;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener referencia al ImageView
        ImageView imageView = findViewById(R.id.imageView);

        // Establecer la imagen desde drawable
        imageView.setImageResource(R.drawable.panda);

        // Ajustar imagen a la pantalla
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}