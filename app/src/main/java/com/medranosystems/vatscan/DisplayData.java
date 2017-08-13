package com.medranosystems.vatscan;

import android.app.Activity;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by super on 7/4/2017.
 */

public class DisplayData {

    private static List<Client> clients;
    private SlidingUpPanelLayout panel;
    private TextViews textViews;

    public DisplayData(Activity activity) {
        this.textViews = new TextViews(activity);
        this.panel = (SlidingUpPanelLayout) activity.findViewById(R.id.sliding_layout);
        panel.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    textViews.clear();
                }
            }
        });
    }

    private static String[][] aircraftTypes = {
        { "b71", "b72", "b73", "b75", "a318", "a319", "a32", "cr", "e1", "md8", "md9" },
        { "b74", "b76", "b77", "b78", "a30", "a31", "a33", "a34", "a35", "a38", "il8", "il9" }
    };

    public void updateData(String s, GoogleMap map) {
        String[] clientsRaw = (s.split("!CLIENTS:\n")[1]).split("\n;\n;\n!SERVERS:")[0].split("\n");
        clients = new ArrayList<>();

        map.clear();

        for (String c : clientsRaw) {
            String[] data = c.split(":");

            if (Objects.equals(data[3], "PILOT")) {
                clients.add(new Pilot(data, map));
            } else if (Objects.equals(data[3], "ATC")) {
                clients.add(new Controller(data, map));
            }
        }

        addMarkerListeners(map);
    }

    private void addMarkerListeners(GoogleMap map) {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (Client c : clients) {
                    if (Objects.equals(c.getClienttype(), "PILOT")) {
                        Pilot p = (Pilot) c;
                        if (marker.equals(p.getMarker())) {
                            panel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                            textViews.update(p);
                        }
                    }
                }
                return false;
            }
        });
    }

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

}
