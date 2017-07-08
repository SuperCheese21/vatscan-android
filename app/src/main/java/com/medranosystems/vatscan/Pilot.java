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

}
