package com.example.mobilesecurity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import java.util.List;

public class SensorAccessChecker {
    public static String checkSensors(Context context) {
        StringBuilder result = new StringBuilder();
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager == null) {
            return "SensorManager not available.";
        }

        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        if (sensors.isEmpty()) {
            return "No sensors detected.";
        }

        result.append("Detected Sensors:\n");
        for (Sensor sensor : sensors) {
            result.append("- ").append(sensor.getName()).append(" (").append(sensor.getType()).append(")\n");
        }

        return result.toString();
    }
}
