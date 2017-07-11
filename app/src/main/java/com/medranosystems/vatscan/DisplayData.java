package com.medranosystems.vatscan;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by super on 7/4/2017.
 */

public class DisplayData {

    public static List<Client> clients;

    public static void updateData(String s, GoogleMap map) {
        String[] clientsRaw = (s.split("!CLIENTS:\n")[1]).split("\n;\n;\n!SERVERS:")[0].split("\n");
        clients = new ArrayList<>();

        map.clear();

        for (int i = 0; i < clientsRaw.length; i++) {
            String[] data = clientsRaw[i].split(":");

            if (Objects.equals(data[3], "PILOT")) {
                clients.add(new Pilot(data, map));
            } else if (Objects.equals(data[3], "ATC")) {
                clients.add(new Controller(data, map));
            }
        }
    }

    private static boolean updateClient(String[] data, GoogleMap map) {
        String callsign = data[0];

        for (Client client : clients) {
            if (Objects.equals(client.callsign, callsign)) {
                if (Objects.equals(client.clienttype, "PILOT")) {
                    Pilot p = (Pilot) client;
                } else if (Objects.equals(client.clienttype, "ATC")) {
                    Controller c = (Controller) client;
                }

                return true;
            }
        }

        return false;
    }

}
