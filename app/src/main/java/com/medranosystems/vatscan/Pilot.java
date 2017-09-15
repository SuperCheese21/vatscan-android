package com.medranosystems.vatscan;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
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
        } catch (NumberFormatException ignore) {}
        this.flightplan = new FlightPlan(clientData, mapData);
        this.lineFlown = new PolylineOptions()
            .add(this.flightplan.getDepairport_coords(), this.getLocation())
            .width(3)
            .color(Color.GREEN)
            .geodesic(true);
        this.lineRemaining = new PolylineOptions()
            .add(this.getLocation(), this.flightplan.getDestairport_coords())
            .width(3)
            .color(Color.RED)
            .geodesic(true);
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

    public int getFlightProgress() {
        int distanceFlown = this.getFlownDistance();
        int distanceRemaining = this.getRemainingDistance();

        double progress = (double) distanceFlown / (distanceFlown + distanceRemaining);
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
