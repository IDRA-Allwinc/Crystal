package com.crystal.repositroy;

import com.crystal.model.entities.catalog.MeetingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("meetingTypeRepository")
public interface MeetingTypeRepository extends JpaRepository<MeetingType,Long>{

}
