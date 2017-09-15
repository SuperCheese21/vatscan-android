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

    private FlightPlan flightplan;
    private int icon;
    private MarkerOptions markerOptions;
    private Marker marker;

    public Pilot(String[] clientData, GoogleMap map, MapData mapData) {
        super(clientData);

        try {
            this.altitude = Integer.parseInt(clientData[7]);
            this.heading = Integer.parseInt(clientData[38]);
            this.groundspeed = Integer.parseInt(clientData[8]);
        } catch (NumberFormatException ignore) {}
        this.flightplan = new FlightPlan(clientData, mapData);
        this.icon = DisplayData.getAircraftType(this.getFlightplan().getAircraft());
        this.marker = map.addMarker(new MarkerOptions()
                .position(this.getLocation())
                .icon(BitmapDescriptorFactory.fromResource(this.icon))
                .anchor(0.5f, 0.5f)
                .rotation(this.heading)
        );
    }

    public int getFlownDistance() {
        LatLng origin = flightplan.getDepairport_coords();
        double distance = FlightCalc.getGCDistance(origin, this.location);

        return (int) distance;
    }

    public int getRemainingDistance() {
        LatLng destination = flightplan.getDestairport_coords();
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

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

}
