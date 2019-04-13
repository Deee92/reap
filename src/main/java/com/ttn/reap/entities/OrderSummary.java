package com.ttn.reap.entities;

import javax.persistence.Entity;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class OrderSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    // key=itemId value=itemQuantity
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Integer, Integer> itemQuantity = new LinkedHashMap<>();
    private Integer userId;
    private Integer totalPointsRedeemed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Map<Integer, Integer> getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Map<Integer, Integer> itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Integer getTotalPointsRedeemed() {
        return totalPointsRedeemed;
    }

    public void setTotalPointsRedeemed(Integer totalPointsRedeemed) {
        this.totalPointsRedeemed = totalPointsRedeemed;
    }

    @Override
    public String toString() {
        return "OrderSummary{" +
                "id=" + id +
                ", itemQuantity=" + itemQuantity +
                ", userId=" + userId +
                ", totalPointsRedeemed=" + totalPointsRedeemed +
                '}';
    }
}