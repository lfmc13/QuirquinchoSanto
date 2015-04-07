package com.quirquinchosanto.quirquinchosantoapp;

/**
 * Created by fernando on 3/27/2015.
 */

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.quirquinchosanto.quirquinchosantoapp.data.ResultContract;
import com.quirquinchosanto.quirquinchosantoapp.service.QuirquinchoSantoService;


/**
 * A placeholder fragment containing a simple view.
 */
public class EquipoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = EquipoFragment.class.getSimpleName();

    private static final int RESULT_LOADER = 0;

    private static final String[] RESULT_COLUMNS = {
            ResultContract.ResultEntry._ID,
            ResultContract.ResultEntry.COLUMN_IMAGEN_URL,
            ResultContract.ResultEntry.COLUMN_NOMBRE,
            ResultContract.ResultEntry.COLUMN_DATOS_NACIMIENTO,
            ResultContract.ResultEntry.COLUMN_POSICION,
            ResultContract.ResultEntry.COLUMN_GOLES_ANOTADOS,
            ResultContract.ResultEntry.COLUMN_TARJETAS_AMARILLAS,
            ResultContract.ResultEntry.COLUMN_TARJETAS_ROJAS,
            ResultContract.ResultEntry.COLUMN_JUGADOR_ID
    };

    static final int COL_ID = 0;
    static final int COL_URL_IMAGEN_JUGADOR = 1;
    static final int COL_NOMBRE = 2;
    static final int COL_DATOS_NACIMIENTO = 3;
    static final int COL_POSICION = 4;
    static final int COL_GOLES_ANOTADOS = 5;
    static final int COL_TARJETAS_AMARILLAS = 6;
    static final int COL_TARJETAS_ROJAS = 7;
    static final int COL_JUGADOR_ID = 8;

    private EquipoAdapter EquipoAdapter;

    public EquipoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.result_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                updateResults();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_equipo, container, false);
        EquipoAdapter = new EquipoAdapter(getActivity(), null, 0);

        ListView listView = (ListView)rootView.findViewById(R.id.equipo_list_view);

        listView.setAdapter(EquipoAdapter);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(RESULT_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }


    private void updateResults() {
        Intent intent = new Intent(getActivity(), QuirquinchoSantoService.class);

        intent.putExtra(QuirquinchoSantoService.TEAM_QUERY_EXTRA, Integer.toString(Utility.getPreferedTeam(getActivity())));

        getActivity().startService(intent);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        int teamSetting = Utility.getPreferedTeam(getActivity());
        int daysSetting = Utility.getPreferedDays(getActivity());

        long date = System.currentTimeMillis();

        String sortOrder = ResultContract.ResultEntry.COLUMN_JUGADOR_ID + " ASC";
        Uri resultForTeamUri = ResultContract.ResultEntry.buildEquipo();

        return new CursorLoader(getActivity(), resultForTeamUri, RESULT_COLUMNS, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        EquipoAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        EquipoAdapter.swapCursor(null);
    }
}
