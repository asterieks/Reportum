package com.reportum.angular2.springmvc.dao;

import com.reportum.angular2.springmvc.configuration.TestDataSourceConfig;
import com.reportum.angular2.springmvc.dao.impl.UserDAOImpl;
import com.reportum.angular2.springmvc.persistence.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertEquals;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestDataSourceConfig.class)
@Transactional
public class UserDaoImplTest {

    @PersistenceContext
    private EntityManager em;

    @InjectMocks
    private UserDAOImpl userDAO=new UserDAOImpl();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        userDAO.setEm(em);
    }

    @Test
    public void saveUserTest(){
        User user = new User();
        user.setFullName("fn");
        user.setId("12");
        user.setPassword("pass");

        userDAO.saveUser(user);
        User actual=em.find(User.class, "12");
        assertEquals("12", actual.getId());
    }

    @Test
    public void findAllUsersTest(){
        User user = new User();
        user.setFullName("fn");
        user.setId("102");
        user.setPassword("pass");
        em.merge(user);

        User user1 = new User();
        user1.setFullName("fn");
        user1.setId("120");
        user1.setPassword("pass");
        em.merge(user1);

        List<User> actual = userDAO.findAllUsers();
        assertEquals(2, actual.size());
        assertEquals("102", actual.get(0).getId());
        assertEquals("120", actual.get(1).getId());
    }

    @Test
    public void findUserTest(){
        User user = new User();
        user.setFullName("fn");
        user.setId("1002");
        user.setPassword("pass");
        em.merge(user);

        User actual = userDAO.findUser("1002");
        assertEquals("1002", actual.getId());
    }
}
