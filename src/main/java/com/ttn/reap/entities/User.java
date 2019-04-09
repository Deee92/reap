package com.ttn.reap.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "First name cannot be blank")
    @NotNull(message = "First name must have a value")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    @NotNull(message = "Last name must have a value")
    private String lastName;
    private String fullName;
    private String photo;
    @NotNull
    @NotBlank
    @Column(unique = true)
    @Email(message = "Invalid email")
    private String email;
    private String resetToken;
    @Size(min = 3, message = "Password should be at least 3 characters in length")
    private String password;
    private Boolean active = true;
    private Integer goldShareable = 3;
    private Integer silverShareable = 2;
    private Integer bronzeShareable = 1;
    private Integer goldRedeemable = 0;
    private Integer silverRedeemable = 0;
    private Integer bronzeRedeemable = 0;

    private Integer points = 0;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Role> roleSet = new HashSet<>(Arrays.asList(Role.USER));

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
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

    public Integer getGoldShareable() {
        return goldShareable;
    }

    public void setGoldShareable(Integer goldShareable) {
        this.goldShareable = goldShareable;
    }

    public Integer getSilverShareable() {
        return silverShareable;
    }

    public void setSilverShareable(Integer silverShareable) {
        this.silverShareable = silverShareable;
    }

    public Integer getBronzeShareable() {
        return bronzeShareable;
    }

    public void setBronzeShareable(Integer bronzeShareable) {
        this.bronzeShareable = bronzeShareable;
    }

    public Integer getGoldRedeemable() {
        return goldRedeemable;
    }

    public void setGoldRedeemable(Integer goldRedeemable) {
        this.goldRedeemable = goldRedeemable;
    }

    public Integer getSilverRedeemable() {
        return silverRedeemable;
    }

    public void setSilverRedeemable(Integer silverRedeemable) {
        this.silverRedeemable = silverRedeemable;
    }

    public Integer getBronzeRedeemable() {
        return bronzeRedeemable;
    }

    public void setBronzeRedeemable(Integer bronzeRedeemable) {
        this.bronzeRedeemable = bronzeRedeemable;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public List<Role> getRolesAsList() {
        return new ArrayList<Role>(roleSet);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", photo='" + photo + '\'' +
                ", email='" + email + '\'' +
                ", resetToken='" + resetToken + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", goldShareable=" + goldShareable +
                ", silverShareable=" + silverShareable +
                ", bronzeShareable=" + bronzeShareable +
                ", goldRedeemable=" + goldRedeemable +
                ", silverRedeemable=" + silverRedeemable +
                ", bronzeRedeemable=" + bronzeRedeemable +
                ", points=" + points +
                ", roleSet=" + roleSet +
                '}';
    }
}
