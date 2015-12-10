package com.crystal.repositroy;

import com.crystal.model.entities.catalog.AuditedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("auditedEntityTypeRepository")
public interface AuditedEntityRepository extends JpaRepository<AuditedEntity,Long>{

}
