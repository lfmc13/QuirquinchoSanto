package com.quirquinchosanto.quirquinchosantoapp;

/**
 * Created by fernando on 4/7/2015.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.util.Date;

public class EquipoAdapter extends CursorAdapter {
    public EquipoAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.jugador_detail, parent, false);

        return view;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // TextView textView = (TextView)view;

//        textView.setText(convertCursorToResult(cursor));

        TextView textViewNombre = (TextView)view.findViewById(R.id.textViewNombre);
        textViewNombre.setText(cursor.getString(EquipoFragment.COL_NOMBRE));

        TextView textViewNacimiento = (TextView)view.findViewById(R.id.textViewNacimiento);
        textViewNacimiento.setText(cursor.getString(EquipoFragment.COL_DATOS_NACIMIENTO));

        TextView textViewPosicion = (TextView)view.findViewById(R.id.textViewPosicion);
        textViewPosicion.setText( "Posición :" +  cursor.getString(EquipoFragment.COL_POSICION));

        TextView textViewGolesAnotados = (TextView)view.findViewById(R.id.textViewGolesTemporada);
        textViewGolesAnotados.setText(  "Goles Anotados :" + cursor.getString(EquipoFragment.COL_GOLES_ANOTADOS));

        TextView textViewTarjetasAmarillas = (TextView)view.findViewById(R.id.textViewTarjetasAmarillas);
        textViewTarjetasAmarillas.setText("Tarjetas Amarillas:" + cursor.getString(EquipoFragment.COL_TARJETAS_AMARILLAS));

        TextView textViewTarjetasRojas = (TextView)view.findViewById(R.id.textViewTarjetasAmarillas);
        textViewTarjetasRojas.setText( "Tarjetas Rojas:" +  cursor.getString(EquipoFragment.COL_TARJETAS_ROJAS));


        LoaderImageView  loaderImageView = (LoaderImageView) view.findViewById(R.id.loaderImageViewDetailJugador);
        //loaderImageView.setLayoutParams(new LinearLayout.LayoutParams(600,500));
        //loaderImageView.setPadding(0,0,0,0);
        //loaderImageView.setRight(-50);

        Log.d("La url es :", "http://insolwebsite.com/Images/" + cursor.getString(EquipoFragment.COL_URL_IMAGEN_JUGADOR));
        loaderImageView.setImageDrawable("http://insolwebsite.com/Images/" + cursor.getString(EquipoFragment.COL_URL_IMAGEN_JUGADOR));



    }

}