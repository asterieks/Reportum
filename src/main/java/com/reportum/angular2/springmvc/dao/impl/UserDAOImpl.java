package com.reportum.angular2.springmvc.dao.impl;


import com.reportum.angular2.springmvc.dao.IUserDAO;
import com.reportum.angular2.springmvc.persistence.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class UserDAOImpl implements IUserDAO{

    @PersistenceContext
    private EntityManager em;

    @Override
    public User findUserById(String id) {
        return em.find(User.class,id);
    }
}
