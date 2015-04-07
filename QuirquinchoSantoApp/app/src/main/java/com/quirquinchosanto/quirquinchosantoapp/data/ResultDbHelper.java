package com.quirquinchosanto.quirquinchosantoapp.data;

/**
 * Created by fernando on 3/24/2015.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.quirquinchosanto.quirquinchosantoapp.data.ResultContract.ResultEntry;

public class ResultDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 9;

    static final String DATABASE_NAME = "quirquinchosanto.db";

    public ResultDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + ResultEntry.TABLE_NAME_RESULTADOS + " (" +
                ResultEntry._ID + " INTEGER PRIMARY KEY," +
                ResultEntry.COLUMN_EQUIPO_LOCAL + " TEXT NOT NULL," +
                ResultEntry.COLUMN_EQUIPO_VISITANTE+ " TEXT NOT NULL," +
                ResultEntry.COLUMN_GOLES_LOCAL + " INTEGER NOT_NULL," +
                ResultEntry.COLUMN_GOLES_VISITANTE + " INTEGER NOT_NULL," +
                ResultEntry.COLUMN_FECHA + " INTEGER NOT_NULL," +
                ResultEntry.COLUMN_FIXTURE_ID + " INTEGER NOT_NULL," +
                " UNIQUE (" + ResultEntry.COLUMN_FECHA + ", " +
                ResultEntry.COLUMN_FIXTURE_ID + ") ON CONFLICT REPLACE);";
        db.execSQL(SQL_CREATE_LOCATION_TABLE);

        final String SQL_CREATE_LOCATION_TABLE_NOTICIAS = "CREATE TABLE " + ResultEntry.TABLE_NAME_NOTICIAS + " (" +
                ResultEntry._ID + " INTEGER PRIMARY KEY," +
                ResultEntry.COLUMN_TITULO + " TEXT NOT NULL," +
                ResultEntry.COLUMN_TEXTO_NOTICIA + " TEXT NOT NULL," +
                ResultEntry.COLUMN_FUENTE + " INTEGER NOT_NULL," +
                ResultEntry.COLUMN_URL_IMAGEN + " INTEGER NOT_NULL," +
                ResultEntry.COLUMN_FECHA + " INTEGER NOT_NULL," +
                ResultEntry.COLUMN_NOTICIA_ID + " INTEGER NOT_NULL," +
                " UNIQUE (" + ResultEntry.COLUMN_FECHA_NOTICIA + ", " +
                ResultEntry.COLUMN_NOTICIA_ID + ") ON CONFLICT REPLACE);";
        db.execSQL(SQL_CREATE_LOCATION_TABLE_NOTICIAS);

        final String SQL_CREATE_LOCATION_TABLE_EQUIPO = "CREATE TABLE " + ResultEntry.TABLE_NAME_EQUIPO + " (" +
                ResultEntry._ID + " INTEGER PRIMARY KEY," +
                ResultEntry.COLUMN_IMAGEN_URL + " TEXT NOT NULL," +
                ResultEntry.COLUMN_NOMBRE + " TEXT NOT NULL," +
                ResultEntry.COLUMN_DATOS_NACIMIENTO + " TEXT NOT_NULL," +
                ResultEntry.COLUMN_POSICION + " TEXT NOT_NULL," +
                ResultEntry.COLUMN_PARTIDOS_DISPUTADOS + " INTEGER NOT_NULL," +
                ResultEntry.COLUMN_GOLES_ANOTADOS + " INTEGER NOT_NULL," +
                ResultEntry.COLUMN_TARJETAS_AMARILLAS + " INTEGER NOT_NULL," +
                ResultEntry.COLUMN_TARJETAS_ROJAS + " INTEGER NOT_NULL," +
                ResultEntry.COLUMN_FECHA_MODIFICACION + " INTEGER NOT_NULL," +
                ResultEntry.COLUMN_JUGADOR_ID + " INTEGER NOT_NULL," +
                " UNIQUE (" + ResultEntry.COLUMN_JUGADOR_ID + ") ON CONFLICT REPLACE);";
        db.execSQL(SQL_CREATE_LOCATION_TABLE_EQUIPO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ResultEntry.TABLE_NAME_RESULTADOS);
        db.execSQL("DROP TABLE IF EXISTS " + ResultEntry.TABLE_NAME_NOTICIAS);
        db.execSQL("DROP TABLE IF EXISTS " + ResultEntry.TABLE_NAME_EQUIPO);
        onCreate(db);
    }
}
