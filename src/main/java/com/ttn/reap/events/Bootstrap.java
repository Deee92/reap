package com.ttn.reap.events;

import com.ttn.reap.entities.Item;
import com.ttn.reap.entities.Role;
import com.ttn.reap.entities.User;
import com.ttn.reap.repositories.ItemRepository;
import com.ttn.reap.repositories.UserRepository;
import com.ttn.reap.services.ItemService;
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
    
    @Autowired
    ItemRepository itemRepository;
    
    @Autowired
    ItemService itemService;
    
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
        
        if (!itemRepository.findAll().iterator().hasNext()) {
            System.out.println("Bootstrapping item one");
            Item item1 = new Item();
            item1.setName("To The New T-Shirt");
            item1.setPointsWorth(100);
            item1.setQuantity(50);
            item1.setImageUrl("/images/tshirt.jpg");
            itemService.save(item1);
            System.out.println(item1.toString());
            
            System.out.println("Bootstrapping item two");
            Item item2 = new Item();
            item2.setName("To The New Cap");
            item2.setPointsWorth(70);
            item2.setQuantity(100);
            item2.setImageUrl("/images/cap.jpg");
            itemService.save(item2);
            System.out.println(item2.toString());
            
            System.out.println("Bootstrapping item three");
            Item item3 = new Item();
            item3.setName("To The New Backpack");
            item3.setPointsWorth(150);
            item3.setQuantity(70);
            item3.setImageUrl("/images/backpack.jpg");
            itemService.save(item3);
            System.out.println(item3.toString());
            
            System.out.println("Bootstrapping item four");
            Item item4 = new Item();
            item4.setName("To The New Bottle");
            item4.setPointsWorth(80);
            item4.setQuantity(100);
            item4.setImageUrl("/images/bottle.png");
            itemService.save(item4);
            System.out.println(item4.toString());
            
            System.out.println("Bootstrapping item five");
            Item item5 = new Item();
            item5.setName("Staples Easy Button");
            item5.setPointsWorth(200);
            item5.setQuantity(30);
            item5.setImageUrl("/images/easy-button.jpg");
            itemService.save(item5);
            System.out.println(item5.toString());
            
            System.out.println("Bootstrapping item six");
            Item item6 = new Item();
            item6.setName("To The New Keychain");
            item6.setPointsWorth(30);
            item6.setQuantity(150);
            item6.setImageUrl("/images/keychain.jpg");
            itemService.save(item6);
            System.out.println(item6.toString());
            
            System.out.println("Bootstrapping item seven");
            Item item7 = new Item();
            item7.setName("Spiral Notebook + Pen Set");
            item7.setPointsWorth(40);
            item7.setQuantity(100);
            item7.setImageUrl("/images/notebook.jpg");
            itemService.save(item7);
            System.out.println(item7.toString());
            
            System.out.println("Bootstrapping item eight");
            Item item8 = new Item();
            item8.setName("Passport/Travel Wallet");
            item8.setPointsWorth(130);
            item8.setQuantity(50);
            item8.setImageUrl("/images/passport-wallet.jpg");
            itemService.save(item8);
            System.out.println(item8.toString());
            
            System.out.println("Bootstrapping item nine");
            Item item9 = new Item();
            item9.setName("Stationery Organizer");
            item9.setPointsWorth(50);
            item9.setQuantity(100);
            item9.setImageUrl("/images/stationery-organizer.jpg");
            itemService.save(item9);
            System.out.println(item9.toString());
            
            System.out.println("Bootstrapping item ten");
            Item item10 = new Item();
            item10.setName("Belkin 10000 mAh Power Bank");
            item10.setPointsWorth(200);
            item10.setQuantity(60);
            item10.setImageUrl("/images/power-bank.jpeg");
            itemService.save(item10);
            System.out.println(item10.toString());
        }
    }
}
