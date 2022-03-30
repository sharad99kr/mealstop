package com.dalhousie.MealStop.ngo.service;

import com.dalhousie.MealStop.ngo.modal.NGO;
import com.dalhousie.MealStop.ngo.repository.NGORepository;
import com.dalhousie.MealStop.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NGOServiceImpl implements INGOService {
    @Autowired
    private NGORepository ngoRepository;

    @Override
    public NGO getNGOById(String id) {
        Long ngoId = Long.parseLong(id);
        Optional<NGO> ngo = ngoRepository.findById(ngoId);
        return ngo.isPresent() ? ngo.get() : null;
    }
}