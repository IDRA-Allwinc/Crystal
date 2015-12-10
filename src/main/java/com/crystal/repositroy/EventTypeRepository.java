package com.crystal.repositroy;

import com.crystal.model.entities.catalog.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("eventTypeRepository")
public interface EventTypeRepository extends JpaRepository<EventType,Long>{

}
