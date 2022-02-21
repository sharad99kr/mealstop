package com.dalhousie.MealStop.user.service;

import com.dalhousie.MealStop.user.dbmodels.ILogin;
import com.dalhousie.MealStop.user.dbmodels.IUser;
import com.dalhousie.MealStop.user.dbmodels.Login;
import com.dalhousie.MealStop.user.dbmodels.User;

import com.dalhousie.MealStop.user.repository.ILoginRepository;
import com.dalhousie.MealStop.user.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ILoginRepository loginRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var login = this.loginRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(""));
        return null;
    }

    public UserDetails loadUserByUserId(long userId) {
        var login = this.userRepository.findByUserid(userId).orElseThrow(() -> new UsernameNotFoundException(""));
        return null;
    }

    public boolean signUpUser(IUser user, ILogin login) {
        IUser savedUser = this.userRepository.save((User) user);
        if (savedUser == null)
            return false;
        login.setUser(savedUser);
        ILogin savedLogin = this.loginRepository.save((Login) login);
        if (savedLogin == null)
            return false;
        return true;
    }
}
