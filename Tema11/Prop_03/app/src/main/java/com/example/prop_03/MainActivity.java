package com.example.prop_03;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class MainActivity extends View {
    private Paint paint;

    public MainActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setColor(Color.BLACK); // Color de las figuras
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // Definir los límites de la elipse (ojo)
        float left = width * 0.2f;
        float top = height * 0.3f;
        float right = width * 0.8f;
        float bottom = height * 0.7f;
        RectF ovalBounds = new RectF(left, top, right, bottom);

        // Dibujar la elipse
        canvas.drawOval(ovalBounds, paint);

        // Dibujar el círculo en el centro del ojo
        float centerX = width / 2f;
        float centerY = height / 2f;
        float radius = (right - left) / 6; // Radio del círculo

        canvas.drawCircle(centerX, centerY, radius, paint);
    }
}
