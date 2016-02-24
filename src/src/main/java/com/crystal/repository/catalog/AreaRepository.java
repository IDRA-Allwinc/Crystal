package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.Area;
import com.crystal.model.entities.catalog.dto.AreaDto;
import com.crystal.model.shared.SelectList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

    @Query("select new com.crystal.model.entities.catalog.dto.AreaDto(a.id, a.name, a.responsible, a.phone, a.email, a.auditedEntity.id) from Area a where a.id=:areaId")
    public AreaDto findDtoById(@Param("areaId") Long areaId);

    @Query("select new com.crystal.model.shared.SelectList(a.id, a.name, a.responsible) from Area a " +
            "where " +
            "a.auditedEntity.auditedEntityType.code = com.crystal.model.shared.Constants.ENTITY_TYPE_SECRETARY " +
            "and (a.name like :areaStr or  a.responsible like :areaStr)" +
            "and a.isObsolete = false")
    public List<SelectList> findDGPOPAreasByStr(@Param("areaStr") String areaStr, Pageable pageable);

    @Query("select new com.crystal.model.shared.SelectList(a.id, a.name, a.responsible) from Area a " +
            "where a.auditedEntity.id = :auditedEntityId " +
            "and (a.name like :areaStr or  a.responsible like :areaStr)" +
            "and a.isObsolete = false")
    public List<SelectList> findAreasByAuditedEntityIdAndStr(@Param("auditedEntityId") Long auditedEntityId, @Param("areaStr") String areaStr, Pageable pageable);

    @Query("select new com.crystal.model.shared.SelectList(a.id, a.name, a.responsible) from Request r " +
            "inner join r.lstAreas as a " +
            "where r.id =:requestId ")
    public List<SelectList> findSelectedAreasByRequestId(@Param("requestId") Long requestId);

    @Query("select new com.crystal.model.shared.SelectList(ar.id, ar.name, ar.responsible) from Audit a " +
            "inner join a.lstAreas as ar " +
            "where a.id =:auditId and a.isObsolete=false")
    public List<SelectList> findSelectedAreasByAuditId(@Param("auditId") Long auditId);

    @Query("select new com.crystal.model.shared.SelectList(ar.id, ar.name, ar.responsible) from Comment c " +
            "inner join c.lstAreas as ar " +
            "where c.id =:commnetId and c.isObsolete=false")
    public List<SelectList> findSelectedAreasByCommentId(@Param("commnetId") Long commnetId);


    @Query("select new com.crystal.model.shared.SelectList(ar.id, ar.name, ar.responsible) from Recommendation r " +
            "inner join r.lstAreas as ar " +
            "where r.id =:recommendationId and r.isObsolete=false")
    public List<SelectList> findSelectedAreasByRecommendationId(@Param("recommendationId") Long recommendationId);

    @Query("select new com.crystal.model.shared.SelectList(ar.id, ar.name, ar.responsible) from Observation o " +
            "inner join o.lstAreas as ar " +
            "where o.id =:observationId and o.isObsolete=false")
    public List<SelectList> findSelectedAreasByObservationId(@Param("observationId") Long observationId);


}
