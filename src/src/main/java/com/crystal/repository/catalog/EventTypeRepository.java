package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.EventType;
import com.crystal.model.entities.catalog.dto.EventTypeDto;
import com.crystal.model.shared.SelectList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType,Long>{
    EventType findByIdAndIsObsolete(Long id, boolean isObsolete);

    @Query("SELECT new com.crystal.model.entities.catalog.dto.EventTypeDto(e.id, e.name, e.description) FROM EventType e WHERE e.id = :id  AND e.isObsolete = 0")
    EventTypeDto findOneDto(@Param("id") Long id);


    @Query("select new com.crystal.model.shared.SelectList(e.id, e.name, e.description) from EventType e where e.isObsolete = false")
    List<SelectList> findNoObsolete();
}
