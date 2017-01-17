package com.reportum.angular2.springmvc.dao.impl;

import com.reportum.angular2.springmvc.dao.IProjectDAO;
import com.reportum.angular2.springmvc.persistence.entities.Project;
import com.reportum.angular2.springmvc.persistence.entities.Project_;
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
public class ProjectDAOImpl implements IProjectDAO{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Project> getProjectsByReporter(User reporter) {
        CriteriaQuery<Project> criteria=getCriteriaBuilder().createQuery(Project.class);
        Root<Project> root=criteria.from(Project.class);
        criteria.where(getCriteriaBuilder().equal(root.get(Project_.reporter),reporter));
        criteria.select(root);
        return em.createQuery(criteria).getResultList();
    }

    @Override
    public List<Project> getProjectsByTeamLead(User teamLeader) {
        CriteriaQuery<Project> criteria=getCriteriaBuilder().createQuery(Project.class);
        Root<Project> root=criteria.from(Project.class);
        criteria.where(getCriteriaBuilder().equal(root.get(Project_.teamLeader),teamLeader));
        criteria.select(root);
        return em.createQuery(criteria).getResultList();
    }


    @Override
    public Project getProjectsByProjectId(Long id) {
        return em.find(Project.class,id);
    }

    @Override
    public void save(Project project) {
        em.merge(project);
    }


    private CriteriaBuilder getCriteriaBuilder(){
        return em.getCriteriaBuilder();
    }

}
