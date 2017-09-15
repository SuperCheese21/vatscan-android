package com.medranosystems.vatscan;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Created by super on 8/16/2017.
 */

public class MapData {

    private Context context;
    public JSONObject airports;
    public JSONObject firs;

    public MapData(Context c) {
        this.context = c;

        try {
            this.airports = new JSONObject(loadJSON("airports_min.json"));
            this.firs = new JSONObject(loadJSON("firs_min.json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String loadJSON(String file) {
        String json = "";
        try {
            InputStream mInputStream = context.getAssets().open(file);

            int size = mInputStream.available();
            byte[] buffer = new byte[size];

            mInputStream.read(buffer);
            mInputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    public LatLng getAirportCoords(String code) {
        double lat = 0.0, lon = 0.0;
        String latString = "", lonString = "";

        try {
            JSONObject airport = airports.getJSONObject(code);

            latString = airport.getString("latitude");
            lonString = airport.getString("longitude");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (!Objects.equals(latString, "")) {
            lat = Double.parseDouble(latString);
        }
        if (!Objects.equals(lonString, "")) {
            lon = Double.parseDouble(lonString);
        }

        return new LatLng(lat, lon);
    }

    public LatLng[] getFirBoundary(String code) {
        LatLng[] coords = {};

        try {
            JSONArray mJSONArray = firs.getJSONArray(code);

            coords = new LatLng[mJSONArray.length()];
            for (int i = 0; i < mJSONArray.length(); i++) {
                JSONObject mJSONObject = mJSONArray.getJSONObject(i);

                double lat = Double.parseDouble(mJSONObject.getString("lat"));
                double lon = Double.parseDouble(mJSONObject.getString("lon"));

                coords[i] = new LatLng(lat, lon);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return coords;
    }

}
