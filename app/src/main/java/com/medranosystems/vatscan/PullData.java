package com.medranosystems.vatscan;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by super on 7/4/2017.
 */

public class PullData extends AsyncTask<String, Integer, String> {

    public AsyncResponse delegate = null;

    // Do the long-running work in here
    protected String doInBackground(String... s) {
        String contents = "";

        try {
            URL url = new URL(s[0]);
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                contents += (line + "\n");
            }
            reader.close();
            System.out.println("Data fetch successful");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Data fetch failed");
        }

        return contents;
    }

    // This is called each time you call publishProgress()
    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(String s) {
        delegate.processFinish(s);
    }
}
