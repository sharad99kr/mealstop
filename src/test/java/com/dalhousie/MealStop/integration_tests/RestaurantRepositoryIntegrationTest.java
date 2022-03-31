package com.dalhousie.MealStop.integration_tests;

import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.restaurant.repository.RestaurantRepository;
import com.dalhousie.MealStop.user.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RestaurantRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void ShouldReturnRestaurantListWhenFindAll() {
        User user = new User();
        user.setUser_id(1L);
        Restaurant restaurant = new Restaurant("Restaurant1", 1L, "monday, tuesday","p@gmail.com", "9029893443", "911 Park Victoria");
        restaurant.setUserId(1L);
        restaurant.setId(1L);
        entityManager.merge(restaurant);
        entityManager.flush();
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList.size())
                .isGreaterThan(0);
    }

    @Test
    public void ShouldReturnRestaurantWhenSave() {
        User user = new User();
        user.setUser_id(1L);
        Restaurant restaurant = new Restaurant("Restaurant1", 1L, "monday, tuesday","p@gmail.com", "9029893443", "911 Park Victoria");
        restaurant.setUserId(1L);
        restaurant.setId(1L);
        restaurant = entityManager.merge(restaurant);
        entityManager.flush();
        restaurantRepository.save(restaurant);
        assertThat(restaurantRepository.findById(restaurant.getId()).get()).isNotNull();
    }

    @Test
    public void ShouldReturnMealWhenFindById() {
        Restaurant restaurant = new Restaurant("Restaurant1", 1L, "monday, tuesday","p@gmail.com", "9029893443", "911 Park Victoria");
        restaurant.setId(1L);
        restaurant = entityManager.merge(restaurant);
        entityManager.flush();
        Restaurant outputRestaurant = restaurantRepository.findById(restaurant.getId()).get();
        assertThat(outputRestaurant)
                .isEqualTo(restaurant);
    }

    @Test
    public void ShouldReturnUpdatedRestaurantWhenUpdateRestaurantIsPerformed() {
        Restaurant restaurant = new Restaurant("Restaurant1", 1L, "monday, tuesday","p@gmail.com", "9029893443", "911 Park Victoria");
        restaurant.setId(1L);
        entityManager.merge(restaurant);
        entityManager.flush();
        restaurant.setRestaurantName("UpdatedName");
        restaurant = entityManager.merge(restaurant);
        Restaurant outputRestaurant = restaurantRepository.findById(restaurant.getId()).get();
        assertThat(outputRestaurant.getRestaurantName())
                .isEqualTo("UpdatedName");
    }
}