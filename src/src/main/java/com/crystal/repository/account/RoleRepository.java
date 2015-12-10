package com.crystal.repository.account;

import com.crystal.model.entities.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
    public Role findByCode(String roleCode);

}
