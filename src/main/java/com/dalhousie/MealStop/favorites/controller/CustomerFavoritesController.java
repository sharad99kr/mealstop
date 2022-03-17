package com.dalhousie.MealStop.favorites.controller;

import com.dalhousie.MealStop.favorites.modal.CustomerFavorites;
import com.dalhousie.MealStop.favorites.service.CustomerFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CustomerFavoritesController
{
    @Autowired
    private CustomerFavoriteService customerFavoriteService;

    @GetMapping("/customer/favorite")
    public String getFavoritePage(Model model)
    {
        List<CustomerFavorites> customerFavorites = customerFavoriteService.getCustomerFavorites();
        System.err.println(customerFavorites);
        model.addAttribute("favorites", customerFavorites);
        return "favorite/get_favorite";
    }

    @PostMapping("/customer/add_favorite/{id}")
    public String addCustomerFavorite(@PathVariable("id") long restaurantId)
    {
        customerFavoriteService.addRestaurantToCustomerFavorites(restaurantId);
        return "redirect:/customer/favorite";
    }

    @PostMapping("/customer/remove_favorite/{id}")
    public String removeCustomerFavorite(@PathVariable("id") long customerFavoriteId)
    {
        customerFavoriteService.deleteCustomerFavoriteById(customerFavoriteId);
        return "redirect:/customer/favorite";
    }
}