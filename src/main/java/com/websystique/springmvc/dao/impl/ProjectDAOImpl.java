package com.websystique.springmvc.dao.impl;

import com.websystique.springmvc.dao.IProjectDAO;
import com.websystique.springmvc.persistence.entities.Project;
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
    public List<Project> getProjectsByUserId() {
        CriteriaQuery<Project> criteria=getCriteriaBuilder().createQuery(Project.class);
        Root<Project> root=criteria.from(Project.class);
        //criteria.where(getCriteriaBuilder().equal(root.get(Organization_.organizationId),id));
        criteria.select(root);
        return em.createQuery(criteria).getResultList();
    }

    private CriteriaBuilder getCriteriaBuilder(){
        return em.getCriteriaBuilder();
    }

}
