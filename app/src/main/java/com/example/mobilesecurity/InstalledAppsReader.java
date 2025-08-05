package com.example.mobilesecurity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class InstalledAppsReader {
    public static String getInstalledApps(Context context) {
        StringBuilder appsBuilder = new StringBuilder();
        PackageManager pm = context.getPackageManager();

        try {
            List<PackageInfo> packages = pm.getInstalledPackages(0);

            for (PackageInfo pkg : packages) {
                ApplicationInfo appInfo = pkg.applicationInfo;
                boolean isSystemApp = (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;

                appsBuilder.append(isSystemApp ? "[SYSTEM] " : "[USER] ")
                        .append(pkg.packageName)
                        .append("\n");
            }

            if (packages.isEmpty()) {
                appsBuilder.append("No apps found.\n");
            }
        } catch (Exception e) {
            appsBuilder.append("Error retrieving installed apps: ").append(e.getMessage());
        }

        return appsBuilder.toString();
    }
}
