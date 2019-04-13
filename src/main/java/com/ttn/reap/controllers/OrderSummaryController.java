package com.ttn.reap.controllers;

import com.ttn.reap.entities.Item;
import com.ttn.reap.entities.OrderSummary;
import com.ttn.reap.entities.User;
import com.ttn.reap.services.ItemService;
import com.ttn.reap.services.OrderSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class OrderSummaryController {
    @Autowired
    ItemService itemService;

    @Autowired
    OrderSummaryService orderSummaryService;

    @PostMapping("/addToCart/{itemId}")
    @ResponseBody
    public void addItemToCart(@PathVariable("itemId") Integer itemId,
                              HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        List<Item> itemList = (List<Item>) httpSession.getAttribute("itemList");
        itemList.add(itemService.getItemById(itemId).get());
    }

    @GetMapping("/checkout")
    public ModelAndView createOrder(HttpServletRequest httpServletRequest) {
        System.out.println("Checking out");
        HttpSession httpSession = httpServletRequest.getSession();
        List<Item> itemList = (List<Item>) httpSession.getAttribute("itemList");
        User activeUser = (User) httpSession.getAttribute("activeUser");
        OrderSummary orderSummary = new OrderSummary();
        orderSummary.setUserId(activeUser.getId());
        Map<Integer, Integer> itemQuantity = new LinkedHashMap<>();
        Integer totalPoints = 0;
        for (Item item : itemList) {
            if (!itemQuantity.containsKey(item.getId()))
                itemQuantity.put(item.getId(), 1);
            else
                itemQuantity.put(item.getId(), itemQuantity.get(item.getId()) + 1);
            totalPoints += item.getPointsWorth();
        }
        orderSummary.setItemQuantity(itemQuantity);
        orderSummary.setTotalPointsRedeemed(totalPoints);
        orderSummaryService.save(orderSummary);
        System.out.println(orderSummary);
        itemList.clear();
        return new ModelAndView("redirect:/users/" + activeUser.getId() + "/orders");
    }
}
