package com.poly.entity;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="Share")
public class Share {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long id;
    
    // Liên kết N-1 tới User
    @ManyToOne
    @JoinColumn(name="UserId")
    private User user;
    
    // Liên kết N-1 tới Video
    @ManyToOne
    @JoinColumn(name="VideoId")
    private Video video;
    
    @Column(name="Emails")
    private String emails;
    
    @Temporal(TemporalType.DATE) // Chỉ lấy Ngày/Tháng/Năm
    @Column(name="ShareDate")
    private Date shareDate = new Date(); // Tự động lấy ngày hiện tại
    
    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public Date getShareDate() {
        return shareDate;
    }

    public void setShareDate(Date shareDate) {
        this.shareDate = shareDate;
    }
}