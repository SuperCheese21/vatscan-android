package com.medranosystems.vatscan;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by super on 7/8/2017.
 */

public class Client {

    protected String callsign;
    protected int cid;
    protected String realname;
    protected String clienttype;
    protected int rating;

    protected double latitude;
    protected double longitude;
    protected LatLng location;

    public Client(String[] data) {
        this.callsign = data[0];
        this.realname = data[2];
        this.clienttype = data[3];

        try {
            this.cid = Integer.parseInt(data[1]);
            this.rating = Integer.parseInt(data[16]);
            this.latitude = Double.parseDouble(data[5]);
            this.longitude = Double.parseDouble(data[6]);
        } catch (NumberFormatException ignored) {}

        this.location = new LatLng(this.latitude, this.longitude);
    }

    public String getCallsign() {
        return this.callsign;
    }

    public void setCallsign(String c) {
        this.callsign = c;
    }

    public int getCid() {
        return this.cid;
    }

    public void setCid(int i) {
        this.cid = i;
    }

    public String getRealname() {
        return this.realname;
    }

    public void setRealname(String n) {
        this.realname = n;
    }

    public String getClienttype() {
        return this.clienttype;
    }

    public void setClienttype(String t) {
        this.clienttype = t;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int r) {
        this.rating = r;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double l) {
        this.latitude = l;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double l) {
        this.longitude = l;
    }

    public LatLng getLocation() {
        return this.location;
    }

    public void setLocation(LatLng l) {
        this.location = l;
    }

}
