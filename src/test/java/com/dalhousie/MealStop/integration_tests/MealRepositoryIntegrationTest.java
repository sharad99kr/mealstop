package com.dalhousie.MealStop.integration_tests;

import com.dalhousie.MealStop.meal.builder.MealBuilder;
import com.dalhousie.MealStop.meal.model.Meal;
import com.dalhousie.MealStop.meal.repository.MealRepository;
import com.dalhousie.MealStop.restaurant.model.Restaurant;
import com.dalhousie.MealStop.restaurant.builder.RestaurantBuilder;
import com.dalhousie.MealStop.tests_support.TestsSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MealRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MealRepository mealRepository;

    private TestsSupport testsSupport = new TestsSupport();

    @Test
    public void ShouldReturnMealListWhenFindByRestaurantId() {
        Meal meal = testsSupport.createDummyMeal();
        meal.setId(1L);
        Restaurant restaurant = testsSupport.createDummyRestaurant();
        restaurant.setId(1L);
        restaurant = entityManager.merge(restaurant);
        entityManager.flush();
        meal.setRestaurant(restaurant);
        entityManager.merge(meal);
        entityManager.flush();
        List<Meal> mealList = mealRepository.findByRestaurantId(meal.getRestaurant().getId());
        assertThat(mealList.size())
                .isGreaterThan(0);
    }

    @Test
    public void ShouldReturnMealWhenFindAll() {
        Meal meal = testsSupport.createDummyMeal();
        meal.setId(1L);
        Restaurant restaurant = testsSupport.createDummyRestaurant();
        restaurant.setId(1L);
        restaurant = entityManager.merge(restaurant);
        entityManager.flush();
        meal.setRestaurant(restaurant);
        entityManager.merge(meal);
        entityManager.flush();
        List<Meal> outputMeal = mealRepository.findAll();
        assertThat(outputMeal).isNotNull();
    }

    @Test
    public void ShouldReturnMealWhenSave() {
        Meal meal = testsSupport.createDummyMeal();
        meal.setId(1L);
        Restaurant restaurant = testsSupport.createDummyRestaurant();
        restaurant.setId(1L);
        restaurant = entityManager.merge(restaurant);
        entityManager.flush();
        meal.setRestaurant(restaurant);
        meal = entityManager.merge(meal);
        entityManager.flush();
        mealRepository.save(meal);
        assertThat(mealRepository.findById(meal.getId()).get()).isNotNull();
    }

    @Test
    public void ShouldReturnMealWhenFindById() {
        Meal meal = testsSupport.createDummyMeal();
        meal.setId(1L);
        Restaurant restaurant = testsSupport.createDummyRestaurant();
        restaurant.setId(1L);
        restaurant = entityManager.merge(restaurant);
        entityManager.flush();
        meal.setRestaurant(restaurant);
        meal = entityManager.merge(meal);
        entityManager.flush();
        Meal outputMeal = mealRepository.findById(meal.getId()).get();
        assertThat(outputMeal)
                .isEqualTo(meal);
    }

    @Test
    public void ShouldReturnUpdatedMealWhenUpdateMealIsPerformed() {
        Meal meal = testsSupport.createDummyMeal();
        meal.setId(1L);
        Restaurant restaurant = testsSupport.createDummyRestaurant();
        restaurant.setId(1L);
        restaurant = entityManager.merge(restaurant);
        entityManager.flush();
        meal.setRestaurant(restaurant);
        meal = entityManager.merge(meal);
        entityManager.flush();
        meal.setPrice(10);
        meal = entityManager.merge(meal);
        Meal outputMeal = mealRepository.findById(meal.getId()).get();
        assertThat(outputMeal.getPrice())
                .isEqualTo(10);
    }
}