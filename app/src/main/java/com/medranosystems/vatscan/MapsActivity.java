package com.medranosystems.vatscan;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MapStyleOptions;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, AsyncResponse {

    private GoogleMap mMap;

    public static final String[] URLS = {
            "http://info.vroute.net/vatsim-data.txt",
            "http://data.vattastic.com/vatsim-data.txt",
            "http://vatsim.aircharts.org/vatsim-data.txt",
            "http://vatsim-data.hardern.net/vatsim-data.txt",
            "http://wazzup.flightoperationssystem.com/vatsim/vatsim-data.txt"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);

        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.style_blue_essence)
            );
            if (!success) {
                System.out.println("Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Toast.makeText(
                    getApplicationContext(),
                    "Can't find style. Error: " + e.toString(),
                    Toast.LENGTH_LONG
            ).show();
        }

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                pullUpdate();
            }
        }, 0, 60000);
    }

    private void pullUpdate() {
        final int rand = ThreadLocalRandom.current().nextInt(0, URLS.length);
        final ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.loadProgress);
        final AsyncResponse response = this;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PullData mPullData = new PullData(mProgressBar);
                mPullData.delegate = response;
                mPullData.execute(URLS[rand]);
            }
        });
    }

    @Override
    public void processFinish(String output){
        DisplayData.updateData(output, mMap);
    }

}
