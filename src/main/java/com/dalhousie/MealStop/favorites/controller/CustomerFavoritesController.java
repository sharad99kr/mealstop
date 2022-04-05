package com.dalhousie.MealStop.favorites.controller;

import com.dalhousie.MealStop.favorites.model.CustomerFavorites;
import com.dalhousie.MealStop.favorites.service.ICustomerFavoriteService;
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
    private ICustomerFavoriteService ICustomerFavoriteService;

    @GetMapping("/customer/favorite")
    public String getFavoritePage(Model model)
    {
        List<CustomerFavorites> customerFavorites = ICustomerFavoriteService.getCustomerFavorites();
        model.addAttribute("favorites", customerFavorites);
        return "favorite/get_favorite";
    }

    @PostMapping("/customer/add_favorite/{id}")
    public String addCustomerFavorite(@PathVariable("id") long restaurantId)
    {
        ICustomerFavoriteService.addRestaurantToCustomerFavorites(restaurantId);
        return "redirect:/customer/favorite";
    }

    @PostMapping("/customer/remove_favorite/{id}")
    public String removeCustomerFavorite(@PathVariable("id") long customerFavoriteId)
    {
        ICustomerFavoriteService.deleteCustomerFavoriteById(customerFavoriteId);
        return "redirect:/customer/favorite";
    }
}