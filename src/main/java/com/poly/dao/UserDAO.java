package com.poly.dao;

import com.poly.entity.User;
import jakarta.persistence.TypedQuery;

public class UserDAO extends AbstractDAO<User> {
    
    public UserDAO() {
        super();
    }
    
    public User findByEmail(String email) {
        String jsql = "SELECT o FROM User o WHERE o.email = :email";
        TypedQuery<User> query = em.createQuery(jsql, User.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null; // Không tìm thấy
        }
    }

    // Phương thức check login
    public User checkLogin(String userId, String password) {
        String jsql = "SELECT o FROM User o WHERE o.id = :uid AND o.password = :pass";
        TypedQuery<User> query = em.createQuery(jsql, User.class);
        query.setParameter("uid", userId);
        query.setParameter("pass", password);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null; // Sai login
        }
    }
}