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
}
