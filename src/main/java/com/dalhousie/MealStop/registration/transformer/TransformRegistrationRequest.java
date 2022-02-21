package com.dalhousie.MealStop.registration.transformer;

import com.dalhousie.MealStop.registration.domainmodels.RegistrationRequest;
import com.dalhousie.MealStop.user.dbmodels.Login;
import com.dalhousie.MealStop.user.dbmodels.User;

import static com.dalhousie.MealStop.user.common.UserType.CUSTOMER;

public class TransformRegistrationRequest {

    public static User transformRegistrationRequestToUser(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setEmail(registrationRequest.getEmail());
        user.setDateOfBirth(registrationRequest.getDateOfBirth());
        user.setMobileNumber(registrationRequest.getMobile());
        user.setUserType(registrationRequest.getUserType() == CUSTOMER.name() ? 1 : 2);
        user.setAddress(registrationRequest.getAddress());
        return user;
    }

    public static Login transformRegistrationRequestToLogin(RegistrationRequest registrationRequest) {
        Login login = new Login();
        login.setUserName(registrationRequest.getUsername());
        login.setPassword(registrationRequest.getPassword());
        return login;
    }
}
