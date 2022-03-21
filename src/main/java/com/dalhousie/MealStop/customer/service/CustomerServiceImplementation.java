package com.dalhousie.MealStop.customer.service;

import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.repository.CustomerRepository;
import com.dalhousie.MealStop.user.entity.User;
import com.dalhousie.MealStop.user.models.UserModel;
import com.dalhousie.MealStop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImplementation implements ICustomerService
{
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserService userService;

    @Override
    public Customer getCustomerById(String id)
    {
        Long customerId = Long.parseLong(id);
        Optional<Customer> customer = customerRepository.findById(customerId);
        return customer.isPresent() ? customer.get() : null;
    }

    @Override
    public Customer getCustomerDetailsFromSession()
    {
        try
        {
            System.err.println(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getDetails();

            System.err.println(user);

            Customer customer = new Customer(user);
            return customer;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;

        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        Customer customer = null;
        try
        {
            user = (User) auth.getDetails();
            customer = new Customer(user);
        }
        catch(Exception e)
        {
            System.err.println("User trying to access url without login");
            e.printStackTrace();
        }
        return customer;
        */
    }

    @Override
    public Long getLoggedInCustomerId()
    {
        Customer loggedInCustomer = getCustomerDetailsFromSession();
        if(loggedInCustomer!=null)
        {
            return loggedInCustomer.getId();
        }
        return null;
    }


    @Override
    public void addCustomer(User user)
    {
        Customer customer = new Customer(user);
        customerRepository.save(customer);
    }
}
