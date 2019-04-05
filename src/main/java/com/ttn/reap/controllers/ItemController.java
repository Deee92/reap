package com.ttn.reap.controllers;

import com.ttn.reap.entities.Item;
import com.ttn.reap.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ItemController {
    @Autowired
    ItemService itemService;
    
    @GetMapping("/items")
    public ModelAndView getItemsPage() {
        ModelAndView modelAndView = new ModelAndView("items");
        List<Item> itemList = itemService.getAllItems();
        modelAndView.addObject("itemList", itemList);
        return modelAndView;
    }
}
