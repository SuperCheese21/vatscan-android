package com.medranosystems.vatscan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, AsyncResponse {

    private GoogleMap mMap;
    ProgressBar mProgressBar;
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
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mProgressBar = (ProgressBar) findViewById(R.id.loadProgress);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Timer t = new Timer();

        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateData();
            }
        }, 0, 30000);
    }

    private void updateData() {
        final int rand = ThreadLocalRandom.current().nextInt(0, URLS.length);
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
        System.out.println("Size: " + output.length());

        displayData(output);
    }

    private void displayData(String rawData) {
        System.out.println("displayData()");
        String[][] data;
        DisplayData display = new DisplayData();

        data = display.parseData(rawData);

        mMap.clear();
        display.addMarkers(data, mMap);
    }
}
