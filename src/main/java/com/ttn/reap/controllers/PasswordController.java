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
import javax.servlet.http.HttpSession;
import java.util.Map;
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
            String appUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort();
            // Email message
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("reap-support@ttn.com");
            passwordResetEmail.setTo(optionalUser.get().getEmail());
            passwordResetEmail.setSubject("REAP - Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                    + "/reset-password?resetToken=" + optionalUser.get().getResetToken());
            emailService.sendEmail(passwordResetEmail);
            HttpSession httpSession = httpServletRequest.getSession();
            httpSession.setAttribute("userToken", optionalUser.get().getResetToken());
            ModelAndView modelAndView = new ModelAndView("redirect:/");
            redirectAttributes.addFlashAttribute("success", "Email sent to " + optionalUser.get().getEmail());
            return modelAndView;
        }
    }

    @GetMapping("/reset-password")
    public ModelAndView showResetPasswordPage(RedirectAttributes redirectAttributes,
                                              HttpServletRequest httpServletRequest,
                                              @RequestParam("resetToken") String resetToken) {
        ModelAndView modelAndView = new ModelAndView("reset-password");
        redirectAttributes.addFlashAttribute("success");
        HttpSession httpSession = httpServletRequest.getSession();
        String sessionToken = (String) httpSession.getAttribute("userToken");
        try {
            if (!sessionToken.equals(resetToken)) {
                ModelAndView modelAndView1 = new ModelAndView("redirect:/");
                redirectAttributes.addFlashAttribute("error", "Invalid reset token");
                return modelAndView1;
            }
        } catch (NullPointerException ne) {
            ModelAndView modelAndView1 = new ModelAndView("redirect:/");
            redirectAttributes.addFlashAttribute("error", "Unauthorized access");
            return modelAndView1;
        }
        return modelAndView;
    }

    @PostMapping("/reset-password")
    public ModelAndView processResetPasswordForm(HttpServletRequest httpServletRequest,
                                                 @RequestParam Map<String, String> requestParams,
                                                 RedirectAttributes redirectAttributes) {
        HttpSession httpSession = httpServletRequest.getSession();
        String token = (String) httpSession.getAttribute("userToken");
        Optional<User> optionalUser = userService.findByResetToken(token);
        if (!optionalUser.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("redirect:/");
            redirectAttributes.addFlashAttribute("error", "Invalid token");
            return modelAndView;
        }
        if (requestParams.get("passwordField").length() < 3) {
            ModelAndView modelAndView = new ModelAndView("redirect:/reset-password?resetToken=" + token);
            redirectAttributes.addFlashAttribute("error", "Passwords must be at least three characters in length");
            return modelAndView;
        } else {
            User user = optionalUser.get();
            user.setPassword(requestParams.get("passwordField"));
            user.setResetToken(null);
            userService.updateUser(user);
            httpSession.invalidate();
            ModelAndView modelAndView = new ModelAndView("redirect:/");
            redirectAttributes.addFlashAttribute("success", "Password reset successful! Please log in now.");
            return modelAndView;
        }
    }
}
