package com.medranosystems.vatscan;

/**
 * Created by super on 7/8/2017.
 */

public class Controller extends Client {

    public float frequency;
    public int facilitytype;
    public int visualrange;

    public Controller(String[] data) {
        super(data);

        try {
            this.frequency = Float.parseFloat(data[4]);
            this.facilitytype = Integer.parseInt(data[18]);
            this.visualrange = Integer.parseInt(data[19]);
        } catch (NumberFormatException ignore) {};
    }

}
