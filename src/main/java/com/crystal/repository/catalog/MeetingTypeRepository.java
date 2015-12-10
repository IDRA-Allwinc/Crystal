package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.MeetingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingTypeRepository extends JpaRepository<MeetingType,Long>{

}
