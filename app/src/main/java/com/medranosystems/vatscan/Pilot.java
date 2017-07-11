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
        } catch (NumberFormatException ignore) {};
        this.transponder = data[17];
        this.flightplan = new FlightPlan(data);
        this.icon = getAircraftType(this.flightplan.aircraft);
        this.markerOptions = new MarkerOptions()
                .position(location)
                .title(this.callsign)
                .snippet(this.realname)
                .icon(BitmapDescriptorFactory.fromResource(this.icon))
                .anchor(0.5f, 0.5f)
                .rotation(this.heading);
        this.marker = map.addMarker(this.markerOptions);
    }

    public static int getAircraftType(String code) {
        String formattedCode = code.toLowerCase();

        if (formattedCode.contains("70") ||
                formattedCode.contains("71") ||
                formattedCode.contains("72") ||
                formattedCode.contains("73") ||
                formattedCode.contains("75") ||
                formattedCode.contains("a32") ||
                formattedCode.contains("cr") ||
                formattedCode.contains("e1") ||
                formattedCode.contains("md8") ||
                formattedCode.contains("md9")) {
            return R.drawable.narrowbody;
        } else if (formattedCode.contains("74") ||
                formattedCode.contains("76") ||
                formattedCode.contains("77") ||
                formattedCode.contains("78") ||
                formattedCode.contains("a30") ||
                formattedCode.contains("a31") ||
                formattedCode.contains("a33") ||
                formattedCode.contains("a34") ||
                formattedCode.contains("a35") ||
                formattedCode.contains("a38") ||
                formattedCode.contains("il8") ||
                formattedCode.contains("il9")) {
            return R.drawable.widebody;
        }

        return R.drawable.ga;
    }

}
