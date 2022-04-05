package com.dalhousie.MealStop.customer.service;

import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.customer.repository.CustomerRepository;
import com.dalhousie.MealStop.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImpl implements ICustomerService
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
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerDetailsFromSession()
    {
        Customer customer = null;
        try
        {
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getDetails();
            customer = getCustomerInstanceFromUser(user);
        }
        catch(Exception e)
        {
            log.error(e.getMessage());
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
    public void incrementCustomerToken(Integer tokenCount)
    {
        Customer loggedInCustomer = getCustomerDetailsFromSession();
        Integer currentCount = getCustomerTokenCount();
        loggedInCustomer.setTokens(currentCount+tokenCount);
        customerRepository.save(loggedInCustomer);
    }

    @Override
    public void addCustomer(User user)
    {
        Customer newCustomer = new Customer(user);
        addCustomer(newCustomer);
    }

    @Override
    public void addCustomer(Customer newCustomer)
    {
        customerRepository.save(newCustomer);
    }

    @Override
    public Customer getCustomerInstanceFromUser(User user)
    {
        Long customerId = user.getUser_id();
        return getCustomerById(customerId.toString());
    }
}
