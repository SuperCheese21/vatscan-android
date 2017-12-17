package com.medranosystems.vatscan;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by super on 7/4/2017.
 */

public class ClientData extends AsyncTask<String, Integer, String> {

    public AsyncResponse delegate = null;
    private ProgressBar mProgressBar;
    private static final String TAG = "DATA";

    public ClientData(ProgressBar p){
        this.mProgressBar = p;
    }

    @Override
    protected String doInBackground(String... s) {
        StringBuilder contents = new StringBuilder();

        try {
            // Initiate URL connection
            URL url = new URL(s[0]);
            URLConnection connection = url.openConnection();

            // Initiate new Buffered Reader to receive input stream
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );

            // Build string
            String line;
            while ((line = reader.readLine()) != null) {
                contents.append(line).append("\n");
            }

            // Close buffered reader
            reader.close();

            Log.i(TAG,"Data fetch successful");
        } catch(Exception e) {
            e.printStackTrace();
            Log.e(TAG,"Data fetch failed");
        }

        return contents.toString();
    }

    @Override
    protected void onPreExecute() {
        // Show loading bar before update
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
    }

    @Override
    protected void onPostExecute(String s) {
        // Hide loading bar when update is complete
        mProgressBar.setVisibility(View.GONE);
        delegate.processFinish(s);
    }
}
