package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.SupervisoryEntity;
import com.crystal.model.entities.catalog.dto.SupervisoryEntityDto;
import com.crystal.model.shared.SelectList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupervisoryEntityRepository extends JpaRepository<SupervisoryEntity, Long> {
    @Query("select new com.crystal.model.entities.catalog.dto.SupervisoryEntityDto(se.id, se.name, se.responsible, se. phone, se.email, se.belongsTo) from SupervisoryEntity se where se.id=:supervisoryEntityId")
    public SupervisoryEntityDto findDtoById(@Param("supervisoryEntityId") Long supervisoryEntityId);

    @Query("select new com.crystal.model.shared.SelectList(se.id, se.name, se.responsible) from SupervisoryEntity se " +
            "where (se.name like :supervisoryStr or se.responsible like :supervisoryStr) " +
            "and se.isObsolete=false")
    public List<SelectList> findSupervisoryEntitiesByStr(@Param("supervisoryStr") String supervisoryStr);

    @Query("select new com.crystal.model.shared.SelectList(se.id, se.name, se.responsible, se.email, se.phone) from SupervisoryEntity se " +
            "where " +
            "(se.name like :assistantStr or  se.responsible like :assistantStr)" +
            "and se.isObsolete = false")
    public List<SelectList> findAssistantsByStr(@Param("assistantStr") String assistantStr, Pageable pageable);


    @Query("select new com.crystal.model.shared.SelectList(se.id, se.name, se.belongsTo) from SupervisoryEntity se where se.isObsolete=false")
    public List<SelectList> findNoObsolete();



    @Query(value = "select rv.year, rv.audit_number, rv.emitted, rv.attended, rv.recommendations, rv.observations, rv.responsibilities, rv.not_attended, rv.progress  " +
            "from report_view_by_supervisory rv " +
            "where rv.supervisory_entity = :idSupervisory " +
            "order by rv.year desc", nativeQuery = true)
    public List<Object> findDataReportBySupervisory(@Param("idSupervisory") Long idSupervisory);


    @Query(value = "select rv.entity_type, rv.audit_number, rv.emitted, rv.attended, rv.recommendations, rv.observations, rv.responsibilities, rv.not_attended, rv.progress, rv.entity_type_id  " +
            "from report_view_by_supervisory_year rv " +
            "where rv.supervisory_entity = :idSupervisory " +
            "and rv.year = :year " +
            "order by rv.entity_type_id asc", nativeQuery = true)
    public List<Object> findDataReportBySupervisoryYear(@Param("idSupervisory") Long idSupervisory, @Param("year") int year);


    @Query(value = "select rv.entity_name, rv.audit_number, rv.emitted, rv.attended, rv.recommendations, rv.observations, rv.responsibilities, rv.not_attended, rv.progress, rv.entity_id  " +
            "from report_view_by_supervisory_year_entitytype rv " +
            "where rv.supervisory_entity = :idSupervisory " +
            "and rv.year = :year " +
            "and rv.entity_type = :idEntityType " +
            "order by rv.entity_name asc", nativeQuery = true)
    public List<Object> findDataReportBySupervisoryYearEntityType(@Param("idSupervisory") Long idSupervisory, @Param("year") int year, @Param("idEntityType") Long idEntityType);


    @Query(value = "select rv.audit_name, rv.audit_number, rv.emitted, rv.attended, rv.recommendations, rv.observations, rv.responsibilities, rv.not_attended, rv.progress, rv.audit_id  " +
            "from report_view_by_supervisory_year_entity rv " +
            "where rv.supervisory_entity = :idSupervisory " +
            "and rv.year = :year " +
            "and rv.entity = :idEntity " +
            "order by rv.audit_name asc", nativeQuery = true)
    public List<Object> findDataReportBySupervisoryYearEntity(@Param("idSupervisory") Long idSupervisory, @Param("year") int year, @Param("idEntity") Long idEntity);





}
