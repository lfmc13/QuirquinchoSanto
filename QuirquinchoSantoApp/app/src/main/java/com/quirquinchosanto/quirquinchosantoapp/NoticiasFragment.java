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
public class NoticiasFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = NoticiasFragment.class.getSimpleName();

    private static final int RESULT_LOADER = 0;

    private static final String[] RESULT_COLUMNS = {
            ResultContract.ResultEntry._ID,
            ResultContract.ResultEntry.COLUMN_TITULO,
            ResultContract.ResultEntry.COLUMN_TEXTO_NOTICIA,
            ResultContract.ResultEntry.COLUMN_FUENTE,
            ResultContract.ResultEntry.COLUMN_URL_IMAGEN,
            ResultContract.ResultEntry.COLUMN_FECHA_NOTICIA,
            ResultContract.ResultEntry.COLUMN_NOTICIA_ID
    };

    static final int COL_ID = 0;
    static final int COL_TITULO = 1;
    static final int COL_TEXTO_NOTICIA = 2;
    static final int COL_FUENTE = 3;
    static final int COL_URL_IMAGEN = 4;
    static final int COL_FECHA_NOTICIA = 5;
    static final int COL_NOTICIA_ID = 6;

    private NoticiasAdapter NoticiasAdapter;

    public NoticiasFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_noticias, container, false);
        NoticiasAdapter = new NoticiasAdapter(getActivity(), null, 0);

        ListView listView = (ListView)rootView.findViewById(R.id.noticias_list_view);

        listView.setAdapter(NoticiasAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), DetailsNoticia.class);
                int teamSetting = Utility.getPreferedTeam(getActivity());

                intent.setData(ResultContract.ResultEntry.buildNoticiasWithNoticiaID("noticias", cursor.getString(COL_NOTICIA_ID)));
                startActivity(intent);
            }
        });

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

    public void onTeamChanged() {
        updateResults();
        getLoaderManager().restartLoader(RESULT_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        int teamSetting = Utility.getPreferedTeam(getActivity());
        int daysSetting = Utility.getPreferedDays(getActivity());

        long date = System.currentTimeMillis();

        String sortOrder = ResultContract.ResultEntry.COLUMN_FECHA_NOTICIA + " DESC";
        Uri resultForTeamUri = ResultContract.ResultEntry.buildNoticias("resultados", date);

        return new CursorLoader(getActivity(), resultForTeamUri, RESULT_COLUMNS, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        NoticiasAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        NoticiasAdapter.swapCursor(null);
    }
}
