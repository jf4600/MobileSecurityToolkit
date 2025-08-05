package com.example.mobilesecurity;

import android.media.MediaRecorder;

public class MicrophoneAccessCheck {
    public static String checkMicrophoneAccess() {
        MediaRecorder recorder = new MediaRecorder();
        try {
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile("/dev/null"); // No actual recording

            recorder.prepare();
            recorder.start();
            recorder.stop();
            recorder.release();

            return "Microphone access: ✅ Success";
        } catch (Exception e) {
            return "Microphone access: ❌ Failed or blocked\n" + e.getMessage();
        }
    }
}
