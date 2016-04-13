package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Audit;
import com.crystal.model.entities.audit.dto.AuditDto;
import com.crystal.model.shared.SelectList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

    @Query(value = "select new com.crystal.model.entities.audit.dto.AuditDto(a.id, a.letterNumber,a.letterDate,a.number,a.name,a.objective,a.reviewInitDate,a.reviewEndDate,a.auditedYear,a.budgetProgram," +
            "       se.id, se.name, se.responsible," +
            "       ae.id, ae.name, ae.responsible," +
            "       at.id, at.name) " +
            "from Audit a " +
            "inner join a.supervisoryEntity se " +
            "inner join a.auditedEntity ae " +
            "inner join a.auditType at where a.isObsolete=false " +
            "and a.id=:auditId")
    public AuditDto findDtoById(@Param("auditId") Long auditId);

    Audit findByIdAndIsObsolete(Long id, boolean bIsObsolete);


    @Query(value = "select a from Audit a " +
            "where a.isObsolete=false " +
            "and a.letterNumber=:letterNumber " +
            "and a.id <> :auditId")
    public Audit findByLetterNumberAndId(@Param("letterNumber") String letterNumber, @Param("auditId") Long auditId);


    @Query(value = "select new com.crystal.model.shared.SelectList(o.number, o.description, o.endDate) from Audit a " +
            "left join a.lstObservation o " +
            "where o.isAttended=false " +
            "and o.isObsolete=false " +
            "and a.isObsolete= false " +
            "and a.id=:auditId order by o.endDate asc")
    public List<SelectList> getUnattendedObservations(@Param("auditId") Long auditId);


    @Query(value = "select new com.crystal.model.shared.SelectList(r.number, r.description, r.endDate) from Audit a " +
            "left join a.lstRecommendation r " +
            "where r.isAttended=false " +
            "and r.isObsolete=false " +
            "and a.isObsolete= false " +
            "and a.id=:auditId order by r.endDate asc")
    public List<SelectList> getUnattendedRecommendations(@Param("auditId") Long auditId);

    @Query(value = "select new com.crystal.model.shared.SelectList(res.number, res.description, res.endDate) from Audit a " +
            "left join a.lstResponsibility res " +
            "where res.isAttended=false " +
            "and res.isObsolete=false " +
            "and a.isObsolete= false " +
            "and a.id=:auditId order by res.endDate asc")
    public List<SelectList> getUnattendedResponsibility(@Param("auditId") Long auditId);


}
