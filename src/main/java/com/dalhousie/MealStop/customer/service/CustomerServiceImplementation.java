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
        Customer customer = null;
        try
        {
            System.err.println(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getDetails();

            System.err.println(user);

            customer = new Customer(user);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return customer;
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
    public Integer getCustomerTokenCount()
    {
        Customer loggedInCustomer = getCustomerDetailsFromSession();
        return loggedInCustomer.getTokens();
    }

    @Override
    public void decrementCustomerToken(Integer decrementTokenCount)
    {
        Customer loggedInCustomer = getCustomerDetailsFromSession();
        Integer currentTokenCount = getCustomerTokenCount();

        if(currentTokenCount >= decrementTokenCount)
        {
            Integer updatedTokenCount = currentTokenCount-decrementTokenCount;
            loggedInCustomer.setTokens(updatedTokenCount);
        }
    }

    @Override
    public void addCustomer(User user)
    {
        Customer customer = new Customer(user);
        customerRepository.save(customer);
    }
}
