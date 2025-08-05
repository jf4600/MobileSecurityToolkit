package com.example.mobilesecurity;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

public class RunningServicesReader {

    public static String getRunningServices(Context context) {
        StringBuilder builder = new StringBuilder();

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager != null) {
            List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

            if (services.size() == 0) {
                builder.append("No running services found (or access is restricted).\n");
            } else {
                for (ActivityManager.RunningServiceInfo service : services) {
                    builder.append("Service: ").append(service.service.getClassName())
                            .append("\nPackage: ").append(service.service.getPackageName())
                            .append("\nProcess: ").append(service.process)
                            .append("\nPID: ").append(service.pid)
                            .append("\nUID: ").append(service.uid)
                            .append("\n---\n");
                }
            }
        } else {
            builder.append("Unable to access ActivityManager.\n");
        }

        return builder.toString();
    }
}
