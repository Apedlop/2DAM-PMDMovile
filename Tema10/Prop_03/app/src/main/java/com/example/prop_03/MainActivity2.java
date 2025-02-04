package com.example.prop_03;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MainActivity2 extends View {

    private Paint paint;

    public MainActivity2(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // Dimensiones del óvalo
        float left = width * 0.2f;
        float top = height * 0.3f;
        float right = width * 0.8f;
        float bottom = height * 0.7f;

        // Dibujar óvalo (rojo)
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawOval(100, 100, 1000, 650, paint);


        // Dibujar círculo (negro) en el centro del óvalo
        float centerX = width / 2f;
        float centerY = height / 2f;
        float radius = Math.min(right - left, bottom - top) / 4; // Tamaño proporcional

        paint.setColor(Color.BLACK);
        canvas.drawCircle(centerX, centerY, radius, paint);
    }
}
