package com.example.mobilesecurity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.PackageManager;
import android.Manifest;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import android.net.Uri;
import android.content.Intent;
import android.widget.Button;
import androidx.core.content.FileProvider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView resultText = findViewById(R.id.result_text);
        StringBuilder resultBuilder = new StringBuilder();

        // Emulator Check
        boolean isEmulator = EmulatorCheck.isEmulator();
        resultBuilder.append(isEmulator ? "Running in Emulator\n" : "Running on Real Device\n");

        // Root Check
        boolean rooted = RootCheck.isDeviceRooted();
        resultBuilder.append(rooted ? "Root Detected\n" : "Device is NOT Rooted\n");

        // Request SMS permission at runtime
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    1);
        }

        // Request Location permissions at runtime
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    2);
        }

        // Request Microphone permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    103);
        }

        // Request Contacts permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    104);
        }

        // Clipboard contents
        String clipboardText = ClipboardReader.getClipboardText(this);
        resultBuilder.append("\n--- Clipboard Contents ---\n");
        if (clipboardText.length() > 500) {
            resultBuilder.append(clipboardText.substring(0, 500)).append("...\n[Truncated]");
        } else {
            resultBuilder.append(clipboardText).append("\n");
        }

        // SMS Sent Log
        String smsLog = SmsSentReader.getSentSms(this);
        resultBuilder.append("\n--- SMS Sent Log ---\n").append(smsLog).append("\n");

        // Installed apps list
        String apps = InstalledAppsReader.getInstalledApps(this);
        resultBuilder.append("\n--- Installed Apps ---\n").append(apps);

        // Location services status
        String locationStatus = LocationStatusCheck.checkLocationStatus(this);
        resultBuilder.append("\n").append(locationStatus);

        // GPS Coordinates
        String gpsCoords = LocationCoordinatesFetcher.getLastKnownLocation(this);
        resultBuilder.append("\n--- GPS Coordinates ---\n").append(gpsCoords).append("\n");

        // Network Info (Wi-Fi SSID + Carrier)
        String networkInfo = NetworkInfoCollector.getNetworkDetails(this);
        resultBuilder.append("\n--- Network Info ---\n").append(networkInfo);

        // Camera Access Check
        String cameraStatus = CameraAccessChecker.checkCameraAccess();
        resultBuilder.append("\n--- Camera Status ---\n").append(cameraStatus);

        // Microphone Access Check (after permission granted)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            String micResult = MicrophoneAccessCheck.checkMicrophoneAccess();
            resultBuilder.append("\n--- Microphone Access ---\n").append(micResult).append("\n");
        }

        // Running Services
        String services = RunningServicesReader.getRunningServices(this);
        resultBuilder.append("\n--- Running Services ---\n").append(services);

        // Wi-Fi saved credentials (rooted devices only)
        String wifiCreds = WifiCredsExtractor.getSavedWifiCredentials();
        resultBuilder.append("\n--- Saved Wi-Fi Credentials ---\n").append(wifiCreds);

        // Contacts extraction
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            String contacts = ContactsReader.getContacts(this);
            resultBuilder.append("\n--- Contacts ---\n").append(contacts);
        }

        // Browser History
        String browserHistory = BrowserHistoryReader.getBrowserHistory(this);
        resultBuilder.append("\n--- Browser History ---\n").append(browserHistory);

        // External Storage Scan
        String fileScan = ExternalStorageScanner.scanForInterestingFiles(this);
        resultBuilder.append("\n--- External Storage Scan ---\n").append(fileScan);

        // Account Info Dump
        String accountDump = AccountInfoDumper.dumpAccounts(this);
        resultBuilder.append("\n--- Registered Accounts ---\n").append(accountDump);

        // Sensor Access
        String sensorInfo = SensorAccessChecker.checkSensors(this);
        resultBuilder.append("\n--- Sensor Info ---\n").append(sensorInfo);

        // Save to TXT file
        String filename = "SecurityReport.txt";
        String reportContent = resultBuilder.toString();
        try {
            FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE);
            fos.write(reportContent.getBytes());
            fos.close();
            resultBuilder.append("\n\n[+] Report saved to internal storage as: ").append(filename);
        } catch (IOException e) {
            resultBuilder.append("\n\n[-] Failed to save report: ").append(e.getMessage());
        }

        // Save report to file for sharing
        String fileName = "security_report.txt";
        // reportContent is already defined above
        File reportFile = new File(getExternalFilesDir(null), fileName);


        try {
            FileOutputStream fos = new FileOutputStream(reportFile);
            fos.write(reportContent.getBytes());
            fos.close();
            resultBuilder.append("\n\n[+] Report saved to: ").append(reportFile.getAbsolutePath());
        } catch (IOException e) {
            resultBuilder.append("\n\n[-] Failed to save report: ").append(e.getMessage());
        }

        // Share report via button
        Button shareButton = findViewById(R.id.share_button);
        shareButton.setOnClickListener(v -> {
            Uri fileUri = FileProvider.getUriForFile(
                    MainActivity.this,
                    getApplicationContext().getPackageName() + ".provider",
                    reportFile
            );

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share report via"));
        });


        // Output all results to the screen
        resultText.setText(resultBuilder.toString());
    }

    // Handle permission responses
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if ((requestCode == 1 || requestCode == 2 || requestCode == 103 || requestCode == 104)
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            recreate(); // Restart activity to re-run granted checks

        }
    }
}
