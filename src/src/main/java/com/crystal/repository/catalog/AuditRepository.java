package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<Audit,Long>{

}
