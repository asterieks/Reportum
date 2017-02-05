package com.reportum.angular2.springmvc.dao.impl;


import com.reportum.angular2.springmvc.dao.IUserDAO;
import com.reportum.angular2.springmvc.persistence.entities.Role_;
import com.reportum.angular2.springmvc.persistence.entities.User;
import com.reportum.angular2.springmvc.persistence.entities.User_;
import com.reportum.angular2.springmvc.wrappers.LoginWrapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
    public LoginWrapper getRoleByUserId(String id) {
        CriteriaQuery<LoginWrapper> criteria=getCriteriaBuilder().createQuery(LoginWrapper.class);
        Root<User> root=criteria.from(User.class);
        criteria.select(getCriteriaBuilder().construct(LoginWrapper.class,
                root.get(User_.id),
                root.get(User_.fullName),
                root.get(User_.role).get(Role_.roleName),
                root.get(User_.password)
        ));
        criteria.where(getCriteriaBuilder().equal(root.get(User_.id),id));
        return em.createQuery(criteria).getSingleResult();
    }

    private CriteriaBuilder getCriteriaBuilder(){
        return em.getCriteriaBuilder();
    }
}
