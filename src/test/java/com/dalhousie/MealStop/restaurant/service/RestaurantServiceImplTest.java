package com.dalhousie.MealStop.restaurant.service;

import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.recommendation.service.IRecommendationService;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.restaurant.repository.RestaurantRepository;
import com.dalhousie.MealStop.customer.service.ICustomerService;
import com.dalhousie.MealStop.review.model.CustomerReview;
import com.dalhousie.MealStop.review.service.ICustomerReviewService;
import com.dalhousie.MealStop.tests_support.TestsSupport;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private ICustomerReviewService customerReviewService ;
    @Mock
    private ICustomerService customerService;
    @Mock
    private IRecommendationService recommendationService;

    @Autowired
    @InjectMocks
    private RestaurantServiceImpl restaurantService;
    private Restaurant restaurant1;
    List<Restaurant> restaurantList;
    private Date endDate;
    private Meal meal1;
    List<Meal> mealList;
    User mockUser;
    List<CustomerReview> customerReviews;
    private final TestsSupport testsSupport = new TestsSupport();

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setUser_id(1L);
        restaurantList = new ArrayList<>();
        mealList = new ArrayList<>();
        meal1 = testsSupport.createDummyMeal();
        meal1.setId(1L);
        restaurant1 = testsSupport.createDummyRestaurant();
        restaurant1.setId(1L);
        restaurantList.add(restaurant1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(Date.from(Instant.now()));
        cal.add(Calendar.DATE, 7);
        endDate = cal.getTime();
        mealList.add(meal1);
        customerReviews = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        restaurant1 = null;
        restaurantList = null;
        endDate = null;
        meal1 = null;
        mealList = null;
        customerReviews = null;
    }

    @Test
    void addRestaurant() {
        when(restaurantRepository.save(any())).thenReturn(restaurant1);
        RestaurantServiceImpl restaurantServiceSpy = spy(restaurantService);
        doReturn(mockUser).when(restaurantServiceSpy).getRestaurantUserDetailsFromSession();
        restaurantServiceSpy.addRestaurant(restaurant1);
        verify(restaurantRepository,times(1)).save(any());
    }

    @Test
    void getAllRestaurantByUserId() {
        assertThat(restaurantService.getAllRestaurantByUserId()).isEqualTo(Collections.<Restaurant>emptyList());
    }

    @Test
    void getAverageReviewScore() {
        when(customerReviewService.getReviewsOfRestaurant(restaurant1)).thenReturn(customerReviews);
        assertEquals("No Reviews", restaurantService.getAverageReviewScore(restaurant1));
    }

    @Test
    void updateRestaurant() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.ofNullable(restaurant1));
        restaurantService.updateRestaurant(restaurant1, restaurant1.getId());
        verify(restaurantRepository,times(1)).save(any());
    }

    @Test
    void getAvailableRestaurants() {
        when(restaurantRepository.findAll()).thenReturn(restaurantList);
        assertThat(restaurantService.getAvailableRestaurants(Date.from(Instant.now()), endDate)).isEqualTo(restaurantList);
    }

    @Test
    void getRestaurantById() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.ofNullable(restaurant1));
        assertThat(restaurantService.getRestaurantById(restaurant1.getId())).isEqualTo(restaurant1);
    }

    @Test
    void getRecommendedMealForCustomer() {
        when(customerService.getLoggedInCustomerId()).thenReturn(1L);
        when(recommendationService.getAllRecommendedMeals(1L, restaurantList)).thenReturn(mealList);
        assertEquals(1, restaurantService.getRecommendedMealForCustomer(restaurantList).size());
    }

    @Test
    void getRestaurantUserDetailsFromSession() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getDetails()).thenReturn(mockUser);
        assertThat(restaurantService.getRestaurantUserDetailsFromSession()).isEqualTo(mockUser);
    }

    @Test
    void getRestaurantReviews(){
        Mockito.lenient().when(customerReviewService.getReviewsOfRestaurant(restaurant1)).thenReturn(customerReviews);
        assertEquals(0, restaurantService.getRestaurantReviews(1L).size());
    }
}