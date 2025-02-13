package com.example.prop_08;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements OnClick {

    private LinearLayout layout1, layout2, layout3, layout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Iniciamos los layouts
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        layout4 = findViewById(R.id.layout4);

        // Establecer listeners de toque para cada layout
        setTouchListener(layout1, 0xFF2196F3);  // Azul
        setTouchListener(layout2, 0xFF4CAF50);  // Verde
        setTouchListener(layout3, 0xFFFF9800);  // Naranja
        setTouchListener(layout4, 0xFFF44336);  // Rojo

    }

    // Configurar el listener de toque para cada layout
    private void setTouchListener(LinearLayout layout, final int color) {
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        onLayoutPressed(color);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        onLayoutReleased(color);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onLayoutPressed(int color) {
        String colorName = getColorName(color);
        Toast.makeText(this, colorName + " Presionado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLayoutReleased(int color) {
        String colorName = getColorName(color);
        Toast.makeText(this, colorName + " Soltado", Toast.LENGTH_SHORT).show();
    }

    private String getColorName(int color) {
        if (color == 0xFF2196F3) {
            return "Azul";
        } else if (color == 0xFF4CAF50) {
            return "Verde";
        } else if (color == 0xFFFF9800) {
            return "Naranja";
        } else if (color == 0xFFF44336) {
            return "Rojo";
        }
        return "Desconocido";
    }

}