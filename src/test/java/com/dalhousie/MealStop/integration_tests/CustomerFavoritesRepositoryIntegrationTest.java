package com.dalhousie.MealStop.integration_tests;

import com.dalhousie.MealStop.customer.builder.CustomerBuilder;
import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.favorites.modal.CustomerFavorites;
import com.dalhousie.MealStop.favorites.repository.CustomerFavoritesRepository;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.tests_support.TestsSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerFavoritesRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerFavoritesRepository customerFavoritesRepository;

    private TestsSupport testsSupport = new TestsSupport();

    @Test
    public void ShouldReturnFavoritesListWhenFindByCustomer() {
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

        CustomerFavorites customerFavorites = new CustomerFavorites();

        customer = entityManager.merge(customer);
        entityManager.flush();
        restaurant = entityManager.merge(restaurant);
        entityManager.flush();
        customerFavorites.setCustomer(customer);
        customerFavorites.setRestaurant(restaurant);
        entityManager.merge(customerFavorites);
        entityManager.flush();
        List<CustomerFavorites> favoritesList = customerFavoritesRepository.findByCustomer(customer);
        assertThat(favoritesList.size())
                .isGreaterThan(0);
    }

    @Test
    public void ShouldReturnCustomerFavoriteWhenFindByCustomerAndRestaurant() {
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

        CustomerFavorites customerFavorites = new CustomerFavorites();

        customer = entityManager.merge(customer);
        entityManager.flush();
        restaurant = entityManager.merge(restaurant);
        entityManager.flush();
        customerFavorites.setCustomer(customer);
        customerFavorites.setRestaurant(restaurant);
        entityManager.merge(customerFavorites);
        entityManager.flush();
        CustomerFavorites favorites = customerFavoritesRepository.findByCustomerAndRestaurant(customer, restaurant);
        assertThat(favorites)
                .isNotNull();
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

        CustomerFavorites customerFavorites = new CustomerFavorites();

        customer = entityManager.merge(customer);
        entityManager.flush();
        restaurant = entityManager.merge(restaurant);
        entityManager.flush();
        customerFavorites.setCustomer(customer);
        customerFavorites.setRestaurant(restaurant);
        customerFavorites.setId(1L);
        entityManager.merge(customerFavorites);
        entityManager.flush();

        customerFavoritesRepository.save(customerFavorites);
        assertThat(customerFavoritesRepository.findById(customerFavorites.getId()).get()).isNotNull();
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

        CustomerFavorites customerFavorites = new CustomerFavorites();

        customer = entityManager.merge(customer);
        entityManager.flush();
        restaurant = entityManager.merge(restaurant);
        entityManager.flush();
        customerFavorites.setCustomer(customer);
        customerFavorites.setRestaurant(restaurant);
        customerFavorites.setId(2L);
        entityManager.merge(customerFavorites);
        entityManager.flush();

        customerFavoritesRepository.deleteById(2L);
        assertThat(customerFavoritesRepository.findById(customerFavorites.getId()).isPresent()).isFalse();
    }
}