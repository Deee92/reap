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
            admin.setFullName(admin.getFirstName() + " " + admin.getLastName());
            admin.setPassword("dee");
            Set roleSet = new HashSet<Role>();
            roleSet.add(Role.ADMIN);
            roleSet.add(Role.PRACTICE_HEAD);
            admin.setRoleSet(roleSet);
            userService.save(admin);
            System.out.println(admin.toString());
            
            System.out.println("Bootstrapping first user data");
            User user1 = new User();
            user1.setActive(true);
            user1.setEmail("divya.arora@tothenew.com");
            user1.setFirstName("Divya");
            user1.setLastName("Arora");
            user1.setFullName(user1.getFirstName() + " " + user1.getLastName());
            user1.setPassword("divya");
            Set roleSet1 = new HashSet<Role>();
            roleSet1.add(Role.SUPERVISOR);
            user1.setRoleSet(roleSet1);
            userService.save(user1);
            System.out.println(user1.toString());
            
            System.out.println("Bootstrapping second user data");
            User user2 = new User();
            user2.setActive(true);
            user2.setEmail("dhruv.oberoi@tothenew.com");
            user2.setFirstName("Dhruv");
            user2.setLastName("Oberoi");
            user2.setFullName(user2.getFirstName() + " " + user2.getLastName());
            user2.setPassword("dhruv");
            userService.save(user2);
            System.out.println(user2.toString());
            
            System.out.println("Bootstrapping third user data");
            User user3 = new User();
            user3.setActive(true);
            user3.setEmail("souvik.chakraborty@tothenew.com");
            user3.setFirstName("Souvik");
            user3.setLastName("Chakraborty");
            user3.setFullName(user3.getFirstName() + " " + user3.getLastName());
            user3.setPassword("souvik");
            Set roleSet3 = new HashSet<Role>();
            roleSet3.add(Role.SUPERVISOR);
            user3.setRoleSet(roleSet3);
            userService.save(user3);
            System.out.println(user3.toString());
            
            System.out.println("Bootstrapping fourth user data");
            User user4 = new User();
            user4.setActive(true);
            user4.setEmail("kanchan.sinha@tothenew.com");
            user4.setFirstName("Kanchan");
            user4.setLastName("Sinha");
            user4.setFullName(user4.getFirstName() + " " + user4.getLastName());
            user4.setPassword("kanchan");
            userService.save(user4);
            System.out.println(user4.toString());
        }
    }
}
