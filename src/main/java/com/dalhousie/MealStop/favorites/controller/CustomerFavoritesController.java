package com.dalhousie.MealStop.favorites.controller;

import com.dalhousie.MealStop.favorites.modal.CustomerFavorites;
import com.dalhousie.MealStop.favorites.service.CustomerFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerFavoritesController
{
    @Autowired
    private CustomerFavoriteService customerFavoriteService;

    @GetMapping("/customer/favorite")
    public String getFavoritePage(Model model)
    {
        CustomerFavorites customerFavorites = customerFavoriteService.getCustomerFavorites();
        model.addAttribute("favorites", customerFavorites);
        return "favorite/get_favorite";
    }

    @PostMapping("/customer/add_favorite/{id}")
    public String addCustomerFavorite(@PathVariable("id") long restaurantId)
    {

        return "redirect:/favorite/get_favorite";
    }
}
