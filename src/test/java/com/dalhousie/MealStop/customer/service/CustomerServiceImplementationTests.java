package com.dalhousie.MealStop.customer.service;

import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.customer.repository.CustomerRepository;
import com.dalhousie.MealStop.domainconstants.MealStopConstants;
import com.dalhousie.MealStop.user.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplementationTests
{
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImplementation customerService;

    private Customer customer1;

    private Customer customer2;

    private User user;

    List<Customer> customerList;

    @BeforeEach
    void setUp()
    {
        user = new User(1L, "shathish", "annamalai", "abc@gmail.com", "9898989898", "March 10, 2021", "Halifax, NS, Canada", "password", "ROLE_CUSTOMER", true, null);
        customer1 = new Customer(user);
        customer2 = new Customer("Test", "User","Test@gmail.com");
        customer2.setId(2L);

        customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);
    }

    @AfterEach
    void tearDown()
    {
        customer1 = customer2 = null;
        customerList = null;
    }

    @Test
    void addCustomer()
    {
        when(customerRepository.save(any())).thenReturn(customer1);
        customerService.addCustomer(customer1);
        verify(customerRepository,times(1)).save(any());
    }

    @Test
    void getAllCustomer()
    {
        customerRepository.save(customer1);
        when(customerRepository.findAll()).thenReturn(customerList);
        List<Customer> customerList1 = customerService.getAllCustomers();
        assertEquals(customerList, customerList1);
        verify(customerRepository, times(1)).save(customer1);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerById()
    {
        java.util.Optional<Customer> customer = java.util.Optional.of(new Customer());
        when(customerRepository.findById(1L)).thenReturn(customer);

        String customerId = new Long(customer1.getId()).toString();
        assertThat(customerService.getCustomerById(customerId)).isEqualTo(customer.get());
    }

    void setDummyUserInSession()
    {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getDetails()).thenReturn(user);
    }

    /*
    @Test
    void getCustomerDetailsFromSession()
    {
        setDummyUserInSession();
        Customer loggedInCustomer = customerService.getCustomerDetailsFromSession();
        assertThat(loggedInCustomer.getEmail()).isEqualTo(user.getUsername());
    }

    @Test
    public void getLoggedInCustomerId()
    {
        setDummyUserInSession();
        Customer loggedInCustomer = customerService.getCustomerDetailsFromSession();
        assertThat(loggedInCustomer.getId()).isEqualTo(user.getUser_id());
    }

    @Test
    public void getCustomerTokenCount()
    {
        setDummyUserInSession();
        Customer loggedInCustomer = customerService.getCustomerDetailsFromSession();
        assertThat(loggedInCustomer.getTokens()).isEqualTo(MealStopConstants.CUSTOMER_DEFAULT_TOKENS);
    }

    @Test
    public void decrementCustomerToken()
    {
        setDummyUserInSession();
        Integer currentTokens = customerService.getCustomerTokenCount();
        Integer updatedToken = customerService.decrementCustomerToken(2);
        assertEquals(currentTokens-2, updatedToken);
    }*/
}
