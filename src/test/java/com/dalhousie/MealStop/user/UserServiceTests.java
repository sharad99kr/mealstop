package com.dalhousie.MealStop.user;

import com.dalhousie.MealStop.user.entity.User;
import com.dalhousie.MealStop.user.repository.UserRepository;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTests {

    @Mock
    private UserRepository mockUserRepository;

    void ShouldSignUpUser(){
        //when(mockUserRepository.save(any(User.class))).thenReturn(null);
    }
}
