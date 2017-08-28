package de.fraunhofer.polycare.models;

/**
 * Created by aw3s0 on 8/6/2017.
 * Model to represent the REST error
 */
public class RestErrorInfo {
    public final String detail;
    public final String message;

    public RestErrorInfo(Exception ex, String detail) {
        this.message = ex.getLocalizedMessage();
        this.detail = detail;
    }
}
