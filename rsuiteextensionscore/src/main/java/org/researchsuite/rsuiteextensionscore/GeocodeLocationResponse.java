package org.researchsuite.rsuiteextensionscore;

/**
 * Created by jameskizer on 9/25/17.
 */

public class GeocodeLocationResponse {

    final String formattedAddress;
    final double latitude;
    final double longitude;

    public GeocodeLocationResponse(String formattedAddress, double latitude, double longitude) {
        this.formattedAddress = formattedAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
