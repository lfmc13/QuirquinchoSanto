package com.quirquinchosanto.quirquinchosantoapp;

/**
 * Created by fernando on 3/25/2015.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quirquinchosanto.quirquinchosantoapp.data.ResultContract;

public class ResultAdapter extends CursorAdapter {
    public ResultAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    private String convertCursorToResult(Cursor cursor) {
        return String.format("%s: %d - %s: %d",
                cursor.getString(ResultsFragment.COL_EQUIPO_LOCAL),
                cursor.getInt(ResultsFragment.COL_GOLES_LOCAL),
                cursor.getString(ResultsFragment.COL_EQUIPO_VISITANTE),
                cursor.getInt(ResultsFragment.COL_GOLES_VISITANTE));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.result_view, parent, false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        TextView equipoLocal = (TextView)view.findViewById(R.id.nombre_equipo_local);
        equipoLocal.setText(cursor.getString(ResultsFragment.COL_EQUIPO_LOCAL));

        TextView equipoVisitante = (TextView)view.findViewById(R.id.nombre_equipo_visitante);
        equipoVisitante.setText(cursor.getString(ResultsFragment.COL_EQUIPO_VISITANTE));

        TextView ResultadoEquipoVisitante = (TextView)view.findViewById(R.id.result_equipo_visitante);
        ResultadoEquipoVisitante.setText(cursor.getString(ResultsFragment.COL_GOLES_VISITANTE));

        TextView ResultadoEquipoLocal = (TextView)view.findViewById(R.id.result_equipo_local);
        ResultadoEquipoLocal.setText(cursor.getString(ResultsFragment.COL_GOLES_LOCAL));

        ImageView equipoLocalImagen = (ImageView)view.findViewById(R.id.EquipoLocalImage);

        ImageView equipoVisitanteImagen = (ImageView)view.findViewById(R.id.EquipoVisitanteImage);

        switch (cursor.getString(ResultsFragment.COL_EQUIPO_LOCAL)) {
            case "San Jose":
                equipoLocalImagen.setImageResource(R.mipmap.ic_sanjose);
                break;
            case "San José":
                equipoLocalImagen.setImageResource(R.mipmap.ic_sanjose);
                break;
            case "Bolivar":
                equipoLocalImagen.setImageResource(R.mipmap.ic_bolivar);
                break;
            case "The Strongest":
                equipoLocalImagen.setImageResource(R.mipmap.ic_thestrongest);
                break;
            case "Real Potosi":
                equipoLocalImagen.setImageResource(R.mipmap.ic_rpotosi);
                break;
            case "Nacional Potosi":
                equipoLocalImagen.setImageResource(R.mipmap.ic_npotosi);
                break;
            case "Universitario":
                equipoLocalImagen.setImageResource(R.mipmap.ic_universitario);
                break;
            case "U. de Pando":
                equipoLocalImagen.setImageResource(R.mipmap.ic_upando);
                break;
            case "Wilstermann":
                equipoLocalImagen.setImageResource(R.mipmap.ic_wilster);
                break;
            case "Oriente Petrolero":
                equipoLocalImagen.setImageResource(R.mipmap.ic_opetrolero);
                break;
            case "Blooming":
                equipoLocalImagen.setImageResource(R.mipmap.ic_blooming);
                break;
            case "Sport Boys":
                equipoLocalImagen.setImageResource(R.mipmap.ic_sportboys);
                break;
            case "P. Yacuiba":
                equipoLocalImagen.setImageResource(R.mipmap.ic_petrolero);
                break;
            default:
                throw new UnsupportedOperationException("Unknown team: " + cursor.getString(ResultsFragment.COL_EQUIPO_LOCAL));
        }

        switch (cursor.getString(ResultsFragment.COL_EQUIPO_VISITANTE)) {
            case "San Jose":
                equipoVisitanteImagen.setImageResource(R.mipmap.ic_sanjose);
                break;
            case "San José":
                equipoVisitanteImagen.setImageResource(R.mipmap.ic_sanjose);
                break;
            case "Bolivar":
                equipoVisitanteImagen.setImageResource(R.mipmap.ic_bolivar);
                break;
            case "The Strongest":
                equipoVisitanteImagen.setImageResource(R.mipmap.ic_thestrongest);
                break;
            case "Real Potosi":
                equipoVisitanteImagen.setImageResource(R.mipmap.ic_rpotosi);
                break;
            case "Nacional Potosi":
                equipoVisitanteImagen.setImageResource(R.mipmap.ic_npotosi);
                break;
            case "Universitario":
                equipoVisitanteImagen.setImageResource(R.mipmap.ic_universitario);
                break;
            case "U. de Pando":
                equipoVisitanteImagen.setImageResource(R.mipmap.ic_upando);
                break;
            case "Wilstermann":
                equipoVisitanteImagen.setImageResource(R.mipmap.ic_wilster);
                break;
            case "Oriente Petrolero":
                equipoVisitanteImagen.setImageResource(R.mipmap.ic_opetrolero);
                break;
            case "Blooming":
                equipoVisitanteImagen.setImageResource(R.mipmap.ic_blooming);
                break;
            case "Sport Boys":
                equipoVisitanteImagen.setImageResource(R.mipmap.ic_sportboys);
                break;
            case "P. Yacuiba":
                equipoVisitanteImagen.setImageResource(R.mipmap.ic_petrolero);
                break;
            default:
                throw new UnsupportedOperationException("Unknown team: " + cursor.getString(ResultsFragment.COL_EQUIPO_VISITANTE));
        }
    }
}
