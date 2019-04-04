package com.ttn.reap.services;

import com.ttn.reap.entities.Recognition;
import com.ttn.reap.entities.User;
import com.ttn.reap.repositories.RecognitionRepository;
import com.ttn.reap.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RecognitionService {
    @Autowired
    RecognitionRepository recognitionRepository;
    
    @Autowired
    UserService userService;
    
    public Recognition createRecognition(Recognition recognition) {
        recognition.setDate(new Date());
        System.out.println(recognition.getBadge());
        User sendingUser = userService.findUserById(recognition.getSenderId());
        User receivingUser = userService.findUserById(recognition.getReceiverId());
        if (recognition.getBadge().equals("gold")) {
            sendingUser.setGoldShareable(sendingUser.getGoldShareable() - 1);
            receivingUser.setGoldRedeemable(receivingUser.getGoldRedeemable() + 1);
        } else if (recognition.getBadge().equals("silver")) {
            sendingUser.setSilverShareable(sendingUser.getSilverShareable() - 1);
            receivingUser.setSilverRedeemable(receivingUser.getSilverRedeemable() + 1);
        } else if (recognition.getBadge().equals("bronze")) {
            sendingUser.setBronzeShareable(sendingUser.getBronzeRedeemable() - 1);
            receivingUser.setBronzeRedeemable(receivingUser.getBronzeRedeemable() + 1);
        }
        return recognitionRepository.save(recognition);
    }
    
}
