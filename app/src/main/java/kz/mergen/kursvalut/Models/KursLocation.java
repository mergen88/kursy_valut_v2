package kz.mergen.kursvalut.Models;

import android.location.Location;

import java.io.Serializable;

/**
 * Created by arman on 14.10.17.
 */

public class KursLocation implements Serializable{
    private double latitude;
    private double longitude;
    private String title;

    public KursLocation() {
    }
    public KursLocation(double latitude, double longitude, String title) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
    }
    public KursLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Location toLocation(){
        Location location = new Location(title);
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        return location;
    }
}
