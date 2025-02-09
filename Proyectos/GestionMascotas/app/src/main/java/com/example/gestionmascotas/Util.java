package com.example.gestionmascotas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class Util {

    // Convierte un Bitmap a un array de bytes (byte[])
    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        if (bitmap == null) {
            return new byte[0];  // Retorna un arreglo vacío en caso de que el bitmap sea null
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream); // Comprime en formato PNG
        return byteArrayOutputStream.toByteArray();
    }


    // Convierte un array de bytes a un Bitmap
    public static Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public static Bitmap intToBitmap(int resourceId, Context context) {
        return BitmapFactory.decodeResource(context.getResources(), resourceId);
    }

    public static byte[] intToByteArray(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    public static int byteArrayToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

}
