package com.example.mobilesecurity;

import android.content.Context;
import android.hardware.Camera;

public class CameraAccessChecker {
    public static String checkCameraAccess() {
        Camera camera = null;
        try {
            camera = Camera.open();
            if (camera != null) {
                return "Camera is available (not in use by other apps)";
            } else {
                return "Camera object is null – possibly in use or inaccessible";
            }
        } catch (RuntimeException e) {
            return "Camera is currently in use or blocked by another app";
        } finally {
            if (camera != null) {
                camera.release();
            }
        }
    }
}
