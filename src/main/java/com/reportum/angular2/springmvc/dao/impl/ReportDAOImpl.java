package com.reportum.angular2.springmvc.dao.impl;

import com.reportum.angular2.springmvc.dao.IReportDAO;
import com.reportum.angular2.springmvc.persistence.entities.UserReport;
import com.reportum.angular2.springmvc.persistence.entities.UserReport_;
import com.reportum.angular2.springmvc.utils.DateUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class ReportDAOImpl implements IReportDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveReporterReport(UserReport reporterReport) {
        em.merge(reporterReport);
    }

    public List<UserReport> getActualReport(Long projectId){
        CriteriaQuery<UserReport> criteria=getCriteriaBuilder().createQuery(UserReport.class);
        Root<UserReport> root=criteria.from(UserReport.class);

        Date thisWeekMonday = DateUtils.getThisWeekMondayDate();

        Predicate byId=getCriteriaBuilder().equal(root.get(UserReport_.projectId),projectId);
        Predicate byDate=getCriteriaBuilder().greaterThanOrEqualTo(root.get(UserReport_.date), thisWeekMonday);

        List<Predicate> predicates=new ArrayList<>();
        predicates.add(byId);
        predicates.add(byDate);

        criteria.select(root)
                .where(getCriteriaBuilder().and(predicates.toArray(new Predicate[] {})));

        return em.createQuery(criteria).getResultList();
    }

    @Override
    public UserReport getReport(String reportId) {
        CriteriaQuery<UserReport> criteria=getCriteriaBuilder().createQuery(UserReport.class);
        Root<UserReport> root=criteria.from(UserReport.class);

        criteria.select(root)
                .where(getCriteriaBuilder().equal(root.get(UserReport_.reportId),reportId));

        return em.createQuery(criteria).getSingleResult();
    }

    private CriteriaBuilder getCriteriaBuilder(){
        return em.getCriteriaBuilder();
    }
}
