package com.crystal.repositroy;

import com.crystal.model.entities.catalog.AuditType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("auditTypeRepository")
public interface AuditTypeRepository extends JpaRepository<AuditType,Long>{

}
