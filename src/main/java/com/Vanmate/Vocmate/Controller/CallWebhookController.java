package com.Vanmate.Vocmate.Controller;

import com.Vanmate.Vocmate.Service.TwilioService;
import com.Vanmate.Vocmate.Service.WhisperService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calls")
public class CallWebhookController {

        private final TwilioService twilioService;
        private final WhisperService whisperService;

        @PostMapping("/webhook")
        public ResponseEntity<String> handleIncomingCall(){
            return twilioService.buildTwiMLGreeting();
        }

        @PostMapping("/recording")
        public ResponseEntity<String> handleRecordingCallBack(@RequestParam Map<String , String> params){

           // Extract RecordingUrl from Twilio's request
            String recordingUrl = params.get("RecordingUrl");

            // Call service to process the audio
            twilioService.processRecording(recordingUrl);

            return  ResponseEntity.ok("Recording Received Successfully");
        }

//        @PostMapping("/test")
//        public ResponseEntity<String> whisperTest(){
//            String sampleFile = "C:\\web dev\\whisper.cpp\\samples\\english.mp3";
//            String result = twilioService.transcribeSample(sampleFile);
//            return ResponseEntity.ok(result);
//        }
        @PostMapping("/test")
        public ResponseEntity<String> whisperTest() {
            String sampleFile = "C:\\web dev\\whisper.cpp\\samples\\jfk.wav";

            // 🔹 Get the WhisperResult object
            WhisperService.WhisperResult result = whisperService.transcribeAudio(sampleFile);

            // 🔹 Extract the text from it
            String transcript = result.text;

            return ResponseEntity.ok(transcript);
        }
}

