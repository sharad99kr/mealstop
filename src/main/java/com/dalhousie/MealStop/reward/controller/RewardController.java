package com.dalhousie.MealStop.reward.controller;

import com.dalhousie.MealStop.reward.service.IRewardService;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RewardController {

    @Autowired
    private IRewardService rewardService;

    @Autowired
    private ICustomerService customerService;

    @PostMapping("/reward/redeem")
    String redeemPoints(Model model) {
        long id = customerService.getCustomerDetailsFromSession().getId();
        rewardService.redeemRewardPoints(id);
        return "redirect:/customer/profile";

    }

}
