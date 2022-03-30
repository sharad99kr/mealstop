package com.dalhousie.MealStop.email;

public interface IEmailService {
    void sendEmail(String to, String content, String subject);
}
