package com.ttn.reap.events;

import com.ttn.reap.entities.Role;
import com.ttn.reap.entities.User;
import com.ttn.reap.repositories.UserRepository;
import com.ttn.reap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Bootstrap {
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserService userService;
    
    @EventListener(ContextRefreshedEvent.class)
    void setUp() {
        if (!userRepository.findAll().iterator().hasNext()) {
            System.out.println("Bootstrapping admin data");
            User admin = new User();
            admin.setActive(true);
            admin.setEmail("deepika.tiwari@tothenew.com");
            admin.setFirstName("Deepika");
            admin.setLastName("Tiwari");
            admin.setPassword("dee");
            Set roleSet = new HashSet<Role>();
            roleSet.add(Role.ADMIN);
            roleSet.add(Role.PRACTICE_HEAD);
            admin.setRoleSet(roleSet);
            userService.save(admin);
            System.out.println(admin.toString());
        }
    }
}
