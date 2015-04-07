package com.quirquinchosanto.quirquinchosantoapp;

/**
 * Created by fernando on 3/24/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.quirquinchosanto.quirquinchosantoapp.data.ResultContract;

import static com.quirquinchosanto.quirquinchosantoapp.data.ResultContract.ResultEntry;

/**
 * Created by Pablo on 3/10/2015.
 */
public class Utility {
    private static final String LOG_TAG = Utility.class.getSimpleName();

    public static String getJsonStringFromNetwork(String team, String days) {
        Log.v(LOG_TAG, "Starting network connection");
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String timeFrame = "p" + days;

        try {
            final String FIXTURE_BASE_URL = "http://insolwebsite.com/lfmc13/QuirquinchoSantoAdminApp/generarJSON.php";
            //final String FIXTURE_BASE_URL = "http://10.0.0.13/QuirquinchoSanto/generarJSON.php";

            // final String FIXTURE_PATH = "fixtures";
            final String GET_PARAMETER_RESULTADOS = "resultados";
            final String GET_PARAMETER_NOTICIAS = "noticias";
            final String GET_PARAMETER_EQUIPO = "equipo";

            Uri builtUri = Uri.parse(FIXTURE_BASE_URL).buildUpon()
                    //   .appendPath(team)
                    //.appendPath(FIXTURE_PATH)
                    .appendQueryParameter(GET_PARAMETER_RESULTADOS, "1")
                    .appendQueryParameter(GET_PARAMETER_NOTICIAS, "1")
                    .appendQueryParameter(GET_PARAMETER_EQUIPO, "1")
                    .build();


            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null)
                return "";
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0)
                return "";

            return buffer.toString();
        } catch (IOException e) {
            Log.v(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.v(LOG_TAG, "Error closing stream", e);
                    e.printStackTrace();
                }
            }
        }

        return "";
    }

    public static void parseFixtureJson(String fixtureJson, int teamID, Context context) throws JSONException, ParseException {
        JSONObject jsonObject = new JSONObject(fixtureJson);
        ArrayList<ContentValues> values = new ArrayList<>();
        ArrayList<ContentValues> values_noticia = new ArrayList<>();
        ArrayList<ContentValues> values_equipo = new ArrayList<>();

        final String RESULTADOS = "resultados";
        final String EquipoLocal = "EquipoLocal";
        final String EquipoVisitante = "EquipoVisitante";
        final String Estado = "Estado";
        final String GolesLocal = "GolesLocal";
        final String GolesVisitante = "GolesVisitante";
        final String Fecha = "Fecha";
        final String fixtureID = "fixtureID";
        JSONArray fixturesArray = jsonObject.getJSONArray(RESULTADOS);



        for (int i = 0; i < fixturesArray.length(); i++) {
            String equipo_local;
            String equipo_visitante;
            int goles_local;
            int goles_visitante;
            String fechaString;
            long fecha;
            int fixture_ID;

            JSONObject matchObject = fixturesArray.getJSONObject(i);
            //  JSONObject resultObject = matchObject.getJSONObject(Estado);

            equipo_local = matchObject.getString(EquipoLocal);
            equipo_visitante = matchObject.getString(EquipoVisitante);
            goles_local =  matchObject.getInt(GolesLocal);
            goles_visitante =  matchObject.getInt(GolesVisitante);
            fechaString = matchObject.getString(Fecha);
            fixture_ID =  matchObject.getInt(fixtureID);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.US);
            Date d = dateFormat.parse(fechaString);
            fecha = d.getTime();

            ContentValues content = new ContentValues();

            content.put(ResultEntry.COLUMN_EQUIPO_LOCAL, equipo_local);
            content.put(ResultEntry.COLUMN_EQUIPO_VISITANTE, equipo_visitante);
            content.put(ResultEntry.COLUMN_GOLES_LOCAL, goles_local);
            content.put(ResultEntry.COLUMN_GOLES_VISITANTE, goles_visitante);
            content.put(ResultEntry.COLUMN_FECHA, fecha);
            content.put(ResultEntry.COLUMN_FIXTURE_ID, fixture_ID);

            values.add(content);
        }

        int inserted = 0;

        if (values.size() > 0) {
            ContentValues[] valuesArray = new ContentValues[values.size()];

            values.toArray(valuesArray);
            inserted = context.getContentResolver().bulkInsert(ResultEntry.CONTENT_URI, valuesArray);
        }

        Log.d(LOG_TAG, "FetchResult Complete Resultados " + inserted + " inserted");

        final String Equipo = "equipo";
        final String ImagenUrl = "ImagenUrl";
        final String Nombre = "Nombre";
        final String DatosNacimiento = "DatosNacimiento";
        final String Posicion = "Posicion";
        final String PartidosDisputados = "PartidosDisputados";
        final String GolesAnotados = "GolesAnotados";
        final String TarjetasAmarillas = "TarjetasAmarillas";
        final String TarjetasRojas = "TarjetasRojas";
        final String FechaModificacion = "Fecha";
        final String JugadorID = "jugadorID";
        JSONArray EquipoArray = jsonObject.getJSONArray(Equipo);



        for (int i = 0; i < EquipoArray.length(); i++) {
            String imagen_url;
            String nombre;
            String datos_nacimiento;
            String posicion;
            String partidos_disputados;
            String tarjetas_amarillas;
            String tarjetas_rojas;
            String goles_anotados;
            String fechaModificacionString;
            long fecha_modificacion;
            int jugador_ID;

            JSONObject matchObject = EquipoArray.getJSONObject(i);
            //  JSONObject resultObject = matchObject.getJSONObject(Estado);

            imagen_url = matchObject.getString(ImagenUrl);
            nombre = matchObject.getString(Nombre);
            datos_nacimiento =  matchObject.getString(DatosNacimiento);
            posicion =  matchObject.getString(Posicion);
            partidos_disputados =  matchObject.getString(PartidosDisputados);
            goles_anotados =  matchObject.getString(GolesAnotados);
            tarjetas_amarillas =  matchObject.getString(TarjetasAmarillas);
            tarjetas_rojas =  matchObject.getString(TarjetasRojas);
            fechaModificacionString = matchObject.getString(FechaModificacion);
            jugador_ID =  matchObject.getInt(JugadorID);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.US);
            Date d = dateFormat.parse(fechaModificacionString);
            fecha_modificacion = d.getTime();

            ContentValues content = new ContentValues();

            content.put(ResultEntry.COLUMN_IMAGEN_URL, imagen_url);
            content.put(ResultEntry.COLUMN_NOMBRE, nombre);
            content.put(ResultEntry.COLUMN_DATOS_NACIMIENTO, datos_nacimiento);
            content.put(ResultEntry.COLUMN_POSICION, posicion);
            content.put(ResultEntry.COLUMN_PARTIDOS_DISPUTADOS, partidos_disputados);
            content.put(ResultEntry.COLUMN_GOLES_ANOTADOS, goles_anotados);
            content.put(ResultEntry.COLUMN_TARJETAS_AMARILLAS, tarjetas_amarillas);
            content.put(ResultEntry.COLUMN_TARJETAS_ROJAS, tarjetas_rojas);
            content.put(ResultEntry.COLUMN_JUGADOR_ID, jugador_ID);
            content.put(ResultEntry.COLUMN_FECHA_MODIFICACION, fecha_modificacion);

            values_equipo.add(content);
        }

        int inserted_equipo = 0;

        if (values_equipo.size() > 0) {
            ContentValues[] valuesArrayEquipo = new ContentValues[values_equipo.size()];

            values_equipo.toArray(valuesArrayEquipo);
            inserted_equipo = context.getContentResolver().bulkInsert(ResultEntry.CONTENT_URI_EQUIPO, valuesArrayEquipo);
        }

        Log.d(LOG_TAG, "FetchResult Complete Equipo " + inserted_equipo + " inserted");

        final String NOTICIAS = "noticias";
        final String Titulo = "Titulo";
        final String TextoNoticia = "TextoNoticia";
        final String UrlImagen = "UrlImagen";
        final String Fuente = "Fuente";
        final String FechaNoticia = "Fecha";
        final String NoticiaID = "NoticiaID";
        JSONArray NoticiasArray = jsonObject.getJSONArray(NOTICIAS);



        for (int i = 0; i < NoticiasArray.length(); i++) {
            String titulo_noticia;
            String texto_noticia;
            String url_imagen;
            String fuente_noticia;
            String fechanoticiaString;
            long fecha_noticia;
            int noticia_ID;

            JSONObject matchObject = NoticiasArray.getJSONObject(i);
            //  JSONObject resultObject = matchObject.getJSONObject(Estado);

            titulo_noticia = matchObject.getString(Titulo);
            texto_noticia = matchObject.getString(TextoNoticia);
            url_imagen =  matchObject.getString(UrlImagen);
            fuente_noticia =  matchObject.getString(Fuente);
            fechanoticiaString = matchObject.getString(FechaNoticia);
            noticia_ID =  matchObject.getInt(NoticiaID);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.US);
            Date d = dateFormat.parse(fechanoticiaString);
            fecha_noticia = d.getTime();

            ContentValues content = new ContentValues();

            content.put(ResultEntry.COLUMN_TITULO, titulo_noticia);
            content.put(ResultEntry.COLUMN_TEXTO_NOTICIA, texto_noticia);
            content.put(ResultEntry.COLUMN_URL_IMAGEN, url_imagen);
            content.put(ResultEntry.COLUMN_FUENTE, fuente_noticia);
            content.put(ResultEntry.COLUMN_FECHA_NOTICIA, fecha_noticia);
            content.put(ResultEntry.COLUMN_NOTICIA_ID, noticia_ID);

            values_noticia.add(content);
        }

        int inserted_noticia = 0;

        if (values_noticia.size() > 0) {
            ContentValues[] valuesArrayNoticias = new ContentValues[values_noticia.size()];

            values_noticia.toArray(valuesArrayNoticias);
            inserted_noticia = context.getContentResolver().bulkInsert(ResultEntry.CONTENT_URI_NOTICIAS, valuesArrayNoticias);
        }

        Log.d(LOG_TAG, "FetchResult Complete Noticias " + inserted_noticia + " inserted");

    }

    public static int getPreferedTeam(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        return Integer.parseInt(prefs.getString(context.getString(R.string.pref_team_key), context.getString(R.string.pref_barcelona_key)));
    }

    public static int getPreferedDays(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        return Integer.parseInt(prefs.getString(context.getString(R.string.pref_days_key), context.getString(R.string.pref_days_default)));
    }
}
