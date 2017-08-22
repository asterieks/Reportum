package com.reportum.angular2.springmvc.dao.impl;

import com.reportum.angular2.springmvc.dao.IReportDAO;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.Project_;
import com.reportum.angular2.springmvc.persistence.entities.Report;
import com.reportum.angular2.springmvc.persistence.entities.Report_;
import com.reportum.angular2.springmvc.utils.DateUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

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
        greaterTimePredicate(predicates, root, DateUtils.getThisWeekMondayDate());

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
        greaterTimePredicate(predicates, root, DateUtils.getThisWeekMondayDate());

        criteria.select(root)
                .where(getCriteriaBuilder().and(predicates.toArray(new Predicate[] {})));
        return em.createQuery(criteria).getSingleResult();
    }

    @Override
    public List<Report> findAllActualReports() {
        CriteriaQuery<Report> criteria=getCriteriaBuilder().createQuery(Report.class);
        Root<Report> root=criteria.from(Report.class);

        List<Predicate> predicates=new ArrayList<>();
        greaterTimePredicate(predicates, root, DateUtils.getThisWeekMondayDate());

        criteria.select(root)
                .where(getCriteriaBuilder().and(predicates.toArray(new Predicate[] {})));
        return em.createQuery(criteria).getResultList();
    }

    @Override
    public Report findReportByProjectId(Long projectId) {
        CriteriaQuery<Report> criteria=getCriteriaBuilder().createQuery(Report.class);
        Root<Report> root=criteria.from(Report.class);

        List<Predicate> predicates = new ArrayList<>();
        addProjectIdPredicate(predicates, root, projectId);
        greaterTimePredicate(predicates, root, DateUtils.getThisWeekMondayDate());

        criteria.select(root)
                .where(getCriteriaBuilder().and(predicates.toArray(new Predicate[] {})));
        List<Report> reports = em.createQuery(criteria).getResultList();
        return !isEmpty(reports)? reports.get(0) : null;
    }

    @Override
    public Report findPrevReportByProjectId(Long projectId) {
        CriteriaQuery<Report> criteria=getCriteriaBuilder().createQuery(Report.class);
        Root<Report> root=criteria.from(Report.class);

        List<Predicate> predicates = new ArrayList<>();
        addProjectIdPredicate(predicates, root, projectId);
        lesserTimePredicate(predicates, root, DateUtils.getThisWeekMondayDate());

        criteria.select(root)
                .where(getCriteriaBuilder().and(predicates.toArray(new Predicate[] {})))
                .orderBy(getCriteriaBuilder().desc(root.get(Report_.reportId)));

        List<Report> reports = em.createQuery(criteria).getResultList();
        return !isEmpty(reports)? reports.get(0) : null;
    }


    private void greaterTimePredicate(List<Predicate> predicates, Root<Report> root, Date thisWeekMondayDate) {
        Predicate predicate=getCriteriaBuilder().greaterThanOrEqualTo(root.get(Report_.date), thisWeekMondayDate);
        predicates.add(predicate);
    }

    private void lesserTimePredicate(List<Predicate> predicates, Root<Report> root, Date thisWeekMondayDate) {
        Predicate predicate=getCriteriaBuilder().lessThan(root.get(Report_.date), thisWeekMondayDate);
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

    private void addProjectIdPredicate(List<Predicate> predicates, Root<Report> root, Long projectId) {
        Predicate predicate=getCriteriaBuilder().equal(root.get(Report_.project).get(Project_.projectId),projectId);
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
