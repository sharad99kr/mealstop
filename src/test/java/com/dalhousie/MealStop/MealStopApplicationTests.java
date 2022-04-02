package com.dalhousie.MealStop;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
class MealStopApplicationTests
{
    @Test
    void testMain()
    {
        MealStopApplication application = Mockito.mock(MealStopApplication.class);
        String [] args = { "one", "two", "three" };
        application.main(args);
        verifyZeroInteractions(application);
    }
}
