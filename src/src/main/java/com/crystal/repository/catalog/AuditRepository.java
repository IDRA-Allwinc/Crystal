package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Audit;
import com.crystal.model.entities.audit.dto.AuditDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface AuditRepository extends JpaRepository<Audit,Long>{

    @Query(value = "select new com.crystal.model.entities.audit.dto.AuditDto(a.id, a.letterNumber,a.letterDate,a.number,a.name,a.objective,a.reviewInitDate,a.reviewEndDate,a.auditedYear,a.budgetProgram," +
            "       se.id, se.name, se.responsible," +
            "       ae.id, ae.name, ae.responsible," +
            "       at.id, at.description) from Audit a " +
            "inner join a.supervisoryEntity se " +
            "inner join a.auditedEntity ae " +
            "inner join a.auditType at where a.isObsolete=false")
    public AuditDto findDtoById(@RequestParam("auditId")Long auditId);

}
