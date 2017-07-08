package com.medranosystems.vatscan;

import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by super on 7/8/2017.
 */

public class Pilot extends Client {

    public int altitude;
    public int heading;
    public int groundspeed;
    public String transponder;

    public FlightPlan mFlightPlan;
    public MarkerOptions mMarkerOptions;

    public Pilot(String[] data) {
        super(data);

        try {
            this.altitude = Integer.parseInt(data[7]);
            this.heading = Integer.parseInt(data[38]);
            this.groundspeed = Integer.parseInt(data[8]);
        } catch (NumberFormatException ignore) {};

        this.transponder = data[17];

        this.mFlightPlan = new FlightPlan(data);
        this.mMarkerOptions = new MarkerOptions();
    }

}
