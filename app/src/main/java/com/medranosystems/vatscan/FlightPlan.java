package com.medranosystems.vatscan;

/**
 * Created by super on 7/8/2017.
 */

public class FlightPlan {

    private String aircraft;
    private int tascruise;
    private int altitude;
    private String flighttype;
    private String route;
    private String remarks;

    private String depairport;
    private String destairport;
    private double depairport_lat;
    private double depairport_lon;
    private double destairport_lat;
    private double destairport_lon;

    private int deptime;
    private int actdeptime;
    private int hrsenroute;
    private int minenroute;

    public FlightPlan(String[] data) {
        this.aircraft = data[9];
        this.flighttype = data[22];
        this.route = data[30];
        this.remarks = data[29];
        this.depairport = data[11];
        this.destairport = data[13];

        try {
            this.tascruise = Integer.parseInt(data[10]);
            this.altitude = Integer.parseInt(data[12]);
            this.deptime = Integer.parseInt(data[22]);
            this.actdeptime = Integer.parseInt(data[23]);
            this.hrsenroute = Integer.parseInt(data[24]);
            this.minenroute = Integer.parseInt(data[25]);
            this.depairport_lat = Double.parseDouble(data[31]);
            this.depairport_lon = Double.parseDouble(data[32]);
            this.destairport_lat = Double.parseDouble(data[33]);
            this.destairport_lon = Double.parseDouble(data[34]);
        } catch (NumberFormatException ignore) {}
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public double getDepairport_lat() {
        return depairport_lat;
    }

    public void setDepairport_lat(double depairport_lat) {
        this.depairport_lat = depairport_lat;
    }

    public double getDepairport_lon() {
        return depairport_lon;
    }

    public void setDepairport_lon(double depairport_lon) {
        this.depairport_lon = depairport_lon;
    }

    public double getDestairport_lat() {
        return destairport_lat;
    }

    public void setDestairport_lat(double destairport_lat) {
        this.destairport_lat = destairport_lat;
    }

    public double getDestairport_lon() {
        return destairport_lon;
    }

    public void setDestairport_lon(double destairport_lon) {
        this.destairport_lon = destairport_lon;
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
}
