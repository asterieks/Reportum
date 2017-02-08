package com.reportum.angular2.springmvc.dao.impl;

import com.reportum.angular2.springmvc.configuration.TestDataBaseConfig;
import com.reportum.angular2.springmvc.persistence.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertEquals;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestDataBaseConfig.class)
@Transactional
public class UserDAOImplTest {

    @PersistenceContext
    EntityManager em;

    @InjectMocks
    private UserDAOImpl userDAO = new UserDAOImpl();

    private User user;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userDAO.setEm(em);

        user=new User();
        user.setId("23");
        user.setFullName("FullName");

        em.persist(user);
    }

    @Test
    public void findUserTest(){
        User userTest = userDAO.findUser("23");
        User actual = em.find(User.class, "23");
        assertEquals(userTest.getId(), actual.getId());
    }
}
