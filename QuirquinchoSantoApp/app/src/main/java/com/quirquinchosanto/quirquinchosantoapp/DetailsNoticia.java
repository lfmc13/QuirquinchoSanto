package com.quirquinchosanto.quirquinchosantoapp;

/**
 * Created by fernando on 3/30/2015.
 */

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quirquinchosanto.quirquinchosantoapp.data.ResultContract;

import static com.quirquinchosanto.quirquinchosantoapp.data.ResultContract.*;

/**
 * Created by fernando on 3/25/2015.
 */



public class DetailsNoticia extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailViewFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);

            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
        private static final String LOG_TAG = DetailViewFragment.class.getSimpleName();
        private String result;

        private static final int RESULT_LOADER = 0;

        private static final String[] RESULT_COLUMNS = {
                ResultContract.ResultEntry._ID,
                ResultContract.ResultEntry.COLUMN_TITULO,
                ResultContract.ResultEntry.COLUMN_TEXTO_NOTICIA,
                ResultContract. ResultEntry.COLUMN_URL_IMAGEN,
                ResultContract.ResultEntry.COLUMN_FUENTE,
                ResultContract.ResultEntry.COLUMN_FECHA_NOTICIA
        };

        static final int COL_NOTICIA_ID = 0;
        static final int COL_TITULO = 1;
        static final int COL_TEXTO_NOTICIA = 2;
        static final int COL_URL_IMAGEN = 3;
        static final int COL_FUENTE = 4;
        static final int COL_FECHA_NOTICIA = 5;

        public DetailViewFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.noticia_detail, container, false);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            getLoaderManager().initLoader(RESULT_LOADER, null, this);
            super.onActivityCreated(savedInstanceState);
        }

        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            Log.v(LOG_TAG, "In OnCreateLoader");
            Intent intent = getActivity().getIntent();

            if (intent == null)
                return null;
            return new CursorLoader(getActivity(), intent.getData(), RESULT_COLUMNS, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
            Log.v(LOG_TAG, "In onloadFinished");
            if (!cursor.moveToFirst())
                return;

            TextView tituloNoticia = (TextView)getView().findViewById(R.id.TituloNoticiaDetail);
            tituloNoticia.setText(cursor.getString(COL_TITULO));
            TextView textoNoticia = (TextView)getView().findViewById(R.id.TextoNoticiaLayoutDetail);
            textoNoticia.setText(cursor.getString(COL_TEXTO_NOTICIA));
            textoNoticia.setLines(100);


            TextView FechaNoticia = (TextView)getView().findViewById(R.id.Fecha_noticiaDetail);

            int date = (cursor.getColumnIndex(cursor.getString(COL_FECHA_NOTICIA)));
            String date_c = new java.text.SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date (date*1000));

            FechaNoticia.setText(date_c);

            LoaderImageView  loaderImageView = (LoaderImageView) getView().findViewById(R.id.loaderImageViewDetail);
           // loaderImageView.setImageDrawable(cursor.getString(COL_URL_IMAGEN));

            loaderImageView.setImageDrawable("http://insolwebsite.com/Images/" + cursor.getString(COL_URL_IMAGEN));

        }

        @Override
        public void onLoaderReset(Loader<Cursor> cursorLoader) {

        }
    }
}
