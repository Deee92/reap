package com.ttn.reap.controllers;

import com.ttn.reap.entities.Recognition;
import com.ttn.reap.entities.User;
import com.ttn.reap.services.RecognitionService;
import com.ttn.reap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class RecognitionController {
    @Autowired
    RecognitionService recognitionService;
    
    @Autowired
    UserService userService;
    
    @PostMapping("/recognize")
    @ResponseBody
    public String recognizeNewer(@Valid @ModelAttribute("recognition") Recognition recognition) {
        String receiverName = recognition.getReceiverName();
        System.out.println("Receiver name: " + receiverName);
        User receivingUser = userService.getUserByFullName(receiverName);
        recognition.setReceiverId(receivingUser.getId());
        recognitionService.createRecognition(recognition);
        System.out.println(recognition);
        return "success";
    }
}
