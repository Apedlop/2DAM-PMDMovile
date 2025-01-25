package com.example.gestionmascotas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLite extends SQLiteOpenHelper {

    private static final String dataBase = "mascotas.db";
    private static final int versionDatabase = 1;
    private static final String tabla = "mascotas";
    private static final String idColumna = "id";
    private static final String nombreColumna = "nombre";
    private static final String razaColumna = "raza";
    private static final String imagenColumna = "imagen";
    private static final String edadColumna = "edad";
    private static final String pesoColumna = "peso";
    private static final String vacunadaColumna = "vacunada";
    private static final String desparacitadaColumna = "desparacitada";
    private static final String esterilizadaColumna = "esterilizada";

    // Constructor
    public SQLite(Context context) {
        super(context, dataBase, null, versionDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla con todas las columnas necesarias
        String crearTabla = "CREATE TABLE " + tabla + "(" +
                idColumna + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                nombreColumna + " TEXT, " +
                razaColumna + " TEXT, " +
                imagenColumna + " INTEGER, " +
                edadColumna + " INTEGER, " +
                pesoColumna + " REAL, " +
                vacunadaColumna + " INTEGER, " +
                desparacitadaColumna + " INTEGER, " +
                esterilizadaColumna + " INTEGER)";
        db.execSQL(crearTabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tabla);
        onCreate(db);
    }

    // Método para agregar una mascota
    public void addMascota(Mascota mascota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(nombreColumna, mascota.getNombre());
        values.put(razaColumna, mascota.getRaza());
        values.put(imagenColumna, mascota.getImagen());
        values.put(edadColumna, mascota.getEdad());
        values.put(pesoColumna, mascota.getPeso());
        values.put(vacunadaColumna, mascota.isVacunada() ? 1 : 0);
        values.put(desparacitadaColumna, mascota.isDesparacitada() ? 1 : 0);
        values.put(esterilizadaColumna, mascota.isEsterilizada() ? 1 : 0);
        db.insert(tabla, null, values);
        db.close();
    }

    // Método para obtener todas las mascotas
    public ArrayList<Mascota> getAllMascotas() {
        ArrayList<Mascota> mascotas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + tabla;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(nombreColumna));
                String raza = cursor.getString(cursor.getColumnIndexOrThrow(razaColumna));
                int imagen = cursor.getInt(cursor.getColumnIndexOrThrow(imagenColumna));
                int edad = cursor.getInt(cursor.getColumnIndexOrThrow(edadColumna));
                float peso = cursor.getFloat(cursor.getColumnIndexOrThrow(pesoColumna));
                boolean vacunada = cursor.getInt(cursor.getColumnIndexOrThrow(vacunadaColumna)) > 0;
                boolean desparacitada = cursor.getInt(cursor.getColumnIndexOrThrow(desparacitadaColumna)) > 0;
                boolean esterilizada = cursor.getInt(cursor.getColumnIndexOrThrow(esterilizadaColumna)) > 0;

                Mascota mascota = new Mascota(nombre, raza, imagen, edad, peso, vacunada, desparacitada, esterilizada);
                mascotas.add(mascota);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return mascotas;
    }
}
