package com.example.mobilesecurity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class WifiCredsExtractor {
    public static String getSavedWifiCredentials() {
        StringBuilder result = new StringBuilder();
        File wifiConf = new File("/data/misc/wifi/wpa_supplicant.conf");

        if (!wifiConf.exists()) {
            return "Wi-Fi config file not found or inaccessible.\n";
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(wifiConf))) {
            String line;
            String ssid = "";
            String psk = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("ssid=")) {
                    ssid = line.replace("ssid=", "").replaceAll("\"", "");
                } else if (line.startsWith("psk=")) {
                    psk = line.replace("psk=", "").replaceAll("\"", "");
                } else if (line.startsWith("}")) {
                    if (!ssid.isEmpty()) {
                        result.append("SSID: ").append(ssid).append("\n");
                        result.append("Password: ").append(psk.isEmpty() ? "[OPEN]" : psk).append("\n\n");
                        ssid = "";
                        psk = "";
                    }
                }
            }
        } catch (Exception e) {
            result.append("Error reading Wi-Fi credentials: ").append(e.getMessage()).append("\n");
        }

        return result.toString();
    }
}
