package com.example.mobilesecurity;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class ExternalStorageScanner {

    public static String scanForInterestingFiles(Context context) {
        StringBuilder output = new StringBuilder();

        File[] targets = new File[]{
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        };

        for (File dir : targets) {
            output.append("Scanning: ").append(dir.getAbsolutePath()).append("\n");

            if (dir != null && dir.exists() && dir.isDirectory()) {
                File[] files = dir.listFiles();
                if (files != null && files.length > 0) {
                    for (File file : files) {
                        output.append(" - ").append(file.getName()).append(" (")
                                .append(file.length() / 1024).append(" KB)\n");
                    }
                } else {
                    output.append("   [No files found]\n");
                }
            } else {
                output.append("   [Directory inaccessible or does not exist]\n");
            }
        }

        return output.toString();
    }
}
