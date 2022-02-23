package com.dalhousie.MealStop.user.dbmodels;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="confirmationtoken")
public class ConfirmationToken {

    public ConfirmationToken() {
    }

    public ConfirmationToken(String token, LocalDateTime createdTime, LocalDateTime expiresTime, User user) {
        this.token = token;
        this.createdTime = createdTime;
        this.expiresTime = expiresTime;
        this.user = user;
    }

    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    @Column(nullable = false)
    private LocalDateTime expiresTime;

    @Column
    private LocalDateTime confirmedTime;

    @ManyToOne
    @JoinColumn(nullable = false,
            name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getExpiredTime() {
        return expiresTime;
    }

    public void setExpiredTime(LocalDateTime expiresTime) {
        this.expiresTime = expiresTime;
    }

    public LocalDateTime getConfirmedTime() {
        return confirmedTime;
    }

    public void setConfirmedTime(LocalDateTime confirmedTime) {
        this.confirmedTime = confirmedTime;
    }

    public User getUser() {
        return user;
    }
}

