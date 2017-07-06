package com.medranosystems.vatscan;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by super on 7/4/2017.
 */

public class DisplayData {

    public static String[][] parseData(String data) {
        System.out.println("parseData()");

        String clients = (data.split("!CLIENTS:\n")[1]).split("\n;\n;\n!SERVERS:")[0];
        String[] clientsRaw = clients.split("\n");
        String[][] clientsData = new String[clientsRaw.length][];

        for (int i = 0; i < clientsRaw.length; i++) {
            clientsData[i] = clientsRaw[i].split(":");
        }

        return clientsData;
    }

    public static void addMarkers(String[][] data, GoogleMap mMap) {
        for (String[] s : data) {
            if (s[5] != "" && s[6] != "") {
                String callsign = s[0];

                double lat = Double.parseDouble(s[5]);
                double lon = Double.parseDouble(s[6]);

                //System.out.println("Callsign: " + callsign + ", lat: " + lat + ", lon: " + lon);

                LatLng location = new LatLng(lat, lon);
                mMap.addMarker(new MarkerOptions().position(location).title(callsign));
            }
        }
    }
}
