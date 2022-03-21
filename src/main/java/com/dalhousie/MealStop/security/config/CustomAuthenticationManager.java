package com.dalhousie.MealStop.security.config;

import com.dalhousie.MealStop.user.entity.User;
import com.dalhousie.MealStop.user.service.UserService;
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
public class CustomAuthenticationManager implements AuthenticationManager
{
    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder encode = new BCryptPasswordEncoder();

    Authentication checkUser(String password, User user, Authentication authentication) throws AuthenticationException
    {
        if (encode.matches(password, user.getPassword()))
        {
            List<GrantedAuthority> rights = new ArrayList<GrantedAuthority>();
            rights.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), rights);
            token.setDetails(user);
            user.setToken(token.toString());

            return token;
        }
        else
        {
            System.err.println("The user credentials do not match the database stored values!");
            throw new BadCredentialsException("1000");
        }
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        String emailId = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        User user = new User();
        user.setUsername(emailId);

        System.err.println("email id " + emailId);

        try
        {
            user = userService.findUserByEmail(emailId);
        }
        catch (Exception e)
        {
            System.err.println("The user with the email id mentioned does not exist!");
            e.printStackTrace();
            throw new BadCredentialsException("1000");
        }
        if (user != null)
        {
            System.err.println("Checking if the user's password is correct and assigning rights");
            return checkUser(password, user, authentication);
        }
        else
        {
            System.err.println("The user credentials do not match the database stored values!");
            throw new BadCredentialsException("1000");
        }
    }
}
