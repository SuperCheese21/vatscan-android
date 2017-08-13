package com.medranosystems.vatscan;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, AsyncResponse {

    private GoogleMap map;
    private DisplayData displayData;

    private static final String[] URLS = {
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

        this.displayData = new DisplayData(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMapToolbarEnabled(false);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                displayData.getPanel().setPanelState(
                        SlidingUpPanelLayout.PanelState.COLLAPSED
                );
                displayData.getTextViews().clear(displayData);
            }
        });

        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.style_blue_essence)
            );
            if (!success) {
                Toast.makeText(
                        getApplicationContext(),
                        "Style parsing failed.",
                        Toast.LENGTH_LONG
                ).show();
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
        displayData.updateData(output, map);
    }

    public GoogleMap getMap() {
        return this.map;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public DisplayData getDisplayData() {
        return this.displayData;
    }

    public void setDisplayData(DisplayData displayData) {
        this.displayData = displayData;
    }

}
