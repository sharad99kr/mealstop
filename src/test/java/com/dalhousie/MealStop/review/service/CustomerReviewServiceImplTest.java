package com.dalhousie.MealStop.review.service;

import com.dalhousie.MealStop.customer.builder.CustomerBuilder;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.review.builder.CustomerReviewBuilder;
import com.dalhousie.MealStop.review.model.CustomerReview;
import com.dalhousie.MealStop.review.repository.CustomerReviewRepository;
import com.dalhousie.MealStop.tests_support.TestsSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CustomerReviewServiceImplTest {

    @Mock
    CustomerReviewRepository customerReviewRepository;

    @InjectMocks
    CustomerReviewServiceImpl customerReviewService;

    private CustomerReview customerReview;

    private List<CustomerReview> customerReviewList;

    private Restaurant restaurant1;

    private Customer customer;

    private CustomerBuilder customerBuilder;

    private CustomerReviewBuilder customerReviewBuilder;

    private final TestsSupport testsSupport = new TestsSupport();

    @BeforeEach
    void setUp()
    {
        Date date =new Date();
        restaurant1 = testsSupport.createDummyRestaurant();
        restaurant1.setId(1L);

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

        customerReviewBuilder = new CustomerReviewBuilder();
        customerReviewBuilder.setId(1L);
        customerReviewBuilder.setReviewScore(5);
        customerReviewBuilder.setReviewMessage("Good");
        customerReviewBuilder.setReviewDate(date);
        customerReviewBuilder.setCustomer(customer);
        customerReviewBuilder.setRestaurant(restaurant1);
        customerReview = customerReviewBuilder.buildCustomerReview();


        customerReviewList = new ArrayList<>();
        customerReviewList.add(customerReview);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addReview() {
        when(customerReviewRepository.save(any())).thenReturn(customerReview);
        customerReviewService.addReview(customerReview);
        verify(customerReviewRepository,times(1)).save(any());
    }

    @Test
    void getReviewById() {
        customerReviewRepository.save(customerReview);
        Mockito.lenient().when(customerReviewRepository.getById(1L)).thenReturn(customerReview);
        customerReviewService.getReviewById(1L);
        verify(customerReviewRepository,times(1)).getById(1L);
    }

    @Test
    void getAllReviews() {
        when(customerReviewRepository.findAll()).thenReturn(customerReviewList);
        List<CustomerReview> reviewList = customerReviewService.getAllReviews();
        assertEquals(reviewList, customerReviewList);
    }

    @Test
    void getReviewsOfCustomer() {
        when(customerReviewRepository.findByCustomer(customer)).thenReturn(customerReviewList);
        List<CustomerReview> reviewList = customerReviewService.getReviewsOfCustomer(customer);
        assertEquals(reviewList, customerReviewList);
    }

    @Test
    void getReviewsOfRestaurant() {
        when(customerReviewRepository.findByRestaurant(restaurant1)).thenReturn(customerReviewList);
        List<CustomerReview> reviewList = customerReviewService.getReviewsOfRestaurant(restaurant1);
        assertEquals(reviewList, customerReviewList);
    }

    @Test
    void updateReview() {
        when(customerReviewRepository.save(any())).thenReturn(customerReview);
        customerReviewService.addReview(customerReview);

        customerReview.setReviewScore(2);
        Mockito.lenient().when(customerReviewRepository.getById(1L)).thenReturn(customerReview);
        customerReviewService.updateReview(1L, customerReview);
        verify(customerReviewRepository,times(2)).save(any());
    }

    @Test
    void deleteReviewById() {
        doNothing().when(customerReviewRepository).deleteById(1L);
        customerReviewService.deleteReviewById(1L);
        verify(customerReviewRepository, times(1)).deleteById(1L);
    }

    @Test
    void getterSetterTest()
    {
        Date curDate = new Date();
        CustomerReview customerReview = new CustomerReview();
        customerReview.setId(1L);
        customerReview.setCustomer(customer);
        customerReview.setRestaurant(restaurant1);
        customerReview.setReviewScore(5);
        customerReview.setReviewMessage("good food");
        customerReview.setReviewDate(curDate);

        assertEquals(customerReview.getId(), 1L);
        assertEquals(customerReview.getCustomer(), customer);
        assertEquals(customerReview.getRestaurant(), restaurant1);
        assertEquals(customerReview.getReviewScore(), 5);
        assertEquals(customerReview.getReviewMessage(), "good food");
        assertEquals(customerReview.getReviewDate(), curDate);
    }
}