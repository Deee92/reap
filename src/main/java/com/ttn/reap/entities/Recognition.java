package com.ttn.reap.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Recognition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    private Integer senderId;
    private Integer receiverId;
    @NotNull
    private String badge;
    @NotNull
    private String reason;
    @NotNull
    private String comment;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date date;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getSenderId() {
        return senderId;
    }
    
    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }
    
    public Integer getReceiverId() {
        return receiverId;
    }
    
    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }
    
    public String getBadge() {
        return badge;
    }
    
    public void setBadge(String badge) {
        this.badge = badge;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return "Recognition{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", badge='" + badge + '\'' +
                ", reason='" + reason + '\'' +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }
}
