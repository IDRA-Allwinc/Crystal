package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.MeetingType;
import com.crystal.model.entities.catalog.dto.MeetingTypeDto;
import com.crystal.model.shared.SelectList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingTypeRepository extends JpaRepository<MeetingType,Long>{

    MeetingType findByIdAndIsObsolete(Long id, boolean isObsolete);

    @Query("SELECT new com.crystal.model.entities.catalog.dto.MeetingTypeDto(e.id, e.name, e.description) FROM MeetingType e WHERE e.id = :id  AND e.isObsolete = 0")
    MeetingTypeDto findOneDto(@Param("id") Long id);

    @Query("select new com.crystal.model.shared.SelectList(e.id, e.name, e.description) from MeetingType e where e.isObsolete = false")
    List<SelectList> findNoObsolete();
}
