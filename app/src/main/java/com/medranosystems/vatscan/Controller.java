package com.medranosystems.vatscan;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;

/**
 * Created by super on 7/8/2017.
 */

public class Controller extends Client {

    private float frequency;
    private int facilitytype;
    private int visualrange;

    private CircleOptions circleOptions;
    private Circle circle;

    public Controller(String[] data, GoogleMap map) {
        super(data);

        try {
            this.frequency = Float.parseFloat(data[4]);
            this.facilitytype = Integer.parseInt(data[18]);
            this.visualrange = Integer.parseInt(data[19]);
        } catch (NumberFormatException ignore) {}
    }

    public void removeMarker() {
        this.circle.remove();
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public int getFacilitytype() {
        return facilitytype;
    }

    public void setFacilitytype(int facilitytype) {
        this.facilitytype = facilitytype;
    }

    public int getVisualrange() {
        return visualrange;
    }

    public void setVisualrange(int visualrange) {
        this.visualrange = visualrange;
    }

}
