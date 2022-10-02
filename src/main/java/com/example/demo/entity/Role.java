package com.example.demo.entity;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rolesdata")

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;

    @ManyToMany(mappedBy = "userRoles")
    private Set<ModelUser> users;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<ModelUser> getUsers() {
        return users;
    }

    public void setUsers(Set<ModelUser> users) {
        this.users = users;
    }
}
