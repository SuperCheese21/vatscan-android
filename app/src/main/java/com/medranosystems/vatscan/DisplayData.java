package com.medranosystems.vatscan;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by super on 7/4/2017.
 */

public class DisplayData {

    public static void displayPilots(String rawData, GoogleMap mMap) {
        System.out.println("displayPilots()");
        List<Pilot> pilots = parseData(rawData);
        addPilots(pilots, mMap);
    }

    public static List<Pilot> parseData(String s) {
        String[] clientsRaw = (s.split("!CLIENTS:\n")[1]).split("\n;\n;\n!SERVERS:")[0].split("\n");
        List<Pilot> pilots = new ArrayList<Pilot>();

        for (int i = 0; i < clientsRaw.length; i++) {
            String[] data = clientsRaw[i].split(":");

            if (Objects.equals(data[3], "PILOT")) {
                pilots.add(new Pilot(data));
            }
        }

        return pilots;
    }

    public static void addPilots(List<Pilot> pilots, GoogleMap map) {
        map.clear();

        for (Pilot p : pilots) {
            LatLng location = new LatLng(p.latitude, p.longitude);
            map.addMarker(new MarkerOptions()
                    .position(location)
                    .title(p.callsign)
                    .snippet(p.realname + "\n")
            );
        }
    }
}
