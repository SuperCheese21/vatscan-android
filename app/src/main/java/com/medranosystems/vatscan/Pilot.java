package com.medranosystems.vatscan;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by super on 7/8/2017.
 */

public class Pilot extends Client {

    public int altitude;
    public int heading;
    public int groundspeed;
    public String transponder;

    public FlightPlan flightplan;
    public int icon;
    public MarkerOptions markerOptions;
    public Marker marker;

    public Pilot(String[] data, GoogleMap map) {
        super(data);

        try {
            this.altitude = Integer.parseInt(data[7]);
            this.heading = Integer.parseInt(data[38]);
            this.groundspeed = Integer.parseInt(data[8]);
        } catch (NumberFormatException ignore) {}
        this.transponder = data[17];
        this.flightplan = new FlightPlan(data);
        this.icon = getAircraftType(this.flightplan.aircraft);
        this.markerOptions = new MarkerOptions()
                .position(location)
                .title(this.callsign)
                .snippet(this.flightplan.aircraft)
                .icon(BitmapDescriptorFactory.fromResource(this.icon))
                .anchor(0.5f, 0.5f)
                .rotation(this.heading);
        this.marker = map.addMarker(this.markerOptions);
    }

    public static int getAircraftType(String code) {
        String formattedCode = code.toLowerCase();
        String[][] types = DisplayData.aircraftTypes;

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < types[i].length; j++) {
                if (formattedCode.contains(types[i][j])) {
                    if (i == 0) return R.drawable.narrowbody;
                    else if (i == 1) return R.drawable.widebody;
                }
            }
        }

        return R.drawable.ga;
    }

    public void removeMarker() {
        this.marker.remove();
    }

}
