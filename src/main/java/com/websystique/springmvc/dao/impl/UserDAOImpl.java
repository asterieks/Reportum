package com.websystique.springmvc.dao.impl;


import com.websystique.springmvc.dao.IUserDAO;
import com.websystique.springmvc.persistence.entities.UserReport;
import com.websystique.springmvc.utils.beans.UserReportBean;
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
    public void saveReport(UserReport userReport) {
        em.merge(userReport);
    }
}
