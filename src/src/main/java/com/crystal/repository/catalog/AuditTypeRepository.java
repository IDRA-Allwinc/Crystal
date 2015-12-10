package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.AuditType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditTypeRepository extends JpaRepository<AuditType,Long>{

}
