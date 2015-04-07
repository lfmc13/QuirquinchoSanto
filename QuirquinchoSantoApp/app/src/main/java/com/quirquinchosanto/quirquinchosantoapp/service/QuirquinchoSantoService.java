package com.quirquinchosanto.quirquinchosantoapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;

import java.text.ParseException;


import com.quirquinchosanto.quirquinchosantoapp.Utility;


public class QuirquinchoSantoService extends IntentService {

    public static final String TEAM_QUERY_EXTRA = "tqe";
    private static final String LOG_TAG = QuirquinchoSantoService.class.getSimpleName();

    public QuirquinchoSantoService() {
        super("quirquinchosantoapp");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String team = intent.getStringExtra(TEAM_QUERY_EXTRA);
        String json = Utility.getJsonStringFromNetwork(team, "90");

        Log.v(LOG_TAG, "Service started");

        try {
            Utility.parseFixtureJson(json, Integer.parseInt(team), this);
        } catch (JSONException | ParseException e) {
            Log.e(LOG_TAG, e.getMessage());
            e.printStackTrace();
        }


        Log.v(LOG_TAG, "Service finished");
    }
}
