package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.AuditedEntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditedEntityTypeRepository extends JpaRepository<AuditedEntityType,Long>{

    public AuditedEntityType findByCode(String typeCode);
}
