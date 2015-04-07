package com.quirquinchosanto.quirquinchosantoapp;

/**
 * Created by fernando on 3/25/2015.
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
import android.widget.TextView;

import com.quirquinchosanto.quirquinchosantoapp.data.ResultContract;

import static com.quirquinchosanto.quirquinchosantoapp.data.ResultContract.*;


public class DetailsActivity extends ActionBarActivity {

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
                ResultEntry._ID,
                ResultEntry.COLUMN_EQUIPO_LOCAL,
                ResultEntry.COLUMN_EQUIPO_VISITANTE,
                ResultEntry.COLUMN_GOLES_LOCAL,
                ResultEntry.COLUMN_GOLES_VISITANTE,
                ResultEntry.COLUMN_FECHA
        };

        static final int COL_FIXTURE_ID = 0;
        static final int COL_EQUIPO_LOCAL = 1;
        static final int COL_EQUIPO_VISITANTE = 2;
        static final int COL_GOLES_LOCAL = 3;
        static final int COL_GOLES_VISITANTE = 4;
        static final int COL_FECHA = 5;

        public DetailViewFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_details, container, false);
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

            result = String.format("%s: %d - %s: %d",
                    cursor.getString(COL_EQUIPO_LOCAL),
                    cursor.getInt(COL_GOLES_LOCAL),
                    cursor.getString(COL_EQUIPO_VISITANTE),
                    cursor.getInt(COL_GOLES_VISITANTE));
            TextView textView = (TextView)getView().findViewById(R.id.details_text);

            textView.setText(result);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> cursorLoader) {

        }
    }
}
