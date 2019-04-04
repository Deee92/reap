package com.ttn.reap.services;

import com.ttn.reap.entities.Recognition;
import com.ttn.reap.repositories.RecognitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RecognitionService {
    @Autowired
    RecognitionRepository recognitionRepository;
    
    public Recognition createRecognition(Recognition recognition) {
        recognition.setDate(new Date());
        return recognitionRepository.save(recognition);
    }
}
