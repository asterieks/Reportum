package com.reportum.angular2.springmvc.dao.impl;


import com.reportum.angular2.springmvc.dao.IUserDAO;
import com.reportum.angular2.springmvc.persistence.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements IUserDAO{

    @PersistenceContext
    private EntityManager em;

    @Override
    public User findUser(String id) {
        return em.find(User.class,id);
    }

    @Override
    public List<User> findAllUsers() {
        CriteriaQuery<User> criteria=getCriteriaBuilder().createQuery(User.class);
        Root<User> root=criteria.from(User.class);
        criteria.select(root);
        return em.createQuery(criteria).getResultList();
    }

    @Override
    public void saveUser(User user) {
        em.merge(user);
    }

    @Override
    public void deleteUser(String userId) {
        em.remove(findUser(userId));
    }

    private CriteriaBuilder getCriteriaBuilder(){
        return em.getCriteriaBuilder();
    }

    //for test only
    public void setEm(EntityManager em){
        this.em=em;
    }
}
