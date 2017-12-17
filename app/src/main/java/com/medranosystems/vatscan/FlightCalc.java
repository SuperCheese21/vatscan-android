package com.medranosystems.vatscan;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by super on 8/15/2017.
 */

public class FlightCalc {

    // Radius of the earth in nautical miles
    public static double R = 3440.06;

    /**
     * Calculates the great circle distance between two points
     *
     * @param loc1  First point
     * @param loc2  Second point
     * @return      Distance in nautical miles
     */
    public static double getGCDistance(LatLng loc1, LatLng loc2) {
        double lat1 = Math.toRadians(loc1.latitude);
        double lat2 = Math.toRadians(loc2.latitude);

        double deltaLat = Math.toRadians(loc2.latitude - loc1.latitude);
        double deltaLon = Math.toRadians(loc2.longitude - loc1.longitude);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

}
