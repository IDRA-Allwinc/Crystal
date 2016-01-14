package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Request;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.RequestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("select new com.crystal.model.entities.audit.dto.RequestDto(r.id, r.number, r.description, r.limitTimeDays, r.letter.id) from Request r where r.id=:requestId and r.isObsolete = false")
    public RequestDto findDtoById(@Param("requestId") Long requestId);

    @Query("select r from Request r where r.number=:numberStr and r.isObsolete = false")
    public Request findByNumber(@Param("numberStr") String numberStr);

    @Query("select r from Request r where r.id <> :requestId and r.number=:numberStr and r.isObsolete = false")
    public Request findByNumberWithId(@Param("numberStr") String numberStr, @Param("requestId") Long requestId);

    @Query("select r.number from Request r where r.id = :requestId and r.isObsolete = false")
    String findNumberById(@Param("requestId") Long requestId);

    Request findByIdAndIsObsolete(Long id, boolean b);

    @Query("select new  com.crystal.model.entities.audit.dto.AttentionDto(r.id, r.attentionComment, r.isAttended, r.attentionDate,r.attentionUser.fullName) from Request r " +
            "left join r.attentionUser u " +
            "where r.id=:requestId and r.isObsolete = false")
    public AttentionDto findAttentionInfoById(@Param("requestId") Long requestId);
}
