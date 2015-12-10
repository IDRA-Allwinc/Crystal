package com.crystal.repository.account;

import com.crystal.model.entities.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{

    @Query("SELECT r FROM Role r WHERE r.code=:roleCode")
    public Role findByCode(@Param("roleCode")String roleCode);

}
