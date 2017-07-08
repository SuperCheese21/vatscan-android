package com.medranosystems.vatscan;

/**
 * Created by super on 7/8/2017.
 */

public class Client {

    public String callsign;
    public int cid;
    public String realname;
    public String clienttype;
    public int rating;

    public double latitude;
    public double longitude;

    public Client(String[] data) {
        this.callsign = data[0];
        this.realname = data[2];
        this.clienttype = data[3];

        try {
            this.cid = Integer.parseInt(data[1]);
            this.rating = Integer.parseInt(data[16]);
            this.latitude = Double.parseDouble(data[5]);
            this.longitude = Double.parseDouble(data[6]);
        } catch (NumberFormatException ignored){};
    }

}
