package com.example.dibujarpantalla;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

class Lienzo extends View {
    private Paint paint;
    private Path path;

    public Lienzo(Context context) {
        super(context);
        paint = new Paint();
        path = new Path();

        // Configuramos el pincel
        paint.setColor(Color.BLUE);   // Color azul para dibujar
        paint.setStyle(Paint.Style.STROKE); // Solo trazo (no relleno)
        paint.setStrokeWidth(10f);    // Grosor del trazo
        paint.setAntiAlias(true);     // Suaviza los bordes
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Fondo color crema
        canvas.drawColor(Color.parseColor("#faebda"));
        // Dibujar el camino trazado
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Cuando se toca la pantalla, se empieza un nuevo camino
                path.moveTo(x, y);
                return true;
            case MotionEvent.ACTION_MOVE:
                // Cuando se arrastra el dedo, se dibuja una línea
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                // Al levantar el dedo, finaliza el camino
                break;
            default:
                return false;
        }
        // Forzar a que se redibuje la pantalla
        invalidate();
        return true;
    }
}