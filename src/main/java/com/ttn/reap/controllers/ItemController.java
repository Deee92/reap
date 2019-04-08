package com.ttn.reap.controllers;

import com.ttn.reap.entities.Item;
import com.ttn.reap.entities.User;
import com.ttn.reap.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ItemController {
    @Autowired
    ItemService itemService;
    
    @GetMapping("/items")
    public ModelAndView getItemsPage(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        HttpSession httpSession = httpServletRequest.getSession();
        User activeUser = (User) httpSession.getAttribute("activeUser");
        if (activeUser == null) {
            ModelAndView modelAndView = new ModelAndView("redirect:/");
            redirectAttributes.addFlashAttribute("error", "Please log in to view redeemable items");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("items");
        List<Item> itemList = itemService.getAllItems();
        modelAndView.addObject("itemList", itemList);
        modelAndView.addObject("user", activeUser);
        return modelAndView;
    }
}
