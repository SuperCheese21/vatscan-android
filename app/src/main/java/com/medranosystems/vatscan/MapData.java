package com.medranosystems.vatscan;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

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
            this.airports = new JSONObject(loadJSON("airports.json"));
            this.firs = new JSONObject(loadJSON("fir_Data.json"));
        } catch (JSONException e) {
            System.out.println(e);
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

}
