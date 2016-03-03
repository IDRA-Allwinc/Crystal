package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.AuditedEntity;
import com.crystal.model.entities.catalog.dto.AuditedEntityDto;
import com.crystal.model.shared.SelectList;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuditedEntityRepository extends JpaRepository<AuditedEntity, Long> {

    @Query("SELECT new com.crystal.model.shared.SelectList(r.id, r.name) FROM AuditedEntity r")
    public List<SelectList> findSelectList();

    @Query("SELECT new com.crystal.model.shared.SelectList(r.id, r.name) FROM AuditedEntity r WHERE r.auditedEntityType.code = :code")
    public List<SelectList> findSelectList(@Param("code") String code);

    @Query("select new com.crystal.model.entities.catalog.dto.AuditedEntityDto(ae.id, ae.name, ae.responsible, ae. phone, ae.email, ae.auditedEntityType.id) from AuditedEntity ae where ae.id=:auditedEntityId")
    public AuditedEntityDto findDtoById(@Param("auditedEntityId") Long auditedEntityId);

    @Query("select new com.crystal.model.shared.SelectList(ae.id, ae.name, ae.responsible) from AuditedEntity ae where ae.auditedEntityType.code=:auditedEntityType and ae.isObsolete = false")
    public List<SelectList> findNoObsoleteByType(@Param("auditedEntityType") String auditedEntityType);

    @Query("select new com.crystal.model.shared.SelectList(ae.id, ae.name, ae.responsible) from AuditedEntity ae where ae.auditedEntityType.code=:auditedEntityType and ae.isObsolete = false and (ae.name like :auditStr or ae.responsible like :auditStr)")
    public List<SelectList> findNoObsoleteByTypeAndStr(@Param("auditedEntityType") String auditedEntityType, @Param("auditStr") String auditStr);

    @Query("select u.auditedEntity  from User u where u.id=:userId and u.enabled = true")
    public AuditedEntity findByUserId(@Param("userId") Long userId);


    @Query("select new com.crystal.model.shared.SelectList(ae.id, ae.name, ae.responsible, ae.email, ae.phone) from AuditedEntity ae " +
            "where " +
            "(ae.name like :assistantStr or  ae.responsible like :assistantStr)" +
            "and ae.isObsolete = false")
    public List<SelectList> findAssistantsByStr(@Param("assistantStr") String assistantStr, Pageable pageable);
}
