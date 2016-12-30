package com.reportum.angular2.springmvc.dao.impl;

import com.reportum.angular2.springmvc.dao.IUserReportDAO;
import com.reportum.angular2.springmvc.persistence.entities.UserReport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class UserReportDAOImpl implements IUserReportDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveReport(UserReport userReport) {
        em.merge(userReport);
    }

}
