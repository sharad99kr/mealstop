package com.dalhousie.MealStop.ngo.service;

import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.ngo.modal.NGO;
import com.dalhousie.MealStop.ngo.repository.NGORepository;
import com.dalhousie.MealStop.user.model.User;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class NGOServiceImpl implements INGOService{
    @Autowired
    private NGORepository ngoRepository;

    @Override
    public NGO getNGOById(String id)
    {
        Long ngoId = Long.parseLong(id);
        Optional<NGO> ngo = ngoRepository.findById(ngoId);
        return ngo.isPresent() ? ngo.get() : null;
    }




    @Override
    public void addNGO(User user)
    {
        NGO ngo = new NGO(user);
        ngoRepository.save(ngo);
    }

}
