package org.researchsuite.rsuiteextensionscore;

/**
 * Created by Christina on 6/22/17.
 */

import org.researchstack.backbone.result.Result;

public class LocationStepResult extends Result {

    private String locationResult;
    private Double longitute;
    private Double latitude;

    public LocationStepResult(String identifier) {
        super(identifier);
    }


    public void setLocationResult(String result) {
        this.locationResult = result;
    }
    public String getLocationResult() {
        return this.locationResult;
    }

    public void setLongLat(Double longitute,Double latitude){
        this.longitute = longitute;
        this.latitude = latitude;
    }

    public Double getLongitute() {
        return this.longitute;
    }

    public Double getLatitude() {
        return this.latitude;
    }
}
