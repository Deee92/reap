package com.ttn.reap.component;

public class RecognitionSearch {
    private Integer currentUserId;
    private String fullName;
    
    public Integer getCurrentUserId() {
        return currentUserId;
    }
    
    public void setCurrentUserId(Integer currentUserId) {
        this.currentUserId = currentUserId;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    @Override
    public String toString() {
        return "RecognitionSearch{" +
                "currentUserId=" + currentUserId +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
