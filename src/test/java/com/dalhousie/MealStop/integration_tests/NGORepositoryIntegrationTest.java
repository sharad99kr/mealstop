package com.dalhousie.MealStop.integration_tests;

import com.dalhousie.MealStop.ngo.model.NGO;
import com.dalhousie.MealStop.ngo.repository.NGORepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import com.dalhousie.MealStop.user.entity.User;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NGORepositoryIntegrationTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private NGORepository ngoRepository;


    @Test
    public void ShouldReturnNGOWhenFindById() {
        NGO ngo = new NGO("ngo1", "p@gmail.com", "9029893443", "111111111");
        ngo.setId(1L);
        ngo = entityManager.merge(ngo);
        entityManager.flush();
        NGO resultedNGO = ngoRepository.findById(ngo.getId()).get();
        assertThat(resultedNGO)
                .isEqualTo(ngo);
    }

    @Test
    public void ShouldReturnNGOWhenInsert() {
        User user = new User();
        user.setUser_id(1L);
        NGO ngo = new NGO("ngo1", "p@gmail.com", "9029893443", "111111111");
        ngo.setId(1L);
        ngo = entityManager.merge(ngo);
        entityManager.flush();
        ngoRepository.save(ngo);
        assertThat(ngoRepository.findById(ngo.getId()).get()).isNotNull();
    }

}