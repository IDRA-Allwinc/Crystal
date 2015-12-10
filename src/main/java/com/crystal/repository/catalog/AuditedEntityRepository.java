package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.AuditedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditedEntityRepository extends JpaRepository<AuditedEntity,Long>{

}
