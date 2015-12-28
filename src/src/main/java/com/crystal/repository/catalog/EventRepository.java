package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event,Long>{

}
