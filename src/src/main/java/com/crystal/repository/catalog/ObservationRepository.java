package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Observation;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.ObservationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationRepository extends JpaRepository<Observation,Long>{

    @Query("select new com.crystal.model.entities.audit.dto.ObservationDto(o.id, o.number, o.description, o.initDate, o.endDate, o.isAttended, o.audit.id) from Observation o where o.id=:observationId and o.isObsolete = false")
    ObservationDto findDtoById(@Param("observationId")Long observationId);


    Observation findByIdAndIsObsolete(Long id, boolean bIsObsolete);


    @Query("select o from Observation o where o.id <> :observationId and o.number=:numberStr and o.isObsolete = false")
    Observation findByNumberWithId(@Param("numberStr") String numberStr, @Param("observationId") Long observationId);


    Observation findByNumberAndIsObsolete(String number, boolean b);


    @Query("SELECT e.id FROM Observation o " +
            "inner join o.lstEvidences e WHERE o.id=:id")
    List<Long> findAllFilesIdsByObservationId(@Param("id") Long id);

    @Query("select o.isAttended from Observation o where o.id = :observationId and o.isObsolete = false")
    Boolean isAttendedById(@Param("observationId") Long observationId);


    //7000 referencia a com.crystal.model.shared.Constants
    @Query("select new  com.crystal.model.entities.audit.dto.AttentionDto(o.id, o.attentionComment, o.isAttended, o.attentionDate, o.attentionUser.fullName, a.name, o.number, 7000) from Observation o " +
            "left join o.attentionUser u " +
            "inner join o.audit a " +
            "where o.id=:observationId and o.isObsolete = false")
    public AttentionDto findAttentionInfoById(@Param("observationId") Long observationId);


}
