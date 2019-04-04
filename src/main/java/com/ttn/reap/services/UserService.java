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
        userToSave.setFullName(userToSave.getFirstName() + " " + userToSave.getLastName());
        return userRepository.save(userToSave);
    }
    
    public List<User> getUserList() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUser(Integer id) {
        return userRepository.findById(id);
    }
    
    public User findUserById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.get();
    }
    
    User setBadges(User user) {
        if (user.getRoleSet().contains(Role.PRACTICE_HEAD)) {
            user.setGoldShareable(9);
            user.setSilverShareable(6);
            user.setBronzeShareable(3);
        } else if (user.getRoleSet().contains(Role.SUPERVISOR)) {
            user.setGoldShareable(6);
            user.setSilverShareable(3);
            user.setBronzeShareable(2);
        } else {
            user.setGoldShareable(3);
            user.setSilverShareable(2);
            user.setBronzeShareable(1);
        }
        return user;
    }
    
    public Integer calculatePoints(User user) {
        Integer points;
        points = user.getGoldRedeemable() * 30
                + user.getSilverRedeemable() * 20
                + user.getBronzeRedeemable() * 10;
        return points;
    }
    
    public User getUserByFullName(String fullName) {
        return userRepository.findByFullName(fullName);
    }
    
}
