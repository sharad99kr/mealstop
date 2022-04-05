package com.dalhousie.MealStop.ngoorder.service;

import com.dalhousie.MealStop.ngoorder.model.NGOOrder;
import com.dalhousie.MealStop.ngoorder.repository.NGOOrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class NGOOrderServiceTest {
    @Mock
    private NGOOrderRepository ngoOrderRepository;

    @InjectMocks
    private NGOOrderServiceImpl ngoOrderService;

    private NGOOrder ngoOrder1;

    private NGOOrder ngoOrder2;

    List<NGOOrder> NGOList;


    @BeforeEach
    void setUp()
    {
        ngoOrder1 = new NGOOrder(1L,1L,1);
        ngoOrder2 = new NGOOrder();
        ngoOrder2.setOrderId(2L);
        ngoOrder2.setNGOId(1L);

        NGOList = new ArrayList<NGOOrder>();
        NGOList.add(ngoOrder1);
        NGOList.add(ngoOrder2);
    }

    @AfterEach
    void tearDown()
    {
        ngoOrder1 = ngoOrder2 = null;
        NGOList = null;
    }

    @Test
    void getNGOOrderWithId()
    {
        java.util.Optional<NGOOrder> ngoOrder = java.util.Optional.of(new NGOOrder());
        when(ngoOrderRepository.findByNGOId(1L)).thenReturn(NGOList);

        assertThat(ngoOrderService.getNGOOrderWithId(1L)).isEqualTo(NGOList);
    }

    @Test
    void addNGOOrder()
    {
        when(ngoOrderRepository.save(any())).thenReturn(ngoOrder1);
        ngoOrderService.addNGOOrder(ngoOrder1);
        verify(ngoOrderRepository,times(1)).save(any());
    }

    @Test
    public void gettersInNGO(){
        assertThat(ngoOrder2.getOrderId()).isEqualTo(2L);
        assertThat(ngoOrder2.getNGOId()).isEqualTo(1L);

    }

}
