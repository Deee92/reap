package com.ttn.reap.controllers;

import com.ttn.reap.entities.Recognition;
import com.ttn.reap.entities.User;
import com.ttn.reap.services.RecognitionService;
import com.ttn.reap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RecognitionController {
    @Autowired
    RecognitionService recognitionService;

    @Autowired
    UserService userService;

    @PostMapping("/recognize")
    public ResponseEntity<String> recognizeNewer(@Valid @ModelAttribute("recognition") Recognition recognition,
                                                 RedirectAttributes redirectAttributes) {
        String receiverName = recognition.getReceiverName();
        System.out.println("Receiver name: " + receiverName);
        User receivingUser = userService.getUserByFullName(receiverName);
        if (receivingUser == null) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("myResponseHeader", "doesNotExist");
            return new ResponseEntity<String>("User does not exist", httpHeaders, HttpStatus.OK);
        }
        if (receivingUser.getId().equals(recognition.getSenderId())) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("myResponseHeader", "selfRecognition");
            return new ResponseEntity<String>("Users cannot recognize themselves", httpHeaders, HttpStatus.OK);
        }
        recognition.setReceiverId(receivingUser.getId());
        recognitionService.createRecognition(recognition);
        System.out.println(recognition);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("myResponseHeader", "successfulRecognition");
        return new ResponseEntity<String>("User recognized!", httpHeaders, HttpStatus.OK);
    }
}
