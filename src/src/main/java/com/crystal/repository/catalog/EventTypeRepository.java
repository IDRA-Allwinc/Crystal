package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.EventType;
import com.crystal.model.entities.catalog.dto.EventTypeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType,Long>{
    EventType findByIdAndIsObsolete(Long id, boolean isObsolete);

    @Query("SELECT new com.crystal.model.entities.catalog.dto.EventTypeDto(e.id, e.name, e.description) FROM EventType e WHERE e.id = :id  AND e.isObsolete = 0")
    EventTypeDto findOneDto(@Param("id") Long id);
}
