package com.example.mobilesecurity;

import android.os.Build;

public class EmulatorCheck {
    public static boolean isEmulator() {
        String product = Build.PRODUCT;
        String manufacturer = Build.MANUFACTURER;
        String brand = Build.BRAND;
        String device = Build.DEVICE;
        String model = Build.MODEL;
        String hardware = Build.HARDWARE;
        String fingerprint = Build.FINGERPRINT;

        return (fingerprint != null && fingerprint.contains("generic"))
                || (model != null && model.contains("Emulator"))
                || (product != null && product.contains("sdk"))
                || (hardware != null && hardware.contains("goldfish"))
                || (brand != null && brand.contains("generic"))
                || (device != null && device.contains("generic"))
                || (manufacturer != null && manufacturer.contains("Genymotion"));
    }
}
