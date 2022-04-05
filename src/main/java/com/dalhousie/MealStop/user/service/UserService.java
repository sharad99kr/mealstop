package com.dalhousie.MealStop.user.service;

import com.dalhousie.MealStop.common.RoleEnum;
import com.dalhousie.MealStop.common.UserMessagesConstants;
import com.dalhousie.MealStop.common.VerificationTokenConstants;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.ngo.service.INGOService;
import com.dalhousie.MealStop.user.entity.PasswordResetToken;
import com.dalhousie.MealStop.user.entity.User;
import com.dalhousie.MealStop.user.entity.VerificationToken;
import com.dalhousie.MealStop.user.models.UserModel;
import com.dalhousie.MealStop.user.repository.PasswordResetTokenRepository;
import com.dalhousie.MealStop.user.repository.UserRepository;
import com.dalhousie.MealStop.user.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private INGOService ngoService;

    @Override
    public User signUpUser(UserModel userModel) {

        User entityUser = findUserByEmail(userModel.getEmail());

        //If user with the same email exists.
        if (entityUser != null) {
            VerificationToken token = verificationTokenRepository.findVerificationTokenByUser(entityUser);

            // If token is null, then user is already registered with verification done
            // and if toke is not null then, user has registered but verification is not done yet.
            // Delete the previous token and return the user.
            if (token != null) {
                verificationTokenRepository.delete(token);
            }
            return entityUser;
        }

        User user = new User();
        user.setUsername(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setAddress(userModel.getAddress());
        user.setMobileNumber(userModel.getMobileNumber());
        user.setDateOfBirth(userModel.getDateOfBirth());
        user.setRole(userModel.getRole());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userRepository.save(user);

        if (userModel.getRole().equals(String.valueOf(RoleEnum.ROLE_CUSTOMER))) {
            customerService.addCustomer(user);
        } else if (userModel.getRole().equals(String.valueOf(RoleEnum.ROLE_NGO))) {
            ngoService.addNGO(user);
        }

        return user;
    }

    @Override
    public void saveVerificationTokenForUser(User user, String token) {
        VerificationToken verifyToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verifyToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        // If no verification token present.
        if (verificationToken == null) {
            return VerificationTokenConstants.INVALID;
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        // If verification token is present
        // 1. check if verification has not expired.
        if (verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return VerificationTokenConstants.EXPIRED;
        }

        // 2. Enable the user inside the database
        user.setEnabled(true);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
        return VerificationTokenConstants.VALID;
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        passwordResetTokenRepository.save(new PasswordResetToken(user, token));
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) {
            return VerificationTokenConstants.INVALID;
        }

        Calendar calendar = Calendar.getInstance();

        if (passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return VerificationTokenConstants.EXPIRED;
        }

        return VerificationTokenConstants.VALID;
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByUsername(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error(UserMessagesConstants.USER_NOT_FOUND);
            return null;
        } else {
            log.info(UserMessagesConstants.USER_FOUND);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
