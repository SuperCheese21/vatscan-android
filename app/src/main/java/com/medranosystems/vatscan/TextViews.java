package com.medranosystems.vatscan;

import android.app.Activity;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;

import java.util.Objects;

/**
 * Created by super on 8/12/2017.
 */

public class TextViews {

    private Activity activity;

    private TextView callsign;
    private TextView depairport;
    private TextView arrairport;
    private TextView name;
    private TextView id;

    public TextViews(Activity activity) {
        this.activity = activity;

        this.callsign = (TextView) this.activity.findViewById(R.id.flight_num);
        this.depairport = (TextView) this.activity.findViewById(R.id.dep_airport);
        this.arrairport = (TextView) this.activity.findViewById(R.id.arr_airport);
        this.name = (TextView) this.activity.findViewById(R.id.client_name);
        this.id = (TextView) this.activity.findViewById(R.id.client_id);
    }

    public void update(final Pilot pilot, Marker m) {
        final TextViews textViews = this;
        m.setAlpha(2.0f);

        final String depAirport, arrAirport, callsign, name, id;
        if (!Objects.equals(pilot.getFlightplan().getDepairport(), ""))
            depAirport = pilot.getFlightplan().getDepairport();
        else depAirport = "????";
        if (!Objects.equals(pilot.getFlightplan().getDestairport(), ""))
            arrAirport = pilot.getFlightplan().getDestairport();
        else arrAirport = "????";
        if (!Objects.equals(pilot.getCallsign(), ""))
            callsign = pilot.getCallsign();
        else callsign = "";
        if (!Objects.equals(pilot.getRealname(), ""))
            name = pilot.getRealname();
        else name = "";
        if (!Objects.equals(Integer.toString(pilot.getCid()), ""))
            id = Integer.toString(pilot.getCid());
        else id = "";

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViews.depairport.setText(depAirport);
                textViews.arrairport.setText(arrAirport);
                textViews.callsign.setText(callsign);
                textViews.name.setText(name);
                textViews.id.setText(id);
            }
        });
    }

    public void clear(DisplayData d) {
        final TextViews textViews = this;
        d.getActiveMarker().setAlpha(1.0f);
        d.setActiveMarker(null);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViews.callsign.setText("");
                textViews.depairport.setText("");
                textViews.arrairport.setText("");
                textViews.name.setText("");
                textViews.id.setText("");
            }
        });
    }

}
