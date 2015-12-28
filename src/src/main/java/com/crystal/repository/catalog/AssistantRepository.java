package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Assistant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssistantRepository extends JpaRepository<Assistant,Long>{

}
