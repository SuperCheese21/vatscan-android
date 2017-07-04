package com.medranosystems.vatscan;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by super on 7/4/2017.
 */

public class PullData {
    public static void main(String[] args) {
        String data = getData("http://data.vattastic.com/vatsim-data.txt");
        //System.out.println(data);
        parseData(data);
    }

    private static void parseData(String data) {
        String clients = (data.split("!CLIENTS:\n")[1]).split("\n;\n;\n!SERVERS:")[0];
        String[] clientsLabels = ((data.split("!CLIENTS section -         ")[1]).split(":\n; !PREFILE section -")[0]).split(":");
        String[] clientsRaw = clients.split("\n");
        String[][] clientsData = new String[clientsRaw.length][clientsLabels.length];

        for (int i = 0; i < clientsRaw.length; i++) {
            System.out.println(clientsRaw[i]);
            clientsData[i] = clientsRaw[i].split(":");
        }
    }

    private static String getData(String s) {
        StringBuilder contents = new StringBuilder();

        try {
            URL url = new URL(s);
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                contents.append(line + "\n");
            }
            reader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return contents.toString();
    }
}
