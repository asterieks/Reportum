package com.reportum.angular2.springmvc.dao.impl;

import com.reportum.angular2.springmvc.dao.IReportDAO;
import com.reportum.angular2.springmvc.persistence.entities.Report;
import com.reportum.angular2.springmvc.persistence.entities.Report_;
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
    public Report saveReport(Report report) {
        return em.merge(report);
    }

    public List<Report> getActualReport(Long projectId){
        CriteriaQuery<Report> criteria=getCriteriaBuilder().createQuery(Report.class);
        Root<Report> root=criteria.from(Report.class);

        Date thisWeekMonday = DateUtils.getThisWeekMondayDate();

        Predicate byId=getCriteriaBuilder().equal(root.get(Report_.projectId),projectId);
        Predicate byDate=getCriteriaBuilder().greaterThanOrEqualTo(root.get(Report_.date), thisWeekMonday);

        List<Predicate> predicates=new ArrayList<>();
        predicates.add(byId);
        predicates.add(byDate);

        criteria.select(root)
                .where(getCriteriaBuilder().and(predicates.toArray(new Predicate[] {})));

        return em.createQuery(criteria).getResultList();
    }

    @Override
    public Report getReport(String reportId) {
        CriteriaQuery<Report> criteria=getCriteriaBuilder().createQuery(Report.class);
        Root<Report> root=criteria.from(Report.class);

        criteria.select(root)
                .where(getCriteriaBuilder().equal(root.get(Report_.reportId),reportId));

        return em.createQuery(criteria).getSingleResult();
    }

    @Override
    public List<Report> getReportByProject(Long id) {
        CriteriaQuery<Report> criteria=getCriteriaBuilder().createQuery(Report.class);
        Root<Report> root=criteria.from(Report.class);

        criteria.select(root)
                .where(getCriteriaBuilder().equal(root.get(Report_.projectId),id));

        return em.createQuery(criteria).getResultList();
    }

    @Override
    public List<Report> findAll() {
        CriteriaQuery<Report> criteria=getCriteriaBuilder().createQuery(Report.class);
        Root<Report> root=criteria.from(Report.class);

        criteria.select(root);

        return em.createQuery(criteria).getResultList();
    }

    private CriteriaBuilder getCriteriaBuilder(){
        return em.getCriteriaBuilder();
    }
}
