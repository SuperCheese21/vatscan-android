package com.medranosystems.vatscan;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

/**
 * Created by super on 7/4/2017.
 */

public class DisplayData {

    public static Client[] parseData(String s) {
        String[] clientsRaw = (s.split("!CLIENTS:\n")[1]).split("\n;\n;\n!SERVERS:")[0].split("\n");
        Client[] clients = new Client[clientsRaw.length];

        for (int i = 0; i < clientsRaw.length; i++) {
            String[] data = clientsRaw[i].split(":");

            if (Objects.equals(data[3], "PILOT")) {
                clients[i] = new Pilot(data);
            } else if (Objects.equals(data[3], "ATC")) {
                clients[i] = new Controller(data);
            }
        }

        return clients;
    }

    public static void addMarkers(Client[] clients, GoogleMap map) {
        map.clear();

        for (Client c : clients) {
            LatLng location = new LatLng(c.latitude, c.longitude);
            map.addMarker(new MarkerOptions().position(location).title(c.callsign));
        }
    }
}
