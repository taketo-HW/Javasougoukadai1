package com.example.dockerapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false)
    private Boolean isMale;

    @Column(nullable = false)
    private int old;

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Boolean getIsMale() {
        return isMale;
    }

    public int getOld() {
        return old;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIsMale(Boolean isMale) {
        this.isMale = isMale;
    }

    public void setOld(int old) {
        this.old = old;
    }
}
