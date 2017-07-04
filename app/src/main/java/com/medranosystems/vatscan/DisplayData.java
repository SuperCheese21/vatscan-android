package com.medranosystems.vatscan;

/**
 * Created by super on 7/4/2017.
 */

public class DisplayData {

    public static String[][] parseData(String data) {
        String clients = (data.split("!CLIENTS:\n")[1]).split("\n;\n;\n!SERVERS:")[0];
        String[] clientsRaw = clients.split("\n");
        String[][] clientsData = new String[20][20];

        for (int i = 0; i < clientsRaw.length; i++) {
            clientsData[i] = clientsRaw[i].split(":");
        }

        return clientsData;
    }


}
