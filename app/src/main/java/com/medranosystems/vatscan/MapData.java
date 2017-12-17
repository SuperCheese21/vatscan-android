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

    public MapData(Context c) {
        this.context = c;

        try {
            this.airports = new JSONObject(loadJSON("airports_min.json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Gets contents of file in assets library
     *
     * @param file  File name
     * @return      File contents
     */
    private String loadJSON(String file) {
        String contents = "";
        try {
            InputStream mInputStream = context.getAssets().open(file);

            int size = mInputStream.available();
            byte[] buffer = new byte[size];

            mInputStream.read(buffer);
            mInputStream.close();
            contents = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contents;
    }

    /**
     * Gets the latitude and longitude of an airport
     *
     * @param code  ICAO airport identifier
     * @return      Google Maps Lat/Lon object
     */
    public LatLng getAirportCoords(String code) {
        double lat = 0.0, lon = 0.0;
        String latString = "", lonString = "";

        if (airports.has(code)) {
            JSONObject airport = null;
            try {
                airport = airports.getJSONObject(code);

                latString = airport.getString("latitude");
                lonString = airport.getString("longitude");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (!Objects.equals(latString, "")) {
            lat = Double.parseDouble(latString);
        }
        if (!Objects.equals(lonString, "")) {
            lon = Double.parseDouble(lonString);
        }

        return new LatLng(lat, lon);
    }

}
