package com.quirquinchosanto.quirquinchosantoapp;

/**
 * Created by fernando on 3/27/2015.
 */
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class NoticiasAdapter extends CursorAdapter {
    public NoticiasAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    private String convertCursorToResult(Cursor cursor) {
        return String.format("%s - %s - %s - %s",
                cursor.getString(NoticiasFragment.COL_TITULO),
                cursor.getString(NoticiasFragment.COL_TEXTO_NOTICIA),
                cursor.getString(NoticiasFragment.COL_FUENTE),
                cursor.getString(NoticiasFragment.COL_URL_IMAGEN));
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.noticia_view, parent, false);

        return view;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
       // TextView textView = (TextView)view;

//        textView.setText(convertCursorToResult(cursor));

        TextView tituloNoticia = (TextView)view.findViewById(R.id.TituloNoticia);
        tituloNoticia.setText(cursor.getString(NoticiasFragment.COL_TITULO));
        TextView textoNoticia = (TextView)view.findViewById(R.id.TextoNoticiaLayout);
        textoNoticia.setText(cursor.getString(NoticiasFragment.COL_TEXTO_NOTICIA));

        TextView FechaNoticia = (TextView)view.findViewById(R.id.Fecha_noticia);

        long date = Long.parseLong(cursor.getString(NoticiasFragment.COL_FECHA_NOTICIA));
        Date curDateTime = new Date(date);
        Log.d("La fecha es :", curDateTime.toString());
        String date_c = new java.text.SimpleDateFormat("dd/MM/yyyy").format(curDateTime);

        FechaNoticia.setText(date_c);

        LoaderImageView  loaderImageView = (LoaderImageView) view.findViewById(R.id.loaderImageView);
        Log.d("La url es :", "http://insolwebsite.com/Images/" + cursor.getString(NoticiasFragment.COL_URL_IMAGEN));
        loaderImageView.setImageDrawable("http://insolwebsite.com/Images/" + cursor.getString(NoticiasFragment.COL_URL_IMAGEN));








    }

}