package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Responsibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsibilityRepository extends JpaRepository<Responsibility,Long>{

}
