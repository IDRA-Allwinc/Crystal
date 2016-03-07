package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Event;
import com.crystal.model.entities.audit.dto.EventDto;
import com.crystal.model.shared.SelectList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long>{

    @Query("select new com.crystal.model.entities.audit.dto.EventDto(" +
            "e.id, e.description, e.audit.id, e.eventType.id, e.meetingDate, e.meetingType.id" +
            ") from Event e where e.id=:eventId and e.isObsolete = false")
    EventDto findDtoById(@Param("eventId")Long eventId);


    Event findByIdAndIsObsolete(Long id, boolean bIsObsolete);


    @Query("SELECT f.id FROM Event ev " +
            "inner join ev.lstFiles f WHERE ev.id=:id")
    List<Long> findAllFilesIdsByEventId(@Param("id") Long id);


    @Query("select new com.crystal.model.shared.SelectList(a.id, a.belongsTo, a.name, a.email, a.phone) from Assistant a " +
            "where a.event.id =:eventId and a.event.isObsolete=false")
    List<SelectList> findSelectedAssistantsByEventId(@Param("eventId") Long eventId);

}
