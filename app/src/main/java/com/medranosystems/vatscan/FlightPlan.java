package com.medranosystems.vatscan;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by super on 7/8/2017.
 */

public class FlightPlan {

    private String aircraft;
    private int tascruise;
    private int altitude;
    private String flighttype;
    private String route;

    private String depairport;
    private String destairport;
    private LatLng depairport_coords;
    private LatLng destairport_coords;

    private int deptime;
    private int actdeptime;
    private int hrsenroute;
    private int minenroute;

    public FlightPlan(String[] data, MapData mapData) {
        this.aircraft = data[9];
        this.flighttype = data[22];
        this.route = data[30];
        this.depairport = data[11];
        this.destairport = data[13];

        try {
            this.tascruise = Integer.parseInt(data[10]);
            this.altitude = Integer.parseInt(data[12]);
            this.deptime = Integer.parseInt(data[22]);
            this.actdeptime = Integer.parseInt(data[23]);
            this.hrsenroute = Integer.parseInt(data[24]);
            this.minenroute = Integer.parseInt(data[25]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        this.depairport_coords = mapData.getAirportCoords(this.depairport);
        this.destairport_coords = mapData.getAirportCoords(this.destairport);
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public int getTascruise() {
        return tascruise;
    }

    public void setTascruise(int tascruise) {
        this.tascruise = tascruise;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public String getFlighttype() {
        return flighttype;
    }

    public void setFlighttype(String flighttype) {
        this.flighttype = flighttype;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDepairport() {
        return depairport;
    }

    public void setDepairport(String depairport) {
        this.depairport = depairport;
    }

    public String getDestairport() {
        return destairport;
    }

    public void setDestairport(String destairport) {
        this.destairport = destairport;
    }

    public int getDeptime() {
        return deptime;
    }

    public void setDeptime(int deptime) {
        this.deptime = deptime;
    }

    public int getActdeptime() {
        return actdeptime;
    }

    public void setActdeptime(int actdeptime) {
        this.actdeptime = actdeptime;
    }

    public int getHrsenroute() {
        return hrsenroute;
    }

    public void setHrsenroute(int hrsenroute) {
        this.hrsenroute = hrsenroute;
    }

    public int getMinenroute() {
        return minenroute;
    }

    public void setMinenroute(int minenroute) {
        this.minenroute = minenroute;
    }

    public LatLng getDepairport_coords() {
        return depairport_coords;
    }

    public void setDepairport_coords(LatLng depairport_coords) {
        this.depairport_coords = depairport_coords;
    }

    public LatLng getDestairport_coords() {
        return destairport_coords;
    }

    public void setDestairport_coords(LatLng destairport_coords) {
        this.destairport_coords = destairport_coords;
    }
}
