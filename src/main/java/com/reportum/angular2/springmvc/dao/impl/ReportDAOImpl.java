package com.reportum.angular2.springmvc.dao.impl;

import com.reportum.angular2.springmvc.dao.IReportDAO;
import com.reportum.angular2.springmvc.persistence.entities.Project;
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

    @Override
    public List<Report> findReports(List<Project> projects) {
        CriteriaQuery<Report> criteria=getCriteriaBuilder().createQuery(Report.class);
        Root<Report> root=criteria.from(Report.class);

        List<Predicate> predicates=new ArrayList<>();
        addProjectPredicate(predicates, root, projects);
        addTimePredicate(predicates, root, DateUtils.getThisWeekMondayDate());

        criteria.select(root)
                .where(getCriteriaBuilder().and(predicates.toArray(new Predicate[] {})));
        return em.createQuery(criteria).getResultList();
    }

    @Override
    public Report findReport(Long reportId) {
        CriteriaQuery<Report> criteria=getCriteriaBuilder().createQuery(Report.class);
        Root<Report> root=criteria.from(Report.class);

        List<Predicate> predicates=new ArrayList<>();
        addReportIdPredicate(predicates, root, reportId);
        addTimePredicate(predicates, root, DateUtils.getThisWeekMondayDate());

        criteria.select(root)
                .where(getCriteriaBuilder().and(predicates.toArray(new Predicate[] {})));
        return em.createQuery(criteria).getSingleResult();
    }

    @Override
    public List<Report> findAllActualReports() {
        CriteriaQuery<Report> criteria=getCriteriaBuilder().createQuery(Report.class);
        Root<Report> root=criteria.from(Report.class);

        List<Predicate> predicates=new ArrayList<>();
        addTimePredicate(predicates, root, DateUtils.getThisWeekMondayDate());

        criteria.select(root)
                .where(getCriteriaBuilder().and(predicates.toArray(new Predicate[] {})));
        return em.createQuery(criteria).getResultList();
    }

    private void addTimePredicate(List<Predicate> predicates, Root<Report> root, Date thisWeekMondayDate) {
        Predicate predicate=getCriteriaBuilder().greaterThanOrEqualTo(root.get(Report_.date), thisWeekMondayDate);
        predicates.add(predicate);
    }

    private void addReportIdPredicate(List<Predicate> predicates, Root<Report> root, Long reportId) {
        Predicate predicate=getCriteriaBuilder().equal(root.get(Report_.reportId),reportId);
        predicates.add(predicate);
    }

    private void addProjectPredicate(List<Predicate> predicates, Root<Report> root, List<Project> projects) {
        Predicate predicate=root.get(Report_.project).in(projects);
        predicates.add(predicate);
    }

    private CriteriaBuilder getCriteriaBuilder(){
        return em.getCriteriaBuilder();
    }

    //for test only
    public void setEm(EntityManager em){
        this.em=em;
    }
}
