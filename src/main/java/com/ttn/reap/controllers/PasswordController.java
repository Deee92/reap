package com.ttn.reap.controllers;

import com.ttn.reap.entities.User;
import com.ttn.reap.services.EmailService;
import com.ttn.reap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PasswordController {
    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @PostMapping("/forgot")
    public ModelAndView processForgotPasswordForm(@RequestParam("email") String email,
                                                  HttpServletRequest httpServletRequest,
                                                  RedirectAttributes redirectAttributes) {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (!optionalUser.isPresent()) {
            System.out.println("No user with email " + email);
            ModelAndView modelAndView = new ModelAndView("redirect:/");
            redirectAttributes.addFlashAttribute("error", "No user with this email, consider signing up!");
            return modelAndView;
        } else {
            System.out.println(optionalUser.get());
            optionalUser.get().setResetToken(UUID.randomUUID().toString());
            userService.updateUser(optionalUser.get());
            String appUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName();
            // Email message
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("reap-support@ttn.com");
            passwordResetEmail.setTo(optionalUser.get().getEmail());
            passwordResetEmail.setSubject("REAP - Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                    + "/reset?token=" + optionalUser.get().getResetToken());
            emailService.sendEmail(passwordResetEmail);
            ModelAndView modelAndView = new ModelAndView("redirect:/reset-password");
            redirectAttributes.addAttribute("resetToken", optionalUser.get().getResetToken());
            redirectAttributes.addAttribute("userEmail", optionalUser.get().getEmail());
            redirectAttributes.addFlashAttribute("success", "Email sent to " + optionalUser.get().getEmail());
            return modelAndView;
        }
    }

    @GetMapping("/reset-password")
    public ModelAndView showResetPasswordPage(RedirectAttributes redirectAttributes,
                                              HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = new ModelAndView("reset-password");
        redirectAttributes.addFlashAttribute("success");
        modelAndView.addObject("resetToken", httpServletRequest.getParameter("resetToken"));
        modelAndView.addObject("userEmail", httpServletRequest.getParameter("userEmail"));
        return modelAndView;
    }

    @PostMapping("/reset-password")
    public void processResetPasswordForm() {
        System.out.println("In controller: will reset password here");
    }
}
