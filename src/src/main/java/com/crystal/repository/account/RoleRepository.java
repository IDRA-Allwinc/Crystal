package com.crystal.repository.account;

import com.crystal.model.entities.account.Role;
import com.crystal.model.shared.SelectList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
    public Role findByCode(String roleCode);

    @Query("SELECT new com.crystal.model.shared.SelectList(r.id, r.name, r.code) FROM Role r")
    public List<SelectList> findSelectList();

    @Query("SELECT r.code FROM Role r WHERE r.id = :id")
    String findCodeById(@Param ("id") Long roleId);
}
