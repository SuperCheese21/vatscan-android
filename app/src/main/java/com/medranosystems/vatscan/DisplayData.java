package com.medranosystems.vatscan;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by super on 7/4/2017.
 */

public class DisplayData extends MapsActivity {

    public static List<Client> clients;

    private static String[][] aircraftTypes = {
        { "b71", "b72", "b73", "b75", "a318", "a319", "a32", "cr", "e1", "md8", "md9" },
        { "b74", "b76", "b77", "b78", "a30", "a31", "a33", "a34", "a35", "a38", "il8", "il9" }
    };

    public static void updateData(String s, GoogleMap map) {
        String[] clientsRaw = (s.split("!CLIENTS:\n")[1]).split("\n;\n;\n!SERVERS:")[0].split("\n");
        clients = new ArrayList<>();

        map.clear();

        for (String c : clientsRaw) {
            String[] data = c.split(":");

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
            if (Objects.equals(client.getCallsign(), callsign)) {
                if (Objects.equals(client.getClienttype(), "PILOT")) {
                    Pilot p = (Pilot) client;
                } else if (Objects.equals(client.getClienttype(), "ATC")) {
                    Controller c = (Controller) client;
                }

                return true;
            }
        }

        return false;
    }

    public static int getAircraftType(String code) {
        String formattedCode = code.toLowerCase();

        for (int i = 0; i < aircraftTypes.length; i++) {
            for (int j = 0; j < aircraftTypes[i].length; j++) {
                if (formattedCode.contains(aircraftTypes[i][j])) {
                    if (i == 0) return R.drawable.narrowbody;
                    else if (i == 1) return R.drawable.widebody;
                }
            }
        }

        return R.drawable.ga;
    }

}
