package com.Vanmate.Vocmate.Service;

// Handle Twilio webhook, call events, and TwiML responses

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwilioService {


    private final WhisperService whisperService;
    private final RecordingService recordingService;
    private final TranslationService translationService;
    private final LanguageDetectionService languageDetectionService;

//    public  String transcribeSample(String sampleFile) {
//        return whisperService.transcribeAudio(sampleFile).toString();
//
//    }

    public ResponseEntity<String> buildTwiMLGreeting() {
        // TwiML response to speak something to the caller
        // This method will run whenever a POST request is sent to /api/calls/webhook
        // Twilio will send a POST request here when someone calls your Twilio number
        String twimlResponse=
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + // XMl Declaration for SpringBoot
                        "<Response>" +
                        "<Say voice=\"alice\" language=\"en-US\">Hello! Your call is connected to VocMate test webhook. Thank you!</Say>" +
                        "<Record maxLength=\"30\" action=\"/api/calls/recording\" method=\"POST\" />" +
                        "</Response>";

        // the HTTP response with a 200 OK status
        // Content-Type header to application/xml (because Twilio expects XML)
        // Sending back the TwiML as the body

        return ResponseEntity.ok()
                .header("Content-Type", "application/xml")
                .body(twimlResponse);
    }

    public void processRecording(String recordingUrl) {
        // Step 1: Download recording
        String filePath = recordingService.downloadRecording(recordingUrl);

        // Step 2: Transcribe using whisper
        WhisperService.WhisperResult result = whisperService.transcribeAudio(filePath);

        // Step 3: Detect language (fallback if needed)
        String lang = languageDetectionService.detectLanguage(result.detectedLanguage, result.text);

        // Step 4: Translate if needed
        String translatedText = translationService.translateText(result.text, lang);

        // Step 5: Send to TTS (to be implemented in next step)
        System.out.println("✅ Final text for TTS: " + translatedText + " in language: " + lang);
    }
}
