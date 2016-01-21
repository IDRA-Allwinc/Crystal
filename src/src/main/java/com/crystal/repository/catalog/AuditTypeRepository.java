package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.AuditType;
import com.crystal.model.entities.catalog.dto.AuditTypeDto;
import com.crystal.model.shared.SelectList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditTypeRepository extends JpaRepository<AuditType, Long> {

    @Query("select new com.crystal.model.entities.catalog.dto.AuditTypeDto(at.id, at.name, at.description) from AuditType at where at.id=:auditTypeId")
    public AuditTypeDto findDtoById(@Param("auditTypeId") Long auditTypeId);

    @Query("select new com.crystal.model.shared.SelectList(at.id, at.name, at.description) from AuditType at where at.isObsolete=false")
    public List<SelectList> findNoObsolete();
}
