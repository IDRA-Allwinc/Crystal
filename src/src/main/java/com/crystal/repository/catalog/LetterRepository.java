package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Letter;
import com.crystal.model.entities.audit.LetterDto;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.shared.UploadFileGenericDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

    @Query("SELECT new com.crystal.model.entities.audit.LetterDto(e.id, e.number, e.description, a.id, e.isAttended) " +
            "FROM Letter e " +
            "LEFT JOIN e.audit a " +
            "WHERE e.id=:id AND e.isObsolete = 0")
    LetterDto findOneDto(@Param("id") Long id);

    Letter findByIdAndIsObsolete(Long id, boolean bIsObsolete);

    @Query("SELECT new com.crystal.model.shared.UploadFileGenericDto(e.uploadFileGeneric.id, e.uploadFileGeneric.fileName) FROM LetterUploadFileGenericRel e WHERE e.letter.id=:id and e.isAdditional = false and e.uploadFileGeneric.isObsolete=false")
    List<UploadFileGenericDto> findLetterInitialFileDtoById(@Param("id") Long id);

    @Query("SELECT e.uploadFileGeneric.id FROM LetterUploadFileGenericRel e WHERE e.letter.id=:id and e.isAdditional=false")
    List<Long> findInitialFilesIdsByLetterId(@Param("id") Long id);

    @Query("SELECT e.uploadFileGeneric.id FROM LetterUploadFileGenericRel e WHERE e.letter.id=:id")
    List<Long> findAllFilesIdsByLetterId(@Param("id") Long id);

    @Query("SELECT count(l) FROM Letter e INNER JOIN e.lstRequest l WHERE e.id=:id AND l.isObsolete = 0")
    Long countReqByLetterId(@Param("id") Long id);

    @Query("select l.isAttended from Letter l where l.id = :letterId and l.isAttended= false")
    Boolean isAttendedById(@Param("letterId") Long letterId);

    @Query("select new  com.crystal.model.entities.audit.dto.AttentionDto(l.id, l.attentionComment, l.isAttended, l.attentionDate,l.attentionUser.fullName, a.name, l.number) from Letter l " +
            "left join l.attentionUser u " +
            "left join l.audit a " +
            "where l.id=:requestId and l.isObsolete = false")
    public AttentionDto findAttentionInfoById(@Param("requestId") Long requestId);
}
