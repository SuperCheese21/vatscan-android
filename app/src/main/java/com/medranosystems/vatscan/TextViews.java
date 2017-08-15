package com.medranosystems.vatscan;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;

import org.w3c.dom.Text;

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
    private TextView aircraft;
    private TextView altitude;
    private TextView heading;
    private TextView speed;

    private ImageView headingIcon;

    public TextViews(Activity activity) {
        this.activity = activity;

        this.callsign = (TextView) this.activity.findViewById(R.id.callsign);
        this.depairport = (TextView) this.activity.findViewById(R.id.dep_airport);
        this.arrairport = (TextView) this.activity.findViewById(R.id.arr_airport);
        this.name = (TextView) this.activity.findViewById(R.id.client_name);
        this.id = (TextView) this.activity.findViewById(R.id.client_id);
        this.aircraft = (TextView) this.activity.findViewById(R.id.aircraft);
        this.altitude = (TextView) this.activity.findViewById(R.id.altitude);
        this.heading = (TextView) this.activity.findViewById(R.id.heading);
        this.speed = (TextView) this.activity.findViewById(R.id.speed);

        this.headingIcon = (ImageView) this.activity.findViewById(R.id.heading_icon);
    }

    public void update(final Pilot pilot, Marker m) {
        final TextViews textViews = this;
        m.setAlpha(2.0f);

        final float headingFloat;
        final String depAirport, arrAirport, callsign, name, id, aircraft, altitude, heading, speed;
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
        if (!Objects.equals(pilot.getFlightplan().getAircraft(), ""))
            aircraft = pilot.getFlightplan().getAircraft();
        else aircraft = "N/A";
        if (!Objects.equals(Integer.toString(pilot.getAltitude()), ""))
            altitude = Integer.toString(pilot.getAltitude()) + " ft";
        else altitude = "";
        if (!Objects.equals(Integer.toString(pilot.getHeading()), ""))
            heading = Integer.toString(pilot.getHeading()) + "°";
        else heading = "";
        if (!Objects.equals(Integer.toString(pilot.getGroundspeed()), ""))
            speed = Integer.toString(pilot.getGroundspeed()) + " kts";
        else speed = "";

        headingFloat = pilot.getHeading();

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViews.depairport.setText(depAirport);
                textViews.arrairport.setText(arrAirport);
                textViews.callsign.setText(callsign);
                textViews.name.setText(name);
                textViews.id.setText(id);
                textViews.aircraft.setText(aircraft);
                textViews.altitude.setText(altitude);
                textViews.heading.setText(heading);
                textViews.speed.setText(speed);
                textViews.headingIcon.setRotation(headingFloat);
            }
        });
    }

    public void clear(DisplayData d) {
        final TextViews textViews = this;
        Marker m = d.getActiveMarker();

        if (m != null) {
            m.setAlpha(1.0f);
            d.setActiveMarker(null);
        }
        d.getSeekBar().setVisibility(View.INVISIBLE);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViews.depairport.setText("");
                textViews.arrairport.setText("");
                textViews.callsign.setText("");
                textViews.name.setText("");
                textViews.id.setText("");
                textViews.aircraft.setText("");
                textViews.altitude.setText("");
                textViews.heading.setText("");
                textViews.speed.setText("");

                textViews.headingIcon.setRotation(0);
            }
        });
    }

}
