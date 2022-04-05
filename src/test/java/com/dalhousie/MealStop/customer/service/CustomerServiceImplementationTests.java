package com.dalhousie.MealStop.customer.service;

import com.dalhousie.MealStop.common.CommonConstants;
import com.dalhousie.MealStop.customer.builder.CustomerBuilder;
import com.dalhousie.MealStop.customer.model.UserSearch;
import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.customer.repository.CustomerRepository;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    private CustomerServiceImpl customerService;

    private Customer customer1;

    private Customer customer2;

    private User user;

    private List<Customer> customerList;

    private UserSearch userSearch;

    private CustomerBuilder customerBuilder;

    @BeforeEach
    void setUp()
    {
        user = new User(1L, "Shathish", "Annamalai", "abc@gmail.com", "9898989898", "March 10, 2021", "Halifax, NS, Canada", "password", "ROLE_CUSTOMER", true, null);
        customer1 = new Customer(user);
        customer1.setId(1L);

        customerBuilder = new CustomerBuilder();
        customerBuilder.setId(1L);
        customerBuilder.setFirstName("Shathish");
        customerBuilder.setLastName("Annamalai");
        customerBuilder.setEmail("abc@gmail.com");
        customerBuilder.setAddress("Halifax, NS, Canada");
        customerBuilder.setMobileNumber("9898989898");
        customerBuilder.setDateOfBirth("March 10, 2021");
        customerBuilder.setTokens(10);
        customer2 = customerBuilder.buildCustomer();

        customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);

        userSearch = new UserSearch();
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
        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(customer1));
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getDetails()).thenReturn(user);
    }


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
        Long customerId = customerService.getLoggedInCustomerId();
        assertThat(customerId).isEqualTo(user.getUser_id());
    }

    @Test
    public void getCustomerTokenCount()
    {
        setDummyUserInSession();
        Customer loggedInCustomer = customerService.getCustomerDetailsFromSession();
        assertThat(loggedInCustomer.getTokens()).isEqualTo(CommonConstants.CUSTOMER_DEFAULT_TOKENS);
    }

    @Test
    public void decrementCustomerToken()
    {
        setDummyUserInSession();
        Integer currentTokens = customerService.getCustomerTokenCount();
        Integer updatedToken = customerService.decrementCustomerToken(2);
        assertEquals(currentTokens-2, updatedToken);
    }

    @Test
    public void incrementCustomerToken()
    {
        setDummyUserInSession();
        Integer currentTokens = customerService.getCustomerTokenCount();
        customerService.incrementCustomerToken(2);
        assertEquals(customer1.getTokens(), currentTokens+2);
    }

    @Test
    void getCustomerInstanceFromUser()
    {
        java.util.Optional<Customer> customer = java.util.Optional.of(new Customer());
        when(customerRepository.findById(1L)).thenReturn(customer);
        assertThat(customerService.getCustomerInstanceFromUser(user)).isEqualTo(customer.get());
    }

    @Test
    void addCustomerFromUser()
    {
        when(customerRepository.save(any())).thenReturn(customer1);
        customerService.addCustomer(user);
        verify(customerRepository,times(1)).save(any());
    }

    @Test
    public void getterSetterTest()
    {
        customer1.setFirstName("Shathish");
        customer1.setLastName("Annamalai");
        customer1.setEmail("abc@gmail.com");
        customer1.setAddress("Halifax, NS, Canada");
        customer1.setMobileNumber("9898989898");
        customer1.setDateOfBirth("March 10, 2021");
        customer1.setTokens(10);

        assertThat(customer1.getId()).isEqualTo(1L);
        assertThat(customer1.getFirstName()).isEqualTo("Shathish");
        assertThat(customer1.getLastName()).isEqualTo("Annamalai");
        assertThat(customer1.getEmail()).isEqualTo("abc@gmail.com");
        assertThat(customer1.getAddress()).isEqualTo("Halifax, NS, Canada");
        assertThat(customer1.getMobileNumber()).isEqualTo("9898989898");
        assertThat(customer1.getDateOfBirth()).isEqualTo("March 10, 2021");
        assertThat(customer1.getTokens()).isEqualTo(10);

        Date newDate = new Date();
        userSearch.setStartDate(newDate);
        userSearch.setEndDate(newDate);
        assertThat(userSearch.getStartDate()).isEqualTo(newDate);
        assertThat(userSearch.getEndDate()).isEqualTo(newDate);
    }
}
