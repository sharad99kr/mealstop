package com.dalhousie.MealStop.customer.service;

import com.dalhousie.MealStop.Meal.model.Meal;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.repository.CustomerRepository;
import com.dalhousie.MealStop.user.entity.User;
import com.dalhousie.MealStop.user.models.UserModel;
import com.dalhousie.MealStop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Customer> getAllCustomers()
    {
        List<Customer> customerList = customerRepository.findAll();
        return customerList;
    }

    @Override
    public Customer getCustomerDetailsFromSession()
    {
        Customer customer = null;
        try
        {
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getDetails();
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
    public Integer decrementCustomerToken(Integer decrementTokenCount)
    {
        Customer loggedInCustomer = getCustomerDetailsFromSession();
        Integer currentTokenCount = getCustomerTokenCount();

        if(currentTokenCount >= decrementTokenCount)
        {
            Integer updatedTokenCount = currentTokenCount-decrementTokenCount;
            loggedInCustomer.setTokens(updatedTokenCount);
            return updatedTokenCount;
        }
        customerRepository.save(loggedInCustomer);
        return -1;
    }

    @Override
    public void addCustomer(User user)
    {
        Customer newCustomer = new Customer(user);
        addCustomer(newCustomer);
    }

    public void addCustomer(Customer newCustomer)
    {
        customerRepository.save(newCustomer);
    }
}
