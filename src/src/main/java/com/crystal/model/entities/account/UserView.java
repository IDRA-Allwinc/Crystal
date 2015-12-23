package com.crystal.model.entities.account;

import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("select id_user id, username, fullName, email, enabled, role.name role  from user inner join role on user.id_role = role.id_role")
public class UserView{
    @Id
    private Long id;

    private String username;

    private String fullName;

    private String email;

    private String role;

    private Boolean enabled;

}
