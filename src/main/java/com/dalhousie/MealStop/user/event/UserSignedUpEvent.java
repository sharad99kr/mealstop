package com.dalhousie.MealStop.user.event;

import com.dalhousie.MealStop.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class UserSignedUpEvent extends ApplicationEvent {

    private User user;
    private String applicationUrl;

    public UserSignedUpEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
