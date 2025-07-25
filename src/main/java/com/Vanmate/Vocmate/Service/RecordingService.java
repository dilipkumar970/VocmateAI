package com.Vanmate.Vocmate.Service;

import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class RecordingService {

    // Folder to store all recordings
    private static final String RECORDING_SAVE_DIR = "recordings/";

    /**
     * Downloads the audio from Twilio's RecordingUrl
     * Generates a unique file name automatically
     * @param recordingUrl Twilio-provided URL for the audio
     * @return local file path of saved audio
     */
    public String downloadRecording(String recordingUrl) {
        try {
            // Create a unique file name with timestamp
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = "recording_" + timestamp + ".wav";
            String filePath = RECORDING_SAVE_DIR + fileName;

            // Create folder if not exists
            java.io.File dir = new java.io.File(RECORDING_SAVE_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Download file from URL
            URL url = new URL(recordingUrl + ".wav"); // Twilio provides base URL, add extension
            try (BufferedInputStream in = new BufferedInputStream(url.openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                byte dataBuffer[] = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            }

            System.out.println("✅ Recording saved at: " + filePath);
            return filePath;

        } catch (IOException e) {
            throw new RuntimeException(" Failed to download recording: " + e.getMessage(), e);
        }
    }
}
