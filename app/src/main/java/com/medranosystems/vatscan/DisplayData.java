package com.medranosystems.vatscan;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by super on 7/4/2017.
 */

public class DisplayData {

    private List<Client> clients;
    private SlidingUpPanelLayout panel;
    private TextViews textViews;
    private Marker activeMarker;
    private SeekBar seekBar;
    private Polyline lineFlown, lineRemaining;

    public DisplayData(Activity activity) {
        final DisplayData displayData = this;
        this.textViews = new TextViews(activity);
        this.panel = (SlidingUpPanelLayout) activity.findViewById(R.id.sliding_layout);
        this.seekBar = (SeekBar) activity.findViewById(R.id.seekBar);

        // Disable manual control of progress bar
        seekBar.setVisibility(View.INVISIBLE);
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        // Clear text from panel when collapsed manually
        panel.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    textViews.clear(displayData);
                }
            }
        });
    }

    // Widebody and narrowbody aircraft substrings
    private static String[][] aircraftTypes = {
        { "b71", "b72", "b73", "b75", "a318", "a319", "a32", "cr", "e1", "md8", "md9" },
        { "b74", "b76", "b77", "b78", "a30", "a31", "a33", "a34", "a35", "a38", "il8", "il9" }
    };

    /**
     * Parses and organizes raw client data from vatsim data text file
     *
     * @param s         Raw text data
     * @param map       Google map object
     * @param mapData   Airport coords data
     */
    public void updateData(String s, GoogleMap map, MapData mapData) {
        // Extract client data
        String[] clientsRaw = (s.split("!CLIENTS:\n")[1]).split("\n;\n;\n!SERVERS:")[0].split("\n");

        // Remove everything from map
        map.clear();

        // Create new arraylist of clients
        clients = new ArrayList<>();
        for (String c : clientsRaw) {
            String[] clientData = c.split(":");

            if (Objects.equals(clientData[3], "PILOT")) {
                clients.add(new Pilot(clientData, map, mapData));
            } else if (Objects.equals(clientData[3], "ATC")) {
                clients.add(new Controller(clientData, map, mapData));
            }
        }

        // Add an onclick listener for each marker on the map
        addMarkerListeners(map);
    }

    /**
     * Adds an onclick listener for each marker on the map
     *
     * @param map Google map object
     */
    private void addMarkerListeners(final GoogleMap map) {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (Client c : clients) {

                    // Check if client is a pilot
                    if (Objects.equals(c.getClienttype(), "PILOT")) {
                        Pilot pilot = (Pilot) c;

                        // Check if clicked marker is the current pilot
                        if (marker.equals(pilot.getMarker())) {

                            // Removes flight paths of previous focused aircraft
                            if (activeMarker != null)
                                activeMarker.setAlpha(1.0f);
                            if (lineFlown != null)
                                lineFlown.remove();
                            if (lineRemaining != null)
                                lineRemaining.remove();

                            // Sets active marker to clicked marker
                            activeMarker = marker;

                            // Pulls up sliding panel
                            panel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

                            // Shows and sets progress of flight progress bar
                            seekBar.setVisibility(View.VISIBLE);
                            seekBar.setProgress(pilot.getFlightProgress());

                            // Shows flight paths
                            lineFlown = map.addPolyline(pilot.getLineFlown());
                            lineRemaining = map.addPolyline(pilot.getLineRemaining());

                            // Updates flight data on panel
                            textViews.update(pilot, marker);
                        }
                    }
                }
                return true;
            }
        });
    }

    /**
     * Returns the proper icon given the aircraft's identifier
     *
     * @param code  ICAO aircraft identifier
     * @return      Icon ID
     */
    public static int getAircraftType(String code) {
        String formattedCode = code.toLowerCase();

        for (int i = 0; i < aircraftTypes.length; i++) {
            for (int j = 0; j < aircraftTypes[i].length; j++) {
                if (formattedCode.contains(aircraftTypes[i][j])) {
                    if (i == 0) return R.drawable.narrowbody;
                    else if (i == 1) return R.drawable.widebody;
                }
            }
        }

        return R.drawable.ga;
    }

    public List<Client> getClients() {
        return this.clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public SlidingUpPanelLayout getPanel() {
        return this.panel;
    }

    public void setPanel(SlidingUpPanelLayout panel) {
        this.panel = panel;
    }

    public TextViews getTextViews() {
        return this.textViews;
    }

    public void setTextViews(TextViews textViews) {
        this.textViews = textViews;
    }

    public Marker getActiveMarker() {
        return this.activeMarker;
    }

    public void setActiveMarker(Marker activeMarker) {
        this.activeMarker = activeMarker;
    }

    public SeekBar getSeekBar() {
        return this.seekBar;
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public Polyline getLineFlown() {
        return lineFlown;
    }

    public void setLineFlown(Polyline lineFlown) {
        this.lineFlown = lineFlown;
    }

    public Polyline getLineRemaining() {
        return lineRemaining;
    }

    public void setLineRemaining(Polyline lineRemaining) {
        this.lineRemaining = lineRemaining;
    }
}
