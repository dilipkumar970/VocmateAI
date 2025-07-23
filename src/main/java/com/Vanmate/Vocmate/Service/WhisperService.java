package com.Vanmate.Vocmate.Service;


import org.springframework.stereotype.Service;

/**
 * Service responsible for interacting with whisper.cpp to transcribe audio.
 */

@Service

/*
  Transcribes the given audio file using whisper.cpp.
  @param audioFilePath Path to your audio file (.wav)
  @return Transcribed text
 */

public class WhisperService {

    public String transcribeAudio(String AudioFilePath){

        return AudioFilePath;
    }
}
