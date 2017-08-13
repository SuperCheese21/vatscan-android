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
        System.out.println("Initializing TextViews");
        this.activity = activity;

        this.callsign = (TextView) activity.findViewById(R.id.flight_num);
        this.depairport = (TextView) activity.findViewById(R.id.dep_airport);
        this.arrairport = (TextView) activity.findViewById(R.id.arr_airport);
        this.name = (TextView) activity.findViewById(R.id.client_name);
        this.id = (TextView) activity.findViewById(R.id.client_id);
    }

    public void updateTest(final String callsign, final String dep, final String arr, final String name, final String cid) {
        final TextViews t = this;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                t.callsign.setText(callsign);
                t.depairport.setText(dep);
                t.arrairport.setText(arr);
                t.name.setText(name);
                t.id.setText(cid);
            }
        });
    }

    public void update(final Pilot pilot) {
        System.out.println("Updating UI");
        final TextViews t = this;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                t.callsign.setText(pilot.getCallsign());
                t.depairport.setText(pilot.getFlightplan().getDepairport());
                t.arrairport.setText(pilot.getFlightplan().getDestairport());
                t.name.setText(pilot.getRealname());
                t.id.setText(Integer.toString(pilot.getCid()));
            }
        });
    }

}
