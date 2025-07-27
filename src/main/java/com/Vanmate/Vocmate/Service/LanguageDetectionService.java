
//LanguageDetectionService checks caller’s original language from metadata
// (Twilio can send From country code, or we can detect audio language via a library
// like langdetect or Google’s Language Detection API


package com.Vanmate.Vocmate.Service;

import org.springframework.stereotype.Service;

@Service
public class LanguageDetectionService {

    // Simple fallback: use whisper-detected lang if available
    public String detectLanguage(String fallbackDetected, String text) {
        if (fallbackDetected != null && !fallbackDetected.isEmpty()) {
            return fallbackDetected;
        }
        // If you want to integrate Google Language Detection, do here.
        // For now, we simply return "en" as default.
        return "en";
    }
}

