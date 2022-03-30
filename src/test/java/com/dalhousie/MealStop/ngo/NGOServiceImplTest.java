package com.dalhousie.MealStop.ngo.service;

import com.dalhousie.MealStop.customer.modal.Customer;
import com.dalhousie.MealStop.ngo.modal.NGO;
import com.dalhousie.MealStop.ngo.repository.NGORepository;
import com.dalhousie.MealStop.user.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NGOServiceImplTest {

    @Mock
    private NGORepository ngoRepository;

    @InjectMocks
    private NGOServiceImplTest NGOService;

    private NGO NGO1;

    private NGO NGO2;

    private User user;

    List<NGO> NGOList;

    @BeforeEach
    void setUp()
    {
        user = new User(1L, "kuldeep", "ngo", "ngo@gmail.com", "11111111", "April 22, 1999", "Halifax, NS, Canada", "password", "ROLE_CUSTOMER", true, null);
        NGO1 = new NGO(user);
        NGO2 = new NGO("Test", "User","Test@gmail.com","12121212");
        NGO2.setId(2L);

        NGOList = new ArrayList<>();
        NGOList.add(NGO1);
        NGOList.add(NGO2);
    }


    }

}
