package com.dalhousie.MealStop.integration_tests;

import com.dalhousie.MealStop.customer.builder.CustomerBuilder;
import com.dalhousie.MealStop.customer.model.Customer;
import com.dalhousie.MealStop.customer.repository.CustomerRepository;
import com.dalhousie.MealStop.tests_support.TestsSupport;
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
public class CustomerRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    private TestsSupport testsSupport = new TestsSupport();

    @Test
    public void ShouldReturnCustomerWhenFindAll() {
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

        customer = entityManager.merge(customer);
        entityManager.flush();
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList.size())
                .isGreaterThan(0);
    }

    @Test
    public void ShouldReturnCustomerWhenSave() {
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

        customer = entityManager.merge(customer);
        entityManager.flush();

        customerRepository.save(customer);
        assertThat(customerRepository.findById(customer.getId()).get()).isNotNull();
    }

    @Test
    public void ShouldReturnNothingWhenCustomerIsDeleted() {
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

        customer = entityManager.merge(customer);
        entityManager.flush();

        customerRepository.deleteById(1L);
        assertThat(customerRepository.findById(customer.getId()).isPresent()).isFalse();
    }
}