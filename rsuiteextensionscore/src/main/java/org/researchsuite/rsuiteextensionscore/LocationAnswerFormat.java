package org.researchsuite.rsuiteextensionscore;

import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.os.ParcelableCompat;

import com.google.android.gms.maps.model.LatLng;

import org.researchstack.backbone.answerformat.AnswerFormat;

import java.io.Serializable;

/**
 * Created by jameskizer on 9/24/17.
 */

public class LocationAnswerFormat extends AnswerFormat implements Serializable {
    private boolean mUseCurrentLocation = true;
    private double mDefaultLatitude;
    private double mDefaultLongitude;
    public boolean useCurrentLocation() {
        return mUseCurrentLocation;
    }

    public void setUseCurrentLocation(boolean mUseCurrentLocation) {
        this.mUseCurrentLocation = mUseCurrentLocation;
    }

    public LocationAnswerFormat() {
        super();
        //CT Campus
        this.mDefaultLatitude = 40.756032;
        this.mDefaultLongitude = -73.955938;
    }

    public LocationAnswerFormat(double defaultLatitude, double defaultLongitude) {
        super();
        this.mDefaultLatitude = defaultLatitude;
        this.mDefaultLongitude = defaultLongitude;
    }

    public Location getDefaultLocation() {
        Location location = new Location("DefaultLocation");
        location.setLatitude(this.mDefaultLatitude);
        location.setLongitude(this.mDefaultLongitude);
        return location;
    }

    public void setmDefaultLatitude(double mDefaultLatitude) {
        this.mDefaultLatitude = mDefaultLatitude;
    }

    public void setmDefaultLongitude(double mDefaultLongitude) {
        this.mDefaultLongitude = mDefaultLongitude;
    }
}
