package com.medranosystems.vatscan;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private MapData mapData;
    private DisplayData displayData;

    private static final int UPDATE_PERIOD = 60000;

    // VATSIM data file URL's
    private static final String[] URLS = {
            "http://info.vroute.net/vatsim-data.txt",
            "http://data.vattastic.com/vatsim-data.txt",
            "http://vatsim.aircharts.org/vatsim-data.txt",
            "http://vatsim-data.hardern.net/vatsim-data.txt",
            "http://wazzup.flightoperationssystem.com/vatsim/vatsim-data.txt"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("CREATION","onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.displayData = new DisplayData(this);
        this.mapData = new MapData(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        // Hides map toolbar controls
        map.getUiSettings().setMapToolbarEnabled(false);

        // Collapse panel on tap outside of panel
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
                Log.e("STYLE","Style parsing failed");
            }
        } catch (Resources.NotFoundException e) {
            Log.wtf("STYLE", "Couldn't find style");
        }

        // Pull an update once every 60 seconds
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                pullUpdate();
            }
        }, 0, UPDATE_PERIOD);
    }

    /**
     * Pull updated data from VATSIM data servers
     */
    private void pullUpdate() {
        // Pick random number to select random URL
        final int rand = ThreadLocalRandom.current().nextInt(0, URLS.length);

        final ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.loadProgress);
        final AsyncResponse response = this;

        /*
         * Fetch updated data
         * Must be called from the UI thread in order to show/hide loading bar
         */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ClientData data = new ClientData(mProgressBar);
                data.delegate = response;
                data.execute(URLS[rand]);
            }
        });
    }

    @Override
    public void processFinish(String output){
        // Update map after data is fetched
        displayData.updateData(output, map, mapData);
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

    public MapData getMapData() {
        return this.mapData;
    }

    public void setMapData(MapData data) {
        this.mapData = data;
    }

}
