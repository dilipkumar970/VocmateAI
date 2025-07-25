package com.Vanmate.Vocmate.Service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Service responsible for interacting with whisper.cpp to transcribe audio.
 */
@Service
public class WhisperService {

    //  Path variable for main executable file
    private static final String WHISPER_EXEC_PATH =
            "C:\\web dev\\whisper.cpp\\build\\bin\\Release\\whisper-cli.exe";

    //  Path variable for model
    private static final String WHISPER_MODEL_PATH =
            "C:\\web dev\\whisper.cpp\\models\\ggml-small.bin";


    public String transcribeAudio(String audioFilePath) {
        StringBuilder outputText = new StringBuilder();

        try {

            ProcessBuilder pb = new ProcessBuilder(
                    WHISPER_EXEC_PATH,
                    "-m", WHISPER_MODEL_PATH,
                    "-f", audioFilePath
            );


            Process process = pb.start();

            // Read output stream
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[Whisper Output] " + line);
                    outputText.append(line).append("\n");
                }
            }

            // Wait for process to complete
            int exitCode = process.waitFor();
            System.out.println(" Whisper process finished with exit code: " + exitCode);

        } catch (IOException e) {
            System.err.println(" IO ERROR WHILE RUNNING WHISPER: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println(" Whisper process was interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        //  Return transcription
        return outputText.toString().trim();
    }
}
