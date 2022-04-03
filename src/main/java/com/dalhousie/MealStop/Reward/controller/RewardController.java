package com.dalhousie.MealStop.Reward.controller;

import com.dalhousie.MealStop.Reward.service.IRewardService;
import com.dalhousie.MealStop.common.OrderConstants;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.orders.controller.OrdersPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
