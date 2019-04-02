package com.ttn.reap.services;

import com.ttn.reap.entities.Role;
import com.ttn.reap.entities.User;
import com.ttn.reap.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    
    public User save(User user) {
        User userToSave = setBadges(user);
        userToSave.setPoints(calculatePoints(userToSave));
        return userRepository.save(userToSave);
    }
    
    public List<User> getUserList() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUser(Integer id) {
        return userRepository.findById(id);
    }
    
    User setBadges(User user) {
        if (user.getRoleSet().contains(Role.PRACTICE_HEAD)) {
            user.setGold(9);
            user.setSilver(6);
            user.setBronze(3);
        } else if (user.getRoleSet().contains(Role.SUPERVISOR)) {
            user.setGold(6);
            user.setSilver(3);
            user.setBronze(2);
        } else {
            user.setGold(3);
            user.setSilver(2);
            user.setBronze(1);
        }
        return user;
    }
    
    Integer calculatePoints(User user) {
        Integer points;
        points = user.getGold() * 30
                + user.getSilver() * 20
                + user.getBronze() * 10;
        return points;
    }
    
}
