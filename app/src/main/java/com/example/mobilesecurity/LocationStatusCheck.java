package com.example.mobilesecurity;

import android.content.Context;
import android.location.LocationManager;

public class LocationStatusCheck {
    public static String checkLocationStatus(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        try {
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ignored) {}

        try {
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ignored) {}

        StringBuilder status = new StringBuilder("--- Location Status ---\n");
        status.append("GPS Provider: ").append(gpsEnabled ? "ENABLED" : "DISABLED").append("\n");
        status.append("Network Provider: ").append(networkEnabled ? "ENABLED" : "DISABLED").append("\n");

        return status.toString();
    }
}
