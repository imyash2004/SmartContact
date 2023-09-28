package com.smart.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(length = 500)
    private String about;
    @NotBlank(message = "Value Required !!" )
    @Size(min=2,max = 20,message = "minimum 2 and maximum 20 characters are required !!")
    private String name;

    private String password;
    private boolean enabled;
    private String role;
    private String imageUrl;
    @Column(unique = true)
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",message="Invalid email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Contact> contacts=new ArrayList<>();


    public User() {
        super();
    }

    public User(int id, String about, String name, String password, boolean enabled, String role, String imageUrl, String email) {
        this.id = id;
        this.about = about;
        this.name = name;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
        this.imageUrl = imageUrl;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", about='" + about + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", role='" + role + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", email='" + email + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
