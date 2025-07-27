/**
 * Service responsible for interacting with whisper.cpp to transcribe audio.
 */
package com.Vanmate.Vocmate.Service;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WhisperService {

    private static final String WHISPER_EXEC_PATH = "C:\\web dev\\whisper.cpp\\build\\bin\\Release\\whisper-cli.exe";
    private static final String WHISPER_MODEL_PATH = "C:\\web dev\\whisper.cpp\\models\\ggml-small.bin";

    // Class to hold both transcription and detected language
    public static class WhisperResult {
        public String text;
        public String detectedLanguage;
    }

    public WhisperResult transcribeAudio(String audioFilePath) {
        //  Custom result object to hold detected language and text
        WhisperResult result = new WhisperResult();

        //  Builder to collect transcription lines
        StringBuilder textBuilder = new StringBuilder();

        try {
            //  Build the process to run whisper-cli.exe
            ProcessBuilder pb = new ProcessBuilder(
                    WHISPER_EXEC_PATH,
                    "-m", WHISPER_MODEL_PATH,
                    "-f", audioFilePath,
                    "--print-language"  // whisper.cpp flag to print detected language
            );

            //  Start the process
            Process process = pb.start();

            //  Read output from whisper-cli
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[Whisper Output] " + line);

                    //  Detect language from output (whisper prints like: "detected language: xx")
                    if (line.toLowerCase().contains("detected language:")) {
                        Pattern p = Pattern.compile("detected language:\\s*(\\w+)");
                        Matcher m = p.matcher(line.toLowerCase());
                        if (m.find()) {
                            result.detectedLanguage = m.group(1); // e.g. en, hi, te
                        }
                    } else {
                        //  Collect transcription text
                        textBuilder.append(line).append(" ");
                    }
                }
            }

            //  Wait for whisper process to complete
            int exitCode = process.waitFor();
            System.out.println("Whisper process finished with exit code: " + exitCode);

            // Save the transcription into the result object
            result.text = textBuilder.toString().trim();

            return result; //  Return final result

        } catch (Exception e) {
            throw new RuntimeException("Whisper transcription failed: " + e.getMessage(), e);
        }
    }
}
