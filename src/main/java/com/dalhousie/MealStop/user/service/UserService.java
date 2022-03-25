package com.dalhousie.MealStop.user.service;

import com.dalhousie.MealStop.common.UserMessagesConstants;
import com.dalhousie.MealStop.common.VerificationTokenConstants;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    @Override
    public User signUpUser(UserModel userModel) {
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
        return user;
    }

    /**
     * Saves a new verification token sent with registration.
     *
     * @param user  user information who is being registered.
     * @param token token to be saved for registration
     */
    @Override
    public void saveVerificationTokenForUser(User user, String token) {
        VerificationToken verifyToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verifyToken);
    }

    /**
     * Validates the verification token and if verifies enables the user in the system.
     *
     * @param token incoming verification token
     * @return status of verification
     */
    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        // If no verification token present.
        if (verificationToken == null)
            return VerificationTokenConstants.INVALID;

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
        return VerificationTokenConstants.VALID;
    }

    /**
     * Updates the password reset token for user password reset.
     *
     * @param user  user information
     * @param token token to be updated in the database
     */
    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        passwordResetTokenRepository.save(new PasswordResetToken(user, token));
    }

    /**
     * Validates the verification token for password reset.
     *
     * @param token verification token
     * @return status of validity
     */
    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null)
            return VerificationTokenConstants.INVALID;

        Calendar calendar = Calendar.getInstance();

        if (passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return VerificationTokenConstants.EXPIRED;
        }

        return VerificationTokenConstants.VALID;
    }

    /**
     * Gets the user against the token passed.
     *
     * @param token reset password token
     * @return user if user is found for the given reset token
     */
    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    /**
     * Change the new password for a user.
     *
     * @param user        user information
     * @param newPassword new password to be saved against the given user
     */
    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * To update the old password, checks if old password passed is same as in the system.
     *
     * @param user        user whose password will be changed
     * @param oldPassword password that needs to be checked
     * @return
     */
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
