package com.example.angelapedreraprueba2eval2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class Vista extends View {
    private Paint linea, texto;
    private float escala;
    private float size;

    public Vista(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Líneas verdes
        linea = new Paint();
        linea.setColor(Color.GREEN);
        linea.setStrokeWidth(4);
        linea.setStyle(Paint.Style.STROKE);
        linea.setPathEffect(new DashPathEffect(new float[]{20, 10}, 0)); // Patrón de línea discontinua

        // Texto
        texto = new Paint();
//        texto.setColor(Color.BLACK);
        texto.setTextSize(40);
        texto.setAntiAlias(true);

        // Obtener la escala de densidad de la pantalla
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        escala = metrics.scaledDensity;

        // Definir el tamaño de paso escalado
        size = 30 * escala;
        texto.setTextSize(20 * escala); // Escalar el texto
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE); // Fondo blanco

        int width = getWidth();
        int height = getHeight();

        // Dibujar líneas horizontales con texto en cada una
        for (int i = 1; i * size < height; i++) {
            float y = i * size;

            // El uso del Path para dibujar las lineas discontinuas
            Path path = new Path();
            path.moveTo(0, y);
            path.lineTo(width, y);
            canvas.drawPath(path, linea); // Dibujar la línea discontinua
            canvas.drawText(String.valueOf(y), 50, y - 10, texto);

        }

        // Dibujar algunos valores adicionales en posiciones específicas
        int filaScale = (int) ((height / 4) / size * 30);

        canvas.drawText("Fila: " + height / 4 + " scale= " + escala, width / 3, height / 4, texto);
        canvas.drawText("Fila: " + height / 2 + " width= " + width, width / 3, height * 1 / 2, texto);
        canvas.drawText( "Fila: " + height * 3 / 4 + " height= " + height, width / 3, height * 3 / 4, texto);
        canvas.drawText("ratio= " + (float) height / width, width / 3, height, texto);

    }
}
