package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.AuditedEntity;
import com.crystal.model.shared.SelectList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.repository.query.Param;


@Repository
public interface AuditedEntityRepository extends JpaRepository<AuditedEntity,Long>{

    @Query("SELECT new com.crystal.model.shared.SelectList(r.id, r.name) FROM AuditedEntity r")
    public List<SelectList> findSelectList();

    @Query("SELECT new com.crystal.model.shared.SelectList(r.id, r.name) FROM AuditedEntity r WHERE r.auditedEntityType.code = :code")
    public List<SelectList> findSelectList(@Param("code")String code);
}
