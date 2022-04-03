package com.dalhousie.MealStop.integration_tests;

import com.dalhousie.MealStop.customer.builder.CustomerBuilder;
import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.review.builder.CustomerReviewBuilder;
import com.dalhousie.MealStop.review.model.CustomerReview;
import com.dalhousie.MealStop.review.repository.CustomerReviewRepository;
import com.dalhousie.MealStop.tests_support.TestsSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerReviewRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerReviewRepository customerReviewRepository;

    private TestsSupport testsSupport = new TestsSupport();

    @Test
    public void ShouldReturnReviewsListWhenFindAllOrFindByCustomerOrFindByRestaurant() {
        CustomerBuilder customerBuilder = new CustomerBuilder();
        customerBuilder.setId(1L);
        customerBuilder.setFirstName("Shathish");
        customerBuilder.setLastName("Annamalai");
        customerBuilder.setEmail("abc@gmail.com");
        customerBuilder.setAddress("Halifax, NS, Canada");
        customerBuilder.setMobileNumber("9898989898");
        customerBuilder.setDateOfBirth("March 10, 2021");
        customerBuilder.setTokens(10);
        Customer customer = customerBuilder.buildCustomer();

        Restaurant restaurant = testsSupport.createDummyRestaurant();
        restaurant.setId(1L);

        CustomerReviewBuilder customerReviewBuilder = new CustomerReviewBuilder();
        customerReviewBuilder.setId(1L);
        customerReviewBuilder.setReviewScore(5);
        customerReviewBuilder.setReviewMessage("Good");
        customerReviewBuilder.setReviewDate(new Date());
        customerReviewBuilder.setCustomer(customer);
        customerReviewBuilder.setRestaurant(restaurant);
        CustomerReview customerReview = customerReviewBuilder.buildCustomerReview();

        customer = entityManager.merge(customer);
        entityManager.flush();
        restaurant = entityManager.merge(restaurant);
        entityManager.flush();
        customerReview.setCustomer(customer);
        customerReview.setRestaurant(restaurant);
        entityManager.merge(customerReview);
        entityManager.flush();

        List<CustomerReview> customerReviews = customerReviewRepository.findAll();
        assertThat(customerReviews.size())
                .isGreaterThan(0);
        List<CustomerReview> reviews = customerReviewRepository.findByCustomer(customer);
        assertThat(reviews.size())
                .isGreaterThan(0);
        List<CustomerReview> reviewsRestaurant = customerReviewRepository.findByRestaurant(restaurant);
        assertThat(reviewsRestaurant.size())
                .isGreaterThan(0);
    }

    @Test
    public void ShouldReturnFavoriteWhenSave() {
        CustomerBuilder customerBuilder = new CustomerBuilder();
        customerBuilder.setId(1L);
        customerBuilder.setFirstName("Shathish");
        customerBuilder.setLastName("Annamalai");
        customerBuilder.setEmail("abc@gmail.com");
        customerBuilder.setAddress("Halifax, NS, Canada");
        customerBuilder.setMobileNumber("9898989898");
        customerBuilder.setDateOfBirth("March 10, 2021");
        customerBuilder.setTokens(10);
        Customer customer = customerBuilder.buildCustomer();

        Restaurant restaurant = testsSupport.createDummyRestaurant();
        restaurant.setId(1L);

        CustomerReviewBuilder customerReviewBuilder = new CustomerReviewBuilder();
        customerReviewBuilder.setId(1L);
        customerReviewBuilder.setReviewScore(5);
        customerReviewBuilder.setReviewMessage("Good");
        customerReviewBuilder.setReviewDate(new Date());
        customerReviewBuilder.setCustomer(customer);
        customerReviewBuilder.setRestaurant(restaurant);
        CustomerReview customerReview = customerReviewBuilder.buildCustomerReview();

        customer = entityManager.merge(customer);
        entityManager.flush();
        restaurant = entityManager.merge(restaurant);
        entityManager.flush();
        customerReview.setCustomer(customer);
        customerReview.setRestaurant(restaurant);
        entityManager.merge(customerReview);
        entityManager.flush();

        customerReviewRepository.save(customerReview);
        assertThat(customerReviewRepository.getById(customerReview.getId())).isNotNull();
    }

    @Test
    public void ShouldReturnNothingWhenFavoriteIsDeleted() {
        CustomerBuilder customerBuilder = new CustomerBuilder();
        customerBuilder.setId(1L);
        customerBuilder.setFirstName("Shathish");
        customerBuilder.setLastName("Annamalai");
        customerBuilder.setEmail("abc@gmail.com");
        customerBuilder.setAddress("Halifax, NS, Canada");
        customerBuilder.setMobileNumber("9898989898");
        customerBuilder.setDateOfBirth("March 10, 2021");
        customerBuilder.setTokens(10);
        Customer customer = customerBuilder.buildCustomer();

        Restaurant restaurant = testsSupport.createDummyRestaurant();
        restaurant.setId(1L);

        CustomerReviewBuilder customerReviewBuilder = new CustomerReviewBuilder();
        customerReviewBuilder.setId(1L);
        customerReviewBuilder.setReviewScore(5);
        customerReviewBuilder.setReviewMessage("Good");
        customerReviewBuilder.setReviewDate(new Date());
        customerReviewBuilder.setCustomer(customer);
        customerReviewBuilder.setRestaurant(restaurant);
        CustomerReview customerReview = customerReviewBuilder.buildCustomerReview();

        customer = entityManager.merge(customer);
        entityManager.flush();
        restaurant = entityManager.merge(restaurant);
        entityManager.flush();
        customerReview.setCustomer(customer);
        customerReview.setRestaurant(restaurant);
        customerReview.setId(1L);
        entityManager.merge(customerReview);
        entityManager.flush();

        customerReviewRepository.delete(customerReview);
        assertThat(customerReviewRepository.findById(customerReview.getId()).isPresent()).isFalse();
    }
}