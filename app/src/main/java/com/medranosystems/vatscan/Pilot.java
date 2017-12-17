package com.medranosystems.vatscan;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by super on 7/8/2017.
 */

public class Pilot extends Client {

    private int altitude;
    private int heading;
    private int groundspeed;

    private FlightPlan flightplan;
    private PolylineOptions lineFlown;
    private PolylineOptions lineRemaining;
    private int icon;
    private Marker marker;

    public Pilot(String[] clientData, GoogleMap map, MapData mapData) {
        super(clientData);

        try {
            this.altitude = Integer.parseInt(clientData[7]);
            this.heading = Integer.parseInt(clientData[38]);
            this.groundspeed = Integer.parseInt(clientData[8]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        this.flightplan = new FlightPlan(clientData, mapData);
        this.icon = DisplayData.getAircraftType(this.getFlightplan().getAircraft());
        this.lineFlown = addFlightPath(this.flightplan.getDepairport_coords(), this.location, Color.GREEN);
        this.lineRemaining = addFlightPath(this.location, this.flightplan.getDestairport_coords(), Color.RED);
        this.marker = addMarker(map);
    }

    private Marker addMarker(GoogleMap map) {
        return map.addMarker(new MarkerOptions()
                .position(this.location)
                .icon(BitmapDescriptorFactory.fromResource(this.icon))
                .anchor(0.5f, 0.5f)
                .rotation(this.heading)
        );
    }

    private PolylineOptions addFlightPath(LatLng start, LatLng end, int color) {
        return new PolylineOptions()
            .add(start, end)
            .width(3)
            .color(color)
            .geodesic(true);
    }

    public double getDistFlown() {
        LatLng lat1 = flightplan.getDepairport_coords();
        LatLng lat2 = this.location;

        return FlightCalc.getGCDistance(lat1, lat2);
    }

    public double getDistRemaining() {
        LatLng lat1 = this.location;
        LatLng lat2 = flightplan.getDestairport_coords();

        return FlightCalc.getGCDistance(lat1, lat2);
    }

    public int getFlightProgress() {
        double distFlown = getDistFlown();
        double distRemaining = getDistRemaining();

        double progress = distFlown / (distFlown + distRemaining);
        System.out.println(progress);

        return (int) (100 * progress);
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

    public PolylineOptions getLineFlown() {
        return lineFlown;
    }

    public void setLineFlown(PolylineOptions lineFlown) {
        this.lineFlown = lineFlown;
    }

    public PolylineOptions getLineRemaining() {
        return lineRemaining;
    }

    public void setLineRemaining(PolylineOptions lineRemaining) {
        this.lineRemaining = lineRemaining;
    }

}
