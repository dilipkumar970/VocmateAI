package com.Vanmate.Vocmate.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VocmateController {

    @GetMapping("/test")
    public String testEndpoint() {
        return "✅ VocMate backend is running!";
    }
}