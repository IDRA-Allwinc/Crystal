package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.MeetingType;
import com.crystal.model.entities.catalog.MeetingTypeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingTypeRepository extends JpaRepository<MeetingType,Long>{

    MeetingType findByIdAndIsObsolete(Long id, boolean isObsolete);

    @Query("SELECT new com.crystal.model.entities.catalog.MeetingTypeDto(e.id, e.name, e.description) FROM MeetingType e WHERE e.id = :id  AND e.isObsolete = 0")
    MeetingTypeDto findOneDto(@Param("id") Long id);
}
