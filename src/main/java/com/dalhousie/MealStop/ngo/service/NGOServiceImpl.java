package com.dalhousie.MealStop.ngo.service;

import com.dalhousie.MealStop.common.NGOConstants;
import com.dalhousie.MealStop.email.IEmailService;
import com.dalhousie.MealStop.ngo.model.NGO;
import com.dalhousie.MealStop.ngo.repository.NGORepository;
import com.dalhousie.MealStop.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NGOServiceImpl implements INGOService {
    @Autowired
    private NGORepository ngoRepository;

    @Autowired
    private IEmailService emailService;

    @Override
    public NGO getNGOById(String id) {
        Long ngoId = Long.parseLong(id);
        Optional<NGO> ngo = ngoRepository.findById(ngoId);
        return ngo.isPresent() ? ngo.get() : null;
    }

    @Override
    public NGO getNGODetailsFromSession()
    {
        NGO NgoUser = null;
        try
        {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
            NgoUser = new NGO(user);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return NgoUser;
    }

    @Override
    public Long getLoggedInNGOId()
    {
        NGO NgoUser = getNGODetailsFromSession();
        if(NgoUser!=null)
        {
            return NgoUser.getId();
        }
        return null;
    }

    @Override
    public void addNGO(User user)
    {
        NGO ngo = new NGO(user);
        ngoRepository.save(ngo);
    }

    @Override
    public void sendCancelledOrderNotification(String mealName)
    {
        List<NGO> ngoList = ngoRepository.findAll();
        String subject = NGOConstants.NGO_NOTIFICATION_SUBJECT;


        ngoList.forEach(ngo->{
            String emailId = ngo.getEmail();
            String ngoName = ngo.getName();
            String content = NGOConstants.getNgoNotificationContent(ngoName, mealName);
            emailService.sendEmail(emailId,content, subject);
            log.info("Sending cancelled order notification mail to NGO "+emailId);
        });
    }
}