package com.ttn.reap.services;

import com.ttn.reap.entities.Recognition;
import com.ttn.reap.entities.User;
import com.ttn.reap.repositories.RecognitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RecognitionService {
    @Autowired
    RecognitionRepository recognitionRepository;

    @Autowired
    UserService userService;

    // Create a new recognition, update sending and receiving user's badges
    public Recognition createRecognition(Recognition recognition) {
        recognition.setDate(LocalDate.now());
        // System.out.println(recognition.getBadge());
        User sendingUser = userService.findUserById(recognition.getSenderId());
        User receivingUser = userService.findUserById(recognition.getReceiverId());
        if (recognition.getBadge().equals("gold")) {
            if (sendingUser.getGoldShareable() > 0) {
                sendingUser.setGoldShareable(sendingUser.getGoldShareable() - 1);
                receivingUser.setGoldRedeemable(receivingUser.getGoldRedeemable() + 1);
            }
        } else if (recognition.getBadge().equals("silver")) {
            if (sendingUser.getSilverShareable() > 0) {
                sendingUser.setSilverShareable(sendingUser.getSilverShareable() - 1);
                receivingUser.setSilverRedeemable(receivingUser.getSilverRedeemable() + 1);
            }
        } else if (recognition.getBadge().equals("bronze")) {
            if (sendingUser.getBronzeShareable() > 0) {
                sendingUser.setBronzeShareable(sendingUser.getBronzeShareable() - 1);
                receivingUser.setBronzeRedeemable(receivingUser.getBronzeRedeemable() + 1);
            }
        }
        receivingUser.setPoints(userService.calculatePoints(receivingUser));
        return recognitionRepository.save(recognition);
    }

    public List<Recognition> getListOfRecognitions() {
        return recognitionRepository.findAll();
    }

    public List<Recognition> getRecognitionsByName(String receiverName) {
        return recognitionRepository.findRecognitionByReceiverName(receiverName);
    }

    // Return a list of recognitions by date
    public List<Recognition> getRecognitionsBetweenDates(String dateString) {
        LocalDate today = LocalDate.now();
        if (dateString.equals("today")) {
            return recognitionRepository.findByDateBetween(today, today);
        } else if (dateString.equals("yesterday")) {
            return recognitionRepository.findByDate(today.minusDays(1));
        } else if (dateString.equals("last7")) {
            return recognitionRepository.findByDateBetween(today.minusDays(7), today);
        } else {
            return recognitionRepository.findByDateBetween(today.minusDays(30), today);
        }
    }

    public List<Recognition> getRecognitionsByReceiverName(String receiverName) {
        return recognitionRepository.findByReceiverName(receiverName);
    }

    public List<Recognition> getRecognitionsBySenderName(String senderName) {
        return recognitionRepository.findBySenderName(senderName);
    }

    public List<Recognition> getRecognitionsByReceiverId(Integer receiverId) {
        return recognitionRepository.findByReceiverId(receiverId);
    }

    public List<Recognition> getRecognitionsBySenderId(Integer senderId) {
        return recognitionRepository.findBySenderId(senderId);
    }

    public Optional<Recognition> getRecognitionById(Integer id) {
        return recognitionRepository.findById(id);
    }

    public void updateRecognition(Recognition recognition) {
        recognitionRepository.save(recognition);
    }
}
