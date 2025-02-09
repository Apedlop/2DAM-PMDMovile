package com.example.gestionmascotas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class MascotaDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mascotas.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla de mascotas
    public static final String TABLE_MASCOTAS = "mascotas";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_RAZA = "raza";
    public static final String COLUMN_IMAGEN = "imagenByteArray";
    public static final String COLUMN_EDAD = "edad";
    public static final String COLUMN_PESO = "peso";
    public static final String COLUMN_VACUNADA = "vacunada";
    public static final String COLUMN_ESTERILIZADA = "esterilizada";
    public static final String COLUMN_DESPARACITADA = "desparacitada";

    // Sentencia SQL para crear la tabla de mascotas
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_MASCOTAS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOMBRE + " TEXT, " +
                    COLUMN_RAZA + " TEXT, " +
                    COLUMN_IMAGEN + " BLOB, " +
                    COLUMN_EDAD + " INTEGER, " +
                    COLUMN_PESO + " FLOAT, " +
                    COLUMN_VACUNADA + " INTEGER, " +
                    COLUMN_DESPARACITADA + " INTEGER, " +
                    COLUMN_ESTERILIZADA + " INTEGER)";

    // Constructor
    public MascotaDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASCOTAS); // Eliminar tabla existente
        onCreate(db); // Crear la tabla nuevamente con la nueva estructura
    }

    // Insertar mascota
    public long insertarMascota(Mascota mascota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, mascota.getNombre());
        values.put(COLUMN_RAZA, mascota.getRaza());
        values.put(COLUMN_IMAGEN, mascota.getImagenByteArray());
        values.put(COLUMN_EDAD, mascota.getEdad());
        values.put(COLUMN_PESO, mascota.getPeso());
        values.put(COLUMN_VACUNADA, mascota.isVacunada() ? 1 : 0);  // Convertir booleano a 1 o 0
        values.put(COLUMN_DESPARACITADA, mascota.isDesparacitada() ? 1 : 0); // Convertir booleano a 1 o 0
        values.put(COLUMN_ESTERILIZADA, mascota.isEsterilizada() ? 1 : 0); // Convertir booleano a 1 o 0

        return db.insert(TABLE_MASCOTAS, null, values);
    }

    // Obtener todas las mascotas de la DataBase
    public ArrayList<Mascota> obtenerMascotas() {
        ArrayList<Mascota> mascotas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MASCOTAS, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Mascota mascota = new Mascota(
//                        cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RAZA)),
                        cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EDAD)),
                        cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_PESO)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VACUNADA)) == 1,  // Convertir a booleano
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DESPARACITADA)) == 1,  // Convertir a booleano
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ESTERILIZADA)) == 1   // Convertir a booleano
                );
                mascotas.add(mascota);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return mascotas;
    }

    // Eliminar mascota
    public long eliminarMascota(String nombre) {
        return getWritableDatabase().delete(TABLE_MASCOTAS, COLUMN_NOMBRE + " LIKE ? ", new String[]{String.valueOf(nombre)}); // Debería usar un identificador único, pero si uso el ID no hace nada
    }

    // Actualizar mascota
    public long actualizarMascota(Mascota mascota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, mascota.getNombre());
        values.put(COLUMN_RAZA, mascota.getRaza());
        values.put(COLUMN_IMAGEN, mascota.getImagenByteArray());
        values.put(COLUMN_EDAD, mascota.getEdad());
        values.put(COLUMN_PESO, mascota.getPeso());
        values.put(COLUMN_VACUNADA, mascota.isVacunada() ? 1 : 0);
        values.put(COLUMN_ESTERILIZADA, mascota.isEsterilizada() ? 1 : 0);
        values.put(COLUMN_DESPARACITADA, mascota.isDesparacitada() ? 1 : 0);

        return db.update(TABLE_MASCOTAS, values, COLUMN_NOMBRE + " LIKE ? ", new String[]{String.valueOf(mascota.getNombre())});
    }
}
