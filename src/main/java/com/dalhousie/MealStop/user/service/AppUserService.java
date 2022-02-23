package com.dalhousie.MealStop.user.service;

import com.dalhousie.MealStop.user.dbmodels.*;

import com.dalhousie.MealStop.user.repository.ILoginRepository;
import com.dalhousie.MealStop.user.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AppUserService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ILoginRepository loginRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var login = this.loginRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(""));
        //map user details to login
        return null;
    }

    public UserDetails loadUserByUserId(long userId) {
        //var login = this.userRepository.findByUserid(userId).orElseThrow(() -> new UsernameNotFoundException(""));
        return null;
    }

    public UserDetails loadUserByEmail(String email) {
        var login = this.userRepository.findByEmail(email).orElse(null);
        return null;
    }

    public String signUpUser(IUser user, ILogin login) {

        //Check if a user with similar email and username already exists
        boolean userEmailExists = this.userRepository.findByEmail(user.getEmail()).isPresent();
        boolean userNameExists = this.loginRepository.findByUsername(login.getUserName()).isPresent();

        if(userEmailExists)
            throw new IllegalStateException("User email already taken.");

        if(userNameExists)
            throw new IllegalStateException("User name already taken.");

        //Encode the password
        login.setPassword(bCryptPasswordEncoder.encode(login.getPassword()));

        IUser savedUser = this.userRepository.save((User) user);
        if (savedUser == null)
            return null;
        login.setUser(user);
        ILogin savedLogin = this.loginRepository.save((Login) login);
        if (savedLogin == null)
            return null;

        //Confirmation token
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), (User)savedUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        //Send email
        return token;
    }

    public int enableAppUser(String email) {
        User user = userRepository.findByEmail(email).get();
        return loginRepository.enableAppUser(user.getUserId());
    }
}
