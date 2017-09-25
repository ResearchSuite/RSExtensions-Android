package org.researchsuite.rsuiteextensionscore;

/**
 * Created by Christina on 6/22/17.
 */

import org.researchstack.backbone.result.Result;
import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.Step;

public class LocationResult extends Result {

    private Double longitute;
    private Double latitude;
    private String userInput;
    private String address;
    private Boolean userPlaced;

    public LocationResult(String identifier) {
        super(identifier);
    }

    public Double getLongitute() {
        return longitute;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getUserInput() {
        return userInput;
    }

    public String getAddress() {
        return address;
    }

    public void setLongitute(Double longitute) {
        this.longitute = longitute;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getUserPlaced() {
        return userPlaced;
    }

    public void setUserPlaced(Boolean userPlaced) {
        this.userPlaced = userPlaced;
    }
}
