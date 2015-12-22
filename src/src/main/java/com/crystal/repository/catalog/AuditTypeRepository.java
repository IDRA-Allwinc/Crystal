package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.AuditType;
import com.crystal.model.entities.catalog.dto.AuditTypeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditTypeRepository extends JpaRepository<AuditType,Long>{

    @Query("select new com.crystal.model.entities.catalog.dto.AuditTypeDto(at.id, at.name, at.description) from AuditType at where at.id=:auditTypeId")
    public AuditTypeDto findDtoById(@Param("auditTypeId")Long auditTypeId);
}
