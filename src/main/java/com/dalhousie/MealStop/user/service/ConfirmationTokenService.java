package com.dalhousie.MealStop.user.service;

import com.dalhousie.MealStop.user.dbmodels.ConfirmationToken;
import com.dalhousie.MealStop.user.repository.IConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {
    @Autowired
    private IConfirmationTokenRepository IConfirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        IConfirmationTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return IConfirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return IConfirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

}
