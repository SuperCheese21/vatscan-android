package com.medranosystems.vatscan;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by super on 7/4/2017.
 */

public class DisplayData {

    public static void parseClients(String s, GoogleMap map) {
        String[] clientsRaw = (s.split("!CLIENTS:\n")[1]).split("\n;\n;\n!SERVERS:")[0].split("\n");
        List<Pilot> pilots = new ArrayList<>();
        List<Controller> controllers = new ArrayList<>();

        for (int i = 0; i < clientsRaw.length; i++) {
            String[] data = clientsRaw[i].split(":");

            if (Objects.equals(data[3], "PILOT")) {
                pilots.add(new Pilot(data));
            } else if (Objects.equals(data[3], "ATC")) {
                controllers.add(new Controller(data));
            }
        }

        displayClients(pilots, controllers, map);
    }

    private static void displayClients(List<Pilot> pilots, List<Controller> controllers, GoogleMap map) {
        map.clear();
        for (Pilot p : pilots) {
            LatLng location = new LatLng(p.latitude, p.longitude);
            map.addMarker(new MarkerOptions()
                    .position(location)
                    .title(p.callsign)
                    .snippet(p.realname)
            );
        }

        for (Controller c : controllers) {
            LatLng location = new LatLng(c.latitude, c.longitude);
            int radius = c.visualrange;

            map.addMarker(new MarkerOptions()
                .position(location)
                .title(c.callsign)
                .snippet(c.realname)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            );
            map.addCircle(new CircleOptions()
                .center(location)
                .radius(radius * 185.2)
                .strokeColor(Color.BLUE)
                .strokeWidth(3)
                .fillColor(Color.argb(40, 0, 0, 255))
            );
        }
    }

}
