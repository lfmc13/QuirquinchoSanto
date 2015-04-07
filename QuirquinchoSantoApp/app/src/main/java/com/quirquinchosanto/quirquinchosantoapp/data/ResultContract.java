package com.quirquinchosanto.quirquinchosantoapp.data;

/**
 * Created by fernando on 3/24/2015.
 */
import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

public class ResultContract {

    // El valor de Content Authority es un nombre para el content provider, esto es
    // simular a las relaciones entre el nombre de dominio de un sitio web.
    // Una cadena conveniente para el "Content Authority" es el nombre del paquete
    // de la aplicacion, que se garantiza que sea unico en el dispositivo
    public static final String CONTENT_AUTHORITY = "com.quirquinchosanto.quirquinchosantoapp";

    // Esta es la URI Basica que identificara a nuestro Content Provider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Paths posibles que se añaden a la URI base
    // Por ejemplo, la url content://com.example.pablo.soccernews/result
    // Obtendra una lista de todos los resultados, pero una url como
    // content://com.example.pablo.soccernews/loquesea devolvera un error
    public static final String PATH_RESULTADOS = "resultados";
    public static final String PATH_NOTICIAS = "noticias";
    public static final String PATH_EQUIPO = "equipo";

    public static long normalizeDate(long startDate) {
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);

        return time.setJulianDay(julianDay);
    }

    // Clase interna que define nuestro contrato con la columna Base
    public static final class ResultEntry implements BaseColumns {
        // Path base de results
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RESULTADOS).build();

        // Path base de noticias
        public static final Uri CONTENT_URI_NOTICIAS = BASE_CONTENT_URI.buildUpon().appendPath(PATH_NOTICIAS).build();

        // Path base de equipo
        public static final Uri CONTENT_URI_EQUIPO = BASE_CONTENT_URI.buildUpon().appendPath(PATH_EQUIPO).build();

        public static final String CONTENT_RESULTADOS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESULTADOS;

        public static final String CONTENT_NOTICIAS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTICIAS;

        public static final String CONTENT_ITEM_NOTICIAS = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTICIAS;

        public static final String CONTENT_ITEM_RESULTADOS = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESULTADOS;

        public static final String CONTENT_EQUIPO = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EQUIPO;

        public static final String TABLE_NAME_RESULTADOS = "resultados";

        public static final String COLUMN_EQUIPO_LOCAL = "EquipoLocal";
        public static final String COLUMN_EQUIPO_VISITANTE = "EquipoVisitante";
        public static final String COLUMN_GOLES_LOCAL = "GolesLocal";
        public static final String COLUMN_GOLES_VISITANTE = "GolesVisitante";
        public static final String COLUMN_FECHA = "fecha";
        public static final String COLUMN_FIXTURE_ID = "fixtureID";

        public static final String TABLE_NAME_NOTICIAS = "noticias";
        public static final String COLUMN_TITULO = "Titulo";
        public static final String COLUMN_TEXTO_NOTICIA = "TextoNoticia";
        public static final String COLUMN_FECHA_NOTICIA = "Fecha";
        public static final String COLUMN_URL_IMAGEN = "UrlImagen";
        public static final String COLUMN_FUENTE = "Fuente";
        public static final String COLUMN_NOTICIA_ID = "NoticiaID";

        public static final String TABLE_NAME_EQUIPO = "equipo";
        public static final String COLUMN_IMAGEN_URL = "ImagenURL";
        public static final String COLUMN_NOMBRE = "Nombre";
        public static final String COLUMN_DATOS_NACIMIENTO = "DatosNacimiento";
        public static final String COLUMN_POSICION = "Posicion";
        public static final String COLUMN_PARTIDOS_DISPUTADOS = "PartidosDisputados";
        public static final String COLUMN_GOLES_ANOTADOS = "GolesAnotados";
        public static final String COLUMN_TARJETAS_AMARILLAS = "TarjetasAmarillas";
        public static final String COLUMN_TARJETAS_ROJAS = "TarjetasRojas";
        public static final String COLUMN_JUGADOR_ID = "JugadorID";
        public static final String COLUMN_FECHA_MODIFICACION= "Fecha";

        public static Uri buildResultUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildNoticiasUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI_NOTICIAS, id);
        }

        public static Uri buildEquipoUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI_EQUIPO, id);
        }

        public static Uri buildResultTeam(String resultados) {
            return CONTENT_URI.buildUpon().appendPath(resultados).build();

        }

        public static Uri buildResultTeamWithStartDate(String resultados, long date) {
            long normalizeDate = normalizeDate(date);

            // return CONTENT_URI.buildUpon().appendPath(resultados)
            //       .appendQueryParameter(COLUMN_FECHA, Long.toString(normalizeDate)).build();
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildResultTeamWithDate(String resultados, long date) {
            long normalizeDate = normalizeDate(date);

            // return CONTENT_URI.buildUpon().appendPath(resultados)
            //       .appendPath(Long.toString(normalizeDate)).build();
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildNoticias(String resultados, long date) {
            long normalizeDate = normalizeDate(date);

            // return CONTENT_URI.buildUpon().appendPath(resultados)
            //       .appendPath(Long.toString(normalizeDate)).build();
            //return CONTENT_URI_NOTICIAS.buildUpon().appendQueryParameter("noticias","1").build();
            return CONTENT_URI_NOTICIAS.buildUpon().build();
        }

        public static Uri buildNoticiasWithNoticiaID(String resultados, String NoticiaID) {
           // long normalizeDate = normalizeDate(date);

            // return CONTENT_URI.buildUpon().appendPath(resultados)
            //       .appendPath(Long.toString(normalizeDate)).build();

            return CONTENT_URI_NOTICIAS.buildUpon().appendPath(NoticiaID).build();
        }

        public static Uri buildEquipo() {
            return CONTENT_URI_EQUIPO.buildUpon().build();
        }

        public static int getTeamFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static int getNoticiaIDFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static long getDateFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(2));
        }

        public static long getStartDateFromUri(Uri uri) {
            String dateString = uri.getQueryParameter(COLUMN_FECHA);
            if (dateString != null && dateString.length() > 0) {
                return Long.parseLong(dateString);
            } else
                return 0;
        }
    }
}
