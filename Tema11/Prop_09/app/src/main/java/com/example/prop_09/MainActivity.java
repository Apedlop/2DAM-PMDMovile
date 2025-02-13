package com.example.prop_09;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView touchInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        touchInfoTextView = findViewById(R.id.touchInfoTextView);

        // Configurar la vista para detectar eventos de toque
        View touchArea = findViewById(R.id.touchArea);
        touchArea.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleTouchEvent(event);
                return true;
            }
        });
    }

    private void handleTouchEvent(MotionEvent event) {
        StringBuilder info = new StringBuilder();

        // Iterar sobre todos los puntos tocados
        int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            int pointerId = event.getPointerId(i);
            float x = event.getX(i);
            float y = event.getY(i);

            // Identificar el tipo de evento
            String action = getActionName(event.getActionMasked());

            // Añadir la información de cada punto de contacto
            info.append("Punto: ").append(pointerId)
                    .append(" - Acción: ").append(action)
                    .append(" - Posición: (").append(x).append(", ").append(y).append(")\n");
        }

        // Mostrar la información en el TextView
        touchInfoTextView.setText(info.toString());
    }

    private String getActionName(int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            case MotionEvent.ACTION_POINTER_DOWN:
                return "ACTION_POINTER_DOWN";
            case MotionEvent.ACTION_POINTER_UP:
                return "ACTION_POINTER_UP";
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_CANCEL";
            default:
                return "UNKNOWN";
        }
    }
}