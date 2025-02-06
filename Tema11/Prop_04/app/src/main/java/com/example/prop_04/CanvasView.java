package com.example.prop_04;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class CanvasView extends View {

    private Paint paint;

    public CanvasView(Context context) {
        super(context);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true); // Suaviza los bordes del texto
        paint.setColor(Color.BLACK); // Color del texto
        paint.setTextSize(60); // Tamaño del texto
        paint.setTypeface(Typeface.DEFAULT); // Tipo de fuente
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Definir las coordenadas base
        float startX = 50;
        float startY = 100;
        float spacing = 120; // Espaciado entre textos

        // Texto común
        String texto = "Pedrera López";

        // Primer texto: Normal
        paint.setTextSize(60);
        paint.setColor(Color.RED);
        paint.setTextAlign(Paint.Align.LEFT); // Justificación izquierda (por defecto)
        canvas.drawText(texto, startX, startY, paint);

        // Segundo texto: Mayor tamaño
        paint.setTextSize(100);
        paint.setColor(Color.GREEN);
        canvas.drawText(texto, startX, startY + spacing, paint);

        // Tercer texto: Justificación derecha
        paint.setTextSize(80);
        paint.setColor(Color.BLUE);
        paint.setTextAlign(Paint.Align.RIGHT); // Justificación derecha
        canvas.drawText(texto, getWidth() - 50, startY + 2 * spacing, paint);

        // Cuarto texto: Escalado
        paint.setTextSize(80);
        paint.setColor(Color.MAGENTA);
        paint.setTextAlign(Paint.Align.LEFT); // Restablecer justificación
        canvas.save();
        canvas.scale(1.5f, 1.5f, startX + 50, startY + 3 * spacing); // Escalar el canvas
        canvas.drawText(texto, startX + 50, startY + 3 * spacing, paint);
        canvas.restore();

        // Quinto texto: Inclinación (rotación)
        paint.setTextSize(80);
        paint.setColor(Color.YELLOW);
        canvas.save();
        canvas.rotate(45, startX + 50, startY + 4 * spacing); // Rotar el canvas
        canvas.drawText(texto, startX + 50, startY + 4 * spacing, paint);
        canvas.restore();
    }
}