package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.AuditedEntityType;
import com.crystal.model.shared.SelectList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditedEntityTypeRepository extends JpaRepository<AuditedEntityType,Long>{

    public AuditedEntityType findByCode(String typeCode);

    @Query("select new com.crystal.model.shared.SelectList(aet.id, aet.name) from AuditedEntityType aet where aet.isObsolete = false")
    public List<SelectList> findNoObsolete();
}

