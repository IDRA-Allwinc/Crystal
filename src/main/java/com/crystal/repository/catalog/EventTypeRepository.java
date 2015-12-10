package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType,Long>{

}
