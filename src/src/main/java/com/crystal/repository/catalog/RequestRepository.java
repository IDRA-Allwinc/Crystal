package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Request;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.RequestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("select new com.crystal.model.entities.audit.dto.RequestDto(r.id, r.number, r.description, r.initDate, r.endDate, r.letter.id) from Request r where r.id=:requestId and r.isObsolete = false")
    public RequestDto findDtoById(@Param("requestId") Long requestId);

    @Query("select r.id from Request r " +
            "inner join r.letter l " +
            "where r.number=:numberStr and r.isObsolete = false and l.id = :letterId")
    public Long findByNumberWithIdAndLetterId(@Param("numberStr") String numberStr, @Param("letterId") Long letterId);

    @Query("select new com.crystal.model.entities.audit.dto.RequestDto(r.id, r.number, r.isAttended) from Request r where r.id = :requestId and r.isObsolete = false")
    public RequestDto findDtoAttById(@Param("requestId") Long requestId);

    public Request findByIdAndIsObsolete(Long id, boolean b);

    @Query("select new  com.crystal.model.entities.audit.dto.AttentionDto(r.id, r.attentionComment, r.isAttended, r.attentionDate,r.attentionUser.fullName, a.name, l.number, r.number) from Request r " +
            "left join r.attentionUser u " +
            "inner join r.letter l " +
            "left join l.audit a " +
            "where r.id=:requestId and r.isObsolete = false")
    public AttentionDto findAttentionInfoById(@Param("requestId") Long requestId);

    @Query("select r.isAttended from Request r where r.id = :requestId and r.isObsolete = false")
    Boolean isAttendedById(@Param("requestId") Long requestId);

    @Query("select r.id from Letter l " +
            "inner join l.lstRequest r " +
            "where r.isObsolete = false " +
            "and l.id =:letterId ")
    List<Long> getRequestIdsNoObsoleteByLetter(@Param("letterId") Long letterId);

    @Query("select count(r.id) from Request r " +
            "where r.isObsolete = false " +
            "and r.isAttended = false " +
            "and r.id in (:requestIds)")
    Long countRequestUnattendedInIds(@Param("requestIds") List<Long> requestIds);

    @Query("select max(e.id) from Request r " +
            "inner join r.lstExtension e " +
            "where r.id=:requestId and e.id <> :extensionId and e.isObsolete = false")
    public Long findSecondLastExtensionIdByRequestId(@Param("requestId")Long requestId, @Param("extensionId")Long extensionId);

    @Query("select max(e.id) from Request r " +
            "inner join r.lstExtension e " +
            "where r.id=:requestId and e.isObsolete = false and e.isInitial = false ")
    public Long findLastExtensionIdByRequestId(@Param("requestId")Long requestId);
}
