package com.dalhousie.MealStop.ngo.service;

import com.dalhousie.MealStop.common.NGOConstants;
import com.dalhousie.MealStop.email.IEmailService;
import com.dalhousie.MealStop.ngo.model.NGO;
import com.dalhousie.MealStop.ngo.repository.NGORepository;
import com.dalhousie.MealStop.ngo.service.NGOServiceImpl;
import com.dalhousie.MealStop.ngoorder.service.INGOOrderService;
import com.dalhousie.MealStop.orders.model.Orders;
import com.dalhousie.MealStop.orders.service.IOrderService;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class NGOServiceImplTest {

    @Mock
    private NGORepository ngoRepository;

    @InjectMocks
    private NGOServiceImpl NGOService;

    @Mock
    private IEmailService emailService;

    @Mock
    private IOrderService orderService;

    @Mock
    private INGOOrderService ngoOrderService;

    private NGO NGO1;

    private NGO NGO2;

    private User user;

    List<NGO> NGOList;

    @BeforeEach
    void setUp()
    {
        user = new User(1L, "kuldeep", "ngo", "ngo@gmail.com", "11111111", "April 22, 1999", "Halifax, NS, Canada", "password", "ROLE_CUSTOMER", true, null);
        NGO1 = new NGO(user);
        NGO2 = new NGO();
        NGO2.setName("TEST");
        NGO2.setEmail("ngo@gmail.com");
        NGO2.setAddress("halifax to india");
        NGO2.setPhoneNumber("1234567890");
        NGO2.setNGOName("TEST");
        NGO2.setTotalOrders(12);
        NGO2.setId(2L);

        NGOList = new ArrayList<>();
        NGOList.add(NGO1);
        NGOList.add(NGO2);
    }

    @AfterEach
    void tearDown()
    {
        NGO1 = NGO2 = null;
        NGOList = null;
    }

    @Test
    void getNGOById()
    {
        java.util.Optional<NGO> ngo = java.util.Optional.of(new NGO());
        when(ngoRepository.findById(1L)).thenReturn(ngo);

        String NGOId = new Long(NGO1.getId()).toString();
        assertThat(NGOService.getNGOById(NGOId)).isEqualTo(ngo.get());
    }


    void setDummyUserInSession()
    {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getDetails()).thenReturn(user);
    }

    @Test
    public void getNGODetailsFromSession()
    {
        setDummyUserInSession();
        NGO loggedInNGO = NGOService.getNGODetailsFromSession();
        assertThat(loggedInNGO.getEmail()).isEqualTo(user.getUsername());
    }

    @Test
    public void getLoggedInNGOId()
    {
        setDummyUserInSession();
        NGO loggedInCustomer = NGOService.getNGODetailsFromSession();
        Long ngoUserId = NGOService.getLoggedInNGOId();
        assertThat(ngoUserId).isEqualTo(user.getUser_id());
    }

    @Test
    void sendCancelledOrderNotificationTest()
    {
        when(ngoRepository.findAll()).thenReturn(NGOList);
        Mockito.doNothing().when(emailService).sendEmail(any(),any(), any());
        NGOService.sendCancelledOrderNotification("mealName");
        verify(ngoRepository,times(1)).findAll();
    }

    @Test
    void addNGO()
    {
        when(ngoRepository.save(any())).thenReturn(NGO1);
        NGOService.addNGO(user);
        verify(ngoRepository,times(1)).save(any());
    }

    @Test
    public void getNGOOrderHistoryTest()
    {
        setDummyUserInSession();
        Mockito.when(orderService.getAllOrders()).thenReturn(new ArrayList<>());
        Mockito.when(ngoOrderService.getNGOOrderWithId(1L)).thenReturn(new ArrayList<>());

        List<Orders> orders = NGOService.getNGOOrderHistory();
        assertThat(orders.size()).isEqualTo(0);
    }

    @Test
    public void gettersInNGO(){
        assertThat(NGO2.getId()).isEqualTo(2L);
        assertThat(NGO2.getName()).isEqualTo("TEST");
        assertThat(NGO2.getEmail()).isEqualTo("ngo@gmail.com");
        assertThat(NGO2.getAddress()).isEqualTo("halifax to india");
        assertThat(NGO2.getPhoneNumber()).isEqualTo("1234567890");
        assertThat(NGO2.getNGOName()).isEqualTo("TEST");
        assertThat(NGO2.getTotalOrders()).isEqualTo(12);

    }
}
