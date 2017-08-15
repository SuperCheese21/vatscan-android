package com.medranosystems.vatscan;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by super on 7/8/2017.
 */

public class Pilot extends Client {

    private int altitude;
    private int heading;
    private int groundspeed;
    private String transponder;

    private FlightPlan flightplan;
    private int icon;
    private MarkerOptions markerOptions;
    private Marker marker;

    public Pilot(String[] data, GoogleMap map) {
        super(data);

        try {
            this.altitude = Integer.parseInt(data[7]);
            this.heading = Integer.parseInt(data[38]);
            this.groundspeed = Integer.parseInt(data[8]);
        } catch (NumberFormatException ignore) {}
        this.transponder = data[17];
        this.flightplan = new FlightPlan(data);
        this.icon = DisplayData.getAircraftType(this.getFlightplan().getAircraft());
        this.markerOptions = new MarkerOptions()
                .position(this.getLocation())
                .icon(BitmapDescriptorFactory.fromResource(this.icon))
                .anchor(0.5f, 0.5f)
                .rotation(this.heading);
        this.marker = map.addMarker(this.markerOptions);
    }

    public int getFlownDistance() {
        double lat = flightplan.getDepairport_lat();
        double lon = flightplan.getDepairport_lon();

        LatLng origin = new LatLng(lat, lon);
        double distance = FlightCalc.getGCDistance(origin, this.location);

        return (int) distance;
    }

    public int getRemainingDistance() {
        double lat = flightplan.getDestairport_lat();
        double lon = flightplan.getDestairport_lon();

        LatLng destination = new LatLng(lat, lon);
        double distance = FlightCalc.getGCDistance(this.location, destination);

        return (int) distance;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getHeading() {
        return heading;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }

    public int getGroundspeed() {
        return groundspeed;
    }

    public void setGroundspeed(int groundspeed) {
        this.groundspeed = groundspeed;
    }

    public String getTransponder() {
        return transponder;
    }

    public void setTransponder(String transponder) {
        this.transponder = transponder;
    }

    public FlightPlan getFlightplan() {
        return flightplan;
    }

    public void setFlightplan(FlightPlan flightplan) {
        this.flightplan = flightplan;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public MarkerOptions getMarkerOptions() {
        return markerOptions;
    }

    public void setMarkerOptions(MarkerOptions markerOptions) {
        this.markerOptions = markerOptions;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

}
