package com.reportum.angular2.springmvc.persistence.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna on 02.02.2017.
 */
  @Entity
    @Table(name = "ROLES")
    public class Role {

        @Id
        @Column(name = "ROLE_ID", nullable = false)
        private Long roleId;

        @Column (name = "ROLE_NAME", nullable = false)
        private String roleName;

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
        private List<User> users = new ArrayList<>();;

        public Role() {}

        public Long getRoleId() {
            return roleId;
        }

        public void setRoleId(Long roleId) {
            this.roleId = roleId;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }
    }

