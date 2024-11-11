package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {

    // Constructor de la clase DataHelperR
    public DataHelper(Context context, String nombreBaseDatos, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombreBaseDatos, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creación de la tabla usuarios
        String crearTabla = "CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "usuario TEXT," +
                "contrasena TEXT," +
                "correo TEXT," +
                "edad INTEGER," +
                "rol TEXT)";
        db.execSQL(crearTabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }
}
