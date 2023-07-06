package com.upn.chuquilin.practica_martes.entities;

public class LocationData {

    private static LocationData instance;
    private double latitude;
    private double longitude;

    private LocationData() {
    }

    public static LocationData getInstance() {
        if (instance == null) {
            instance = new LocationData();
        }
        return instance;
    }

    public void setCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
