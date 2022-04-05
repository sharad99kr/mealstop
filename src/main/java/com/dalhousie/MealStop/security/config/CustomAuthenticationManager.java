package com.dalhousie.MealStop.security.config;

import com.dalhousie.MealStop.common.CommonConstants;
import com.dalhousie.MealStop.user.entity.User;
import com.dalhousie.MealStop.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CustomAuthenticationManager implements AuthenticationManager {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encode;

    private Authentication checkUser(String password, User user, Authentication authentication) throws AuthenticationException {
        if (encode.matches(password, user.getPassword())) {
            if (!user.isEnabled()) {
                return null;
            }

            List<GrantedAuthority> rights = new ArrayList<>();
            rights.add(new SimpleGrantedAuthority(user.getRole()));

            UsernamePasswordAuthenticationToken token;
            Object principal = authentication.getPrincipal();
            Object credential = authentication.getCredentials();
            token = new UsernamePasswordAuthenticationToken(principal, credential, rights);

            token.setDetails(user);
            user.setToken(token.toString());
            return token;
        } else {
            log.error("The user credentials do not match the database stored values!");
            throw new BadCredentialsException(CommonConstants.BAD_AUTHORIZATION_ERROR_CODE);
        }
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String emailId = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        User user;
        try {
            user = userService.findUserByEmail(emailId);
        } catch (Exception e) {
            log.error("The user with the email id mentioned does not exist!");
            e.printStackTrace();
            throw new BadCredentialsException(CommonConstants.BAD_AUTHORIZATION_ERROR_CODE);
        }
        if (user != null) {
            log.info("Checking if the user's password is correct and assigning rights");
            return checkUser(password, user, authentication);
        } else {
            log.error("The user credentials do not match the database stored values!");
            throw new BadCredentialsException(CommonConstants.BAD_AUTHORIZATION_ERROR_CODE);
        }
    }
}
