package com.medranosystems.vatscan;

/**
 * Created by super on 7/8/2017.
 */

public class FlightPlan {

    public String aircraft;
    public int tascruise;
    public int altitude;
    public String flighttype;
    public String route;
    public String remarks;

    public String depairport;
    public String destairport;
    public double depairport_lat;
    public double depairport_lon;
    public double destairport_lat;
    public double destairport_lon;

    public int deptime;
    public int actdeptime;
    public int hrsenroute;
    public int minenroute;

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
            this.depairport_lat = Double.parseDouble(data[31]);
            this.depairport_lon = Double.parseDouble(data[32]);
            this.destairport_lat = Double.parseDouble(data[33]);
            this.destairport_lon = Double.parseDouble(data[34]);
            this.deptime = Integer.parseInt(data[22]);
            this.actdeptime = Integer.parseInt(data[23]);
            this.hrsenroute = Integer.parseInt(data[24]);
            this.minenroute = Integer.parseInt(data[25]);
        } catch (NumberFormatException ignore) {};
    }

}
