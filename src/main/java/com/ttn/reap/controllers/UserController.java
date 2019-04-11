package com.ttn.reap.controllers;

import com.ttn.reap.component.LoggedInUser;
import com.ttn.reap.component.RecognitionSearch;
import com.ttn.reap.entities.Recognition;
import com.ttn.reap.entities.Role;
import com.ttn.reap.entities.User;
import com.ttn.reap.exceptions.UserNotFoundException;
import com.ttn.reap.services.RecognitionService;
import com.ttn.reap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    RecognitionService recognitionService;

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "/home/ttn/ttn_dev/reap/src/main/resources/static/user-images/";

    @GetMapping("/users")
    @ResponseBody
    List<User> getUserList() {
        return userService.getUserList();
    }

    @GetMapping("/users/{id}")
    public ModelAndView getUser(@PathVariable Integer id,
                                HttpServletRequest httpServletRequest,
                                RedirectAttributes redirectAttributes) {
        HttpSession httpSession = httpServletRequest.getSession();
        User activeUser = (User) httpSession.getAttribute("activeUser");

        try {
            if (id != activeUser.getId()) {
                ModelAndView modelAndView = new ModelAndView("redirect:/");
                redirectAttributes.addFlashAttribute("error", "Please log in to continue");
                return modelAndView;
            }
        } catch (NullPointerException ne) {
            ModelAndView modelAndView = new ModelAndView("redirect:/");
            redirectAttributes.addFlashAttribute("error", "Please log in to continue");
            return modelAndView;
        }

        Optional<User> optionalUser = userService.getUser(id);
        if (!optionalUser.isPresent())
            throw new UserNotFoundException("No user with id " + id);
        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("user", optionalUser.get());
        modelAndView.addObject("recognition", new Recognition());
        modelAndView.addObject("recognitionSearch", new RecognitionSearch());
        List<Recognition> recognitionList = recognitionService.getListOfRecognitions();
        Collections.reverse(recognitionList);
        modelAndView.addObject("recognitionList", recognitionList);
        Map<String, List<Integer>> recognizedUserRedeemableBadges = new LinkedHashMap<>();
        Integer recognizedUserGold, recognizedUserSilver, recognizedUserBronze;
        for (Recognition recognition : recognitionList) {
            User recognizedUser = userService.getUserByFullName(recognition.getReceiverName());
            recognizedUserGold = recognizedUser.getGoldRedeemable();
            recognizedUserSilver = recognizedUser.getSilverRedeemable();
            recognizedUserBronze = recognizedUser.getBronzeRedeemable();
            recognizedUserRedeemableBadges.put(recognizedUser.getFullName(), Arrays.asList(recognizedUserGold, recognizedUserSilver, recognizedUserBronze));
        }
        // System.out.println(recognizedUserRedeemableBadges);
        modelAndView.addObject("recognizedUserRedeemableBadges", recognizedUserRedeemableBadges);
        redirectAttributes.addAttribute("error");
        boolean isAdmin = optionalUser.get().getRoleSet().contains(Role.ADMIN);
        if (isAdmin) {
            modelAndView.addObject("isAdmin", isAdmin);
            List<User> userList = userService.getUserList();
            modelAndView.addObject("users", userList);
        }
        return modelAndView;
    }

    @GetMapping("/users/{id}/recognitions")
    public ModelAndView getUserRecognitions(@PathVariable("id") Integer id,
                                            HttpServletRequest httpServletRequest,
                                            RedirectAttributes redirectAttributes) {
        HttpSession httpSession = httpServletRequest.getSession();
        User activeUser = (User) httpSession.getAttribute("activeUser");
        try {
            if (id != activeUser.getId()) {
                ModelAndView modelAndView = new ModelAndView("redirect:/");
                redirectAttributes.addFlashAttribute("error", "Please log in to view your recognitions");
                return modelAndView;
            }
        } catch (NullPointerException ne) {
            ModelAndView modelAndView = new ModelAndView("redirect:/");
            redirectAttributes.addFlashAttribute("error", "Please log in to view your recognitions");
            return modelAndView;
        }
        Optional<User> optionalUser = userService.getUser(id);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("No user with id " + id);
        }
        ModelAndView modelAndView = new ModelAndView("recognitions");
        modelAndView.addObject("user", optionalUser.get());
        List<Recognition> receivedRecognitionsList = recognitionService.getRecognitionsByReceiverName(optionalUser.get().getFullName());
        modelAndView.addObject("receivedRecognitionsList", receivedRecognitionsList);
        List<Recognition> sentRecognitionsList = recognitionService.getRecognitionsBySenderName(optionalUser.get().getFullName());
        modelAndView.addObject("sentRecognitionsList", sentRecognitionsList);
        return modelAndView;
    }

    @PostMapping("/users")
    public ModelAndView createNewUser(@Valid @ModelAttribute("newUser") User user,
                                      BindingResult bindingResult,
                                      @ModelAttribute("loggedInUser") LoggedInUser loggedInUser,
                                      @RequestParam("image") MultipartFile file,
                                      HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            System.out.println("ERROR ERROR ERROR");
            return new ModelAndView("index");
        } else {
            HttpSession httpSession = httpServletRequest.getSession();
            httpSession.setAttribute("activeUser", user);
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                String photoPath = "/user-images/" + file.getOriginalFilename();
                user.setPhoto(photoPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            userService.save(user);
            System.out.println("User saved: " + user.toString());
            return new ModelAndView("redirect:/users/" + user.getId());
        }
    }

    @PutMapping("/users/{id}")
    public User editUser(@PathVariable Integer id, @RequestBody @Valid User user) {
        Optional<User> userOptional = userService.getUser(id);
        if (userOptional.isPresent())
            return userService.save(user);
        else throw new UserNotFoundException("No user with id " + user);
    }

    @PostMapping("/login")
    public ModelAndView logUserIn(@ModelAttribute("loggedInUser") LoggedInUser loggedInUser,
                                  HttpServletRequest httpServletRequest,
                                  RedirectAttributes redirectAttributes) {
        System.out.println(loggedInUser);
        Optional<User> optionalUser = userService.findUserByEmailAndPassword(loggedInUser.getEmail(), loggedInUser.getPassword());
        if (!optionalUser.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("redirect:/");
            redirectAttributes.addFlashAttribute("error", "Invalid credentials");
            return modelAndView;
        } else {
            HttpSession httpSession = httpServletRequest.getSession();
            httpSession.setAttribute("activeUser", optionalUser.get());
            return new ModelAndView("redirect:/users/" + optionalUser.get().getId());
        }
    }

    @PostMapping("/logout")
    public ModelAndView logUserOut(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.invalidate();
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/searchRecognitionByName")
    @ResponseBody
    public List<Recognition> getRecognitionsByName(@ModelAttribute("recognitionSearch") RecognitionSearch recognitionSearch) {
        System.out.println(recognitionSearch);
        List<Recognition> recognitionList = recognitionService.getRecognitionsByName(recognitionSearch.getFullName());
        return recognitionList;
    }

    @GetMapping("/searchRecognitionsByDate/{date}")
    @ResponseBody
    public List<Recognition> getRecognitionsByDate(@PathVariable("date") String dateString) {
        System.out.println("In controller: " + dateString);
        List<Recognition> recognitionList = recognitionService.getRecognitionsBetweenDates(dateString);
        return recognitionList;
    }

    @GetMapping("/autocomplete")
    @ResponseBody
    public List<User> getUsersByNamePattern(@RequestParam("pattern") String pattern) {
        System.out.println(pattern);
        List<User> userList = userService.findUserByFullNamePattern(pattern + "%");
        System.out.println(userList);
        return userList;
    }
}
