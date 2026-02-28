package com.poly.entity;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name="\"User\"") // Dùng dấu ngoặc kép vì "User" là từ khóa trong SQL
public class User {
    
    @Id
    @Column(name="Id")
    private String id;
    
    @Column(name="Password")
    private String password;
    
    @Column(name="Email")
    private String email;
    
    @Column(name="Fullname")
    private String fullname;
    
    @Column(name="Admin")
    private Boolean admin = false; // Mặc định là false (người dùng thường)

    // Liên kết 1-N tới bảng Favorite
    @OneToMany(mappedBy="user")
    List<Favorite> favorites;
    
    // Liên kết 1-N tới bảng Share
    @OneToMany(mappedBy="user")
    List<Share> shares;
    
    // --- Constructors (có thể thêm constructor rỗng và có tham số) ---
    
    public User() {
    }

    // --- Getters and Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    public List<Share> getShares() {
        return shares;
    }

    public void setShares(List<Share> shares) {
        this.shares = shares;
    }
}