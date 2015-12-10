package com.crystal.repositroy;

import com.crystal.model.entities.catalog.AuditedEntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("auditedEntityTypeRepository")
public interface AuditedEntityTypeRepository extends JpaRepository<AuditedEntityType,Long>{

    @Query("SELECT aet FROM AuditedEntityType aet WHERE aet.code=:typeCode")
    public AuditedEntityType findByCode(@Param("typeCode")String typeCode);
}
