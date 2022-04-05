package com.dalhousie.MealStop.review.controller;

import com.dalhousie.MealStop.customer.builder.CustomerBuilder;
import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.restaurant.service.IRestaurantService;
import com.dalhousie.MealStop.review.builder.CustomerReviewBuilder;
import com.dalhousie.MealStop.review.model.CustomerReview;
import com.dalhousie.MealStop.review.service.ICustomerReviewService;
import com.dalhousie.MealStop.tests_support.TestsSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerReviewControllerTest
{
    @InjectMocks
    CustomerReviewController customerReviewController;

    @Mock
    ICustomerReviewService customerReviewService;

    @Mock
    ICustomerService customerService;

    @Mock
    IRestaurantService restaurantService;

    @Mock
    private BindingResult mockBindingResult;

    private MockMvc mockMvc;

    private Customer customer;

    private Restaurant restaurant;

    private CustomerReview customerReview;

    private List<CustomerReview> customerReviewList;

    private CustomerBuilder customerBuilder;

    private CustomerReviewBuilder customerReviewBuilder;

    private final TestsSupport testsSupport = new TestsSupport();

    @BeforeEach
    void setUp()
    {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerReviewController).build();

        customerBuilder = new CustomerBuilder();
        customerBuilder.setId(1L);
        customerBuilder.setFirstName("Shathish");
        customerBuilder.setLastName("Annamalai");
        customerBuilder.setEmail("abc@gmail.com");
        customerBuilder.setAddress("Halifax, NS, Canada");
        customerBuilder.setMobileNumber("9898989898");
        customerBuilder.setDateOfBirth("March 10, 2021");
        customerBuilder.setTokens(10);
        customer = customerBuilder.buildCustomer();

        restaurant = testsSupport.createDummyRestaurant();
        restaurant.setId(1L);

        customerReviewBuilder = new CustomerReviewBuilder();
        customerReviewBuilder.setId(1L);
        customerReviewBuilder.setReviewScore(5);
        customerReviewBuilder.setReviewMessage("Good");
        customerReviewBuilder.setReviewDate(new Date());
        customerReviewBuilder.setCustomer(customer);
        customerReviewBuilder.setRestaurant(restaurant);
        customerReview = customerReviewBuilder.buildCustomerReview();

        customerReviewList = new ArrayList<CustomerReview>();
        customerReviewList.add(customerReview);
    }

    @AfterEach
    void tearDown()
    {
        customer=null;
        restaurant=null;
        customerReview=null;
        customerReviewList=null;
        customerBuilder=null;
    }

    @Test
    void getReviewPage() throws Exception
    {
        Mockito.lenient().when(customerService.getCustomerDetailsFromSession()).thenReturn(customer);
        Mockito.lenient().when(customerReviewService.getReviewsOfCustomer(customer)).thenReturn(customerReviewList);
        mockMvc.perform(get("/customer/reviews"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("reviews", customerReviewList));
    }

    @Test
    public void getAddReviewPage() throws Exception
    {
        Mockito.lenient().when(restaurantService.getRestaurantById(1L)).thenReturn(restaurant);
        mockMvc.perform(get("/customer/add_review/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attribute("restaurant", restaurant));
    }

    @Test
    public void getUpdateReviewPage() throws Exception
    {
        Mockito.lenient().when(customerReviewService.getReviewById(1L)).thenReturn(customerReview);
        mockMvc.perform(get("/customer/update_review/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attribute("review", customerReview));
    }

    @Test
    public void addReview() throws Exception
    {
        Mockito.lenient().when(customerService.getCustomerDetailsFromSession()).thenReturn(customer);
        Mockito.lenient().when(restaurantService.getRestaurantById(1L)).thenReturn(restaurant);
        Mockito.lenient().doNothing().when(customerReviewService).addReview(customerReview);
        Mockito.lenient().when(mockBindingResult.hasErrors()).thenReturn(false);
        mockMvc.perform(post("/customer/add_review/{id}", 1L)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .requestAttr("review", customerReview))
                        .andExpect(status().isFound());
        Model model = null;
        assertEquals("redirect:/customer/reviews", customerReviewController.addReview(customerReview, 1L));
    }

    @Test
    public void updateReview() throws Exception
    {
        Mockito.lenient().doNothing().when(customerReviewService).updateReview(1L, customerReview);
        Mockito.lenient().when(mockBindingResult.hasErrors()).thenReturn(false);
        mockMvc.perform(post("/customer/update_review/{id}", 1L)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .requestAttr("review", customerReview))
                .andExpect(status().isFound());
        Model model = null;
        assertEquals("redirect:/customer/reviews", customerReviewController.updateReview( 1L, customerReview));
    }

    @Test
    public void deleteReview() throws Exception
    {
        Mockito.lenient().doNothing().when(customerReviewService).deleteReviewById(1L);
        mockMvc.perform(post("/customer/update_review/{id}", 1L))
                .andExpect(status().isFound());
        Model model = null;
        assertEquals("redirect:/customer/reviews", customerReviewController.deleteReview( 1L));
    }
}
