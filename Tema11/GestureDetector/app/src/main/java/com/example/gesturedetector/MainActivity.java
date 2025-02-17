package com.example.gesturedetector;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar el GestureDetector
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Toast.makeText(MainActivity.this, "Toque simple", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(MainActivity.this, "Doble toque", Toast.LENGTH_SHORT).show();
                return true;
            }

            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getX() < e2.getX()) {
                    Toast.makeText(MainActivity.this, "Deslizamiento hacia la derecha", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Deslizamiento hace la izquierda", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    // Sobre escribe el método onTouchEvent para interceptar eventos táctiles
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        // Pasar el evento al GestureDetector
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

}