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
        WhisperResult result = new WhisperResult();
        StringBuilder textBuilder = new StringBuilder();

        try {
            ProcessBuilder pb = new ProcessBuilder(
                    WHISPER_EXEC_PATH,
                    "-m", WHISPER_MODEL_PATH,
                    "-f", audioFilePath,
                    "--print-language"  // whisper print detected language
            );

            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[Whisper Output] " + line);

                    // detect language output (whisper prints like: "detected language: xx")
                    if (line.toLowerCase().contains("detected language:")) {
                        Pattern p = Pattern.compile("detected language: (\\w+)");
                        Matcher m = p.matcher(line.toLowerCase());
                        if (m.find()) {
                            result.detectedLanguage = m.group(1); // e.g. en, hi, te
                        }
                    } else {
                        // collect transcription lines
                        textBuilder.append(line).append(" ");
                    }
                }
            }

            process.waitFor();
            result.text = textBuilder.toString().trim();
            return result;

        } catch (Exception e) {
            throw new RuntimeException(" Whisper transcription failed: " + e.getMessage(), e);
        }
    }
}
