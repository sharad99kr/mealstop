package com.dalhousie.MealStop.home.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IndexControllerTest
{
    @InjectMocks
    IndexController indexController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp()
    {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }

    @Test
    void homePageTest() throws Exception
    {
        mockMvc.perform(get("/")).andExpect(status().isOk());
        Model modal = null;
        assertEquals("index", indexController.greeting(modal));
    }
}
