package com.ttn.reap.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull(message = "First name must have a value")
    private String firstName;
    @NotNull(message = "Last name must have a value")
    private String lastName;
    @Lob
    private Byte[] photo;
    @Email(message = "Invalid email")
    private String email;
    @Size(min = 3, message = "Password should be at least 3 characters in length")
    private String password;
    private Boolean active;
    private Integer gold = 3;
    private Integer silver = 2;
    private Integer bronze = 1;
    private Integer points = 0;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Role> roleSet = new HashSet<>();
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public Byte[] getPhoto() {
        return photo;
    }
    
    public void setPhoto(Byte[] photo) {
        this.photo = photo;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public Integer getGold() {
        return gold;
    }
    
    public void setGold(Integer gold) {
        this.gold = gold;
    }
    
    public Integer getSilver() {
        return silver;
    }
    
    public void setSilver(Integer silver) {
        this.silver = silver;
    }
    
    public Integer getBronze() {
        return bronze;
    }
    
    public void setBronze(Integer bronze) {
        this.bronze = bronze;
    }
    
    public Set<Role> getRoleSet() {
        return roleSet;
    }
    
    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }
    
    public Integer getPoints() {
        return points;
    }
    
    public void setPoints(Integer points) {
        this.points = points;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", gold=" + gold +
                ", silver=" + silver +
                ", bronze=" + bronze +
                ", points=" + points +
                ", roleSet=" + roleSet +
                '}';
    }
}
