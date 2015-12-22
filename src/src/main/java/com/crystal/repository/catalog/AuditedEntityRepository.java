package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.AuditedEntity;
import com.crystal.model.entities.catalog.dto.AuditedEntityDto;
import com.crystal.model.shared.SelectList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AuditedEntityRepository extends JpaRepository<AuditedEntity,Long>{

    @Query("SELECT new com.crystal.model.shared.SelectList(r.id, r.name) FROM AuditedEntity r")
    public List<SelectList> findSelectList();

    @Query("SELECT new com.crystal.model.shared.SelectList(r.id, r.name) FROM AuditedEntity r WHERE r.auditedEntityType.code = :code")
    public List<SelectList> findSelectList(@Param("code")String code);

    @Query("select new com.crystal.model.entities.catalog.dto.AuditedEntityDto(ae.id, ae.name, ae.responsible, ae. phone, ae.email, ae.auditedEntityType.id) from AuditedEntity ae where ae.id=:auditedEntityId")
    public AuditedEntityDto findDtoById(@Param("auditedEntityId")Long auditedEntityId);

}
