package com.ttn.reap.controllers;

import com.ttn.reap.component.LoggedInUser;
import com.ttn.reap.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IndexController {
    // Show login/sign up page
    @GetMapping("/")
    public ModelAndView index(RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("newUser", new User());
        modelAndView.addObject("loggedInUser", new LoggedInUser());
        redirectAttributes.addAttribute("error");
        redirectAttributes.addAttribute("success");
        return modelAndView;
    }
}
