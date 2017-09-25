package org.researchsuite.rsuiteextensionscore;

/**
 * Created by jameskizer on 9/25/17.
 */

public class GeocodeException extends Exception {
    private String responseBody;

    public GeocodeException(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getResponseBody() {
        return responseBody;
    }
}