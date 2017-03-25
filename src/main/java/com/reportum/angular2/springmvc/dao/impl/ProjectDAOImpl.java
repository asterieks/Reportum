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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ProjectDAOImpl implements IProjectDAO{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Project> findProjects(User user) {
        CriteriaQuery<Project> criteria=getCriteriaBuilder().createQuery(Project.class);
        Root<Project> root=criteria.from(Project.class);

        List<Predicate> predicates=new ArrayList<>();
        switch(user.getRole()) {
            case REPORTER : addReporterPredicate(predicates, root, user);
                            break;
                case LEAD : addLeadPredicate(predicates, root, user);
                            break;
                  default : addManagerPredicate(predicates, root, user);
        }
        criteria.select(root)
                .where(getCriteriaBuilder().and(predicates.toArray(new Predicate[] {})));
        return em.createQuery(criteria).getResultList();
    }

    @Override
    public Project findProject(Long id) {
        return em.find(Project.class,id);
    }

    @Override
    public void saveProject(Project project) {
        em.merge(project);
    }



    @Override
    public List<Project> findAllProjects() {
        CriteriaQuery<Project> criteria=getCriteriaBuilder().createQuery(Project.class);
        Root<Project> root=criteria.from(Project.class);
        criteria.select(root);
        return em.createQuery(criteria).getResultList();
    }

    @Override
    public void saveProjects(List<Project> projects) {
        projects.forEach(project -> {
            em.merge(project);
        });
    }

    private void addReporterPredicate(List<Predicate> predicates, Root<Project> root, User user) {
        Predicate predicate=getCriteriaBuilder().equal(root.get(Project_.reporter),user);
        predicates.add(predicate);
    }
    private void addLeadPredicate(List<Predicate> predicates, Root<Project> root, User user) {
        Predicate predicate=getCriteriaBuilder().equal(root.get(Project_.teamLeader),user);
        predicates.add(predicate);
    }

    private void addManagerPredicate(List<Predicate> predicates, Root<Project> root, User user) {
        Predicate predicate=getCriteriaBuilder().equal(root.get(Project_.manager),user);
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
