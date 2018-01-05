package com.reportum.angular2.springmvc.dao.impl;

import com.reportum.angular2.springmvc.dao.IJobStateDAO;
import com.reportum.angular2.springmvc.persistence.entities.JobStateHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class JobStateDAOImpl implements IJobStateDAO{

    @PersistenceContext
    private EntityManager em;

    @Override
    public JobStateHolder getJobState(Long id) {
        return em.find(JobStateHolder.class, id);
    }

    @Override
    public void updateJobStateHolder(JobStateHolder jobStateHolder) {
        em.merge(jobStateHolder);
    }
}
