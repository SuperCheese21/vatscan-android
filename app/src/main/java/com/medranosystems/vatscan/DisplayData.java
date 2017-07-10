package com.medranosystems.vatscan;

import com.google.android.gms.maps.GoogleMap;

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
                pilots.add(new Pilot(data, map));
            } else if (Objects.equals(data[3], "ATC")) {
                controllers.add(new Controller(data, map));
            }
        }
    }

}
