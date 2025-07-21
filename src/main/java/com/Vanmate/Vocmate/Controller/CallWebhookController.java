package com.Vanmate.Vocmate.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Endpoint for Twilio to hit (POST)

@RestController
@RequestMapping("/api/calls")
public class CallWebhookController {

    @PostMapping ("/incoming call")
    public ResponseEntity<String> handleIncomingCall() {
        // TwiML response to speak something to the caller
        String twimlResponse=
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<Response>" +
                        "<Say voice=\"alice\" language=\"en-US\">Hello! Your call is connected to VocMate test webhook. Thank you!</Say>" +
                        "<Response>";
    }
}