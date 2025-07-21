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
        // This method will run whenever a POST request is sent to /api/calls/webhook
        // Twilio will send a POST request here when someone calls your Twilio number
        String twimlResponse=
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + // XMl Declaration for SpringBoot
                        "<Response>" +
                        "<Say voice=\"alice\" language=\"en-US\">Hello! Your call is connected to VocMate test webhook. Thank you!</Say>" +
                        "<Response>";

        // the HTTP response with a 200 OK status
        // Content-Type header to application/xml (because Twilio expects XML)
        // Sending back the TwiML as the body
        return ResponseEntity.ok()
                .header("Content-Type", "application/xml")
                .body(twimlResponse);
    }
}