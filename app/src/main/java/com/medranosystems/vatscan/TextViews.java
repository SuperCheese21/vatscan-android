package com.medranosystems.vatscan;

import android.app.Activity;
import android.widget.TextView;

/**
 * Created by super on 8/12/2017.
 */

public class TextViews {

    public Activity activity;

    public TextView callsign;
    public TextView depairport;
    public TextView arrairport;
    public TextView name;
    public TextView id;

    public TextViews(Activity activity) {
        this.activity = activity;

        this.callsign = (TextView) this.activity.findViewById(R.id.flight_num);
        this.depairport = (TextView) this.activity.findViewById(R.id.dep_airport);
        this.arrairport = (TextView) this.activity.findViewById(R.id.arr_airport);
        this.name = (TextView) this.activity.findViewById(R.id.client_name);
        this.id = (TextView) this.activity.findViewById(R.id.client_id);
    }

    public void update(final Pilot pilot) {
        final TextViews textViews = this;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViews.callsign.setText(pilot.getCallsign());
                textViews.depairport.setText(pilot.getFlightplan().getDepairport());
                textViews.arrairport.setText(pilot.getFlightplan().getDestairport());
                textViews.name.setText(pilot.getRealname());
                textViews.id.setText(Integer.toString(pilot.getCid()));
            }
        });
    }

}
