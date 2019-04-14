package com.ttn.reap.controllers;

import com.ttn.reap.entities.Item;
import com.ttn.reap.entities.OrderSummary;
import com.ttn.reap.entities.User;
import com.ttn.reap.services.ItemService;
import com.ttn.reap.services.OrderSummaryService;
import com.ttn.reap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    UserService userService;

    // Add an item to user's cart
    @PostMapping("/addToCart/{itemId}")
    public ResponseEntity<String> addItemToCart(@PathVariable("itemId") Integer itemId,
                                                HttpServletRequest httpServletRequest) {
        Item itemToAdd = itemService.getItemById(itemId).get();
        HttpSession httpSession = httpServletRequest.getSession();
        User activeUser = (User) httpSession.getAttribute("activeUser");
        List<Item> itemList = (List<Item>) httpSession.getAttribute("itemList");
        Integer currentCartPointsWorth = 0;
        for (Item item : itemList) {
            currentCartPointsWorth += item.getPointsWorth();
        }
        // System.out.println(activeUser.getPoints());
        if (activeUser.getPoints() < itemToAdd.getPointsWorth() + currentCartPointsWorth) {
            System.out.println("Not enough points");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("myResponseHeader", "insufficientPoints");
            return new ResponseEntity<String>("You do not have enough redeemable points for this item", httpHeaders, HttpStatus.OK);
        }
        itemList.add(itemToAdd);
        httpSession.setAttribute("currentCartTotal", currentCartPointsWorth + itemToAdd.getPointsWorth());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("myResponseHeader", "cartAddSuccessful");
        return new ResponseEntity<String>("Item added to cart", httpHeaders, HttpStatus.OK);
    }

    // Remove an item from user's cart
    @PutMapping("/removeFromCart/{itemId}")
    @ResponseBody
    public void removeItemFromCart(@PathVariable("itemId") Integer itemId,
                                   HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        List<Item> itemList = (List<Item>) httpSession.getAttribute("itemList");
        Item item = itemService.getItemById(itemId).get();
        ListIterator<Item> itemListIterator = itemList.listIterator();
        while (itemListIterator.hasNext()) {
            if (itemListIterator.next().getId() == item.getId()) {
                itemListIterator.remove();
                break;
            }
        }
        Integer currentCartPointsWorth = 0;
        for (Item itemInCart : itemList) {
            currentCartPointsWorth += itemInCart.getPointsWorth();
        }
        httpSession.setAttribute("currentCartTotal", currentCartPointsWorth);
        httpSession.setAttribute("itemList", itemList);
    }

    // Create a new order with items from the cart
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
        userService.deductPointsOnCheckout(activeUser, totalPoints);
        System.out.println("Order summary: " + orderSummary);
        itemList.clear();
        httpSession.setAttribute("currentCartTotal", 0);
        return new ModelAndView("redirect:/users/" + activeUser.getId() + "/orders");
    }
}
