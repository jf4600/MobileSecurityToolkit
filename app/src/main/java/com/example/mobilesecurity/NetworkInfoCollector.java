package com.example.mobilesecurity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class NetworkInfoCollector {

    public static String getNetworkDetails(Context context) {
        StringBuilder info = new StringBuilder();

        // WiFi SSID
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ssid = wifiInfo.getSSID();
                info.append("WiFi SSID: ").append(ssid).append("\n");
            }
        } catch (Exception e) {
            info.append("WiFi Info: ").append(e.getMessage()).append("\n");
        }

        // Carrier
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                String carrierName = tm.getNetworkOperatorName();
                info.append("Carrier: ").append(carrierName).append("\n");
            }
        } catch (Exception e) {
            info.append("Carrier Info: ").append(e.getMessage()).append("\n");
        }

        // IP Address
        try {
            String ip = getIPAddress();
            info.append("IP Address: ").append(ip).append("\n");
        } catch (Exception e) {
            info.append("IP Address: ").append(e.getMessage()).append("\n");
        }

        return info.toString();
    }

    private static String getIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : interfaces) {
                List<InetAddress> addresses = Collections.list(ni.getInetAddresses());
                for (InetAddress address : addresses) {
                    if (!address.isLoopbackAddress() && address.getHostAddress().indexOf(':') < 0) {
                        return address.getHostAddress();
                    }
                }
            }
        } catch (Exception ignored) {}
        return "Unavailable";
    }
}
