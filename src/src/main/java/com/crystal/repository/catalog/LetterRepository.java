package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Letter;
import com.crystal.model.entities.audit.LetterDto;
import com.crystal.model.shared.UploadFileGenericDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LetterRepository extends JpaRepository<Letter,Long>{

    @Query("SELECT new com.crystal.model.entities.audit.LetterDto(e.id, e.number, e.description) FROM Letter e WHERE e.id=:id AND e.isObsolete = 0")
    LetterDto findOneDto(@Param("id") Long id);

    Letter findByIdAndIsObsolete(Long id, boolean bIsObsolete);

    @Query("SELECT new com.crystal.model.shared.UploadFileGenericDto(e.uploadFileGeneric.id, e.uploadFileGeneric.fileName) FROM LetterUploadFileGenericRel e WHERE e.letter.id=:id")
    List<UploadFileGenericDto> findFilesById(@Param("id") Long id);

    @Query("SELECT e.uploadFileGeneric.id FROM LetterUploadFileGenericRel e WHERE e.letter.id=:id")
    List<Long> findFileIdsById(@Param("id") Long id);

    @Query("SELECT count(l) FROM Letter e INNER JOIN e.lstRequest l WHERE e.id=:id AND l.isObsolete = 0")
    Long countReqByLetterId(@Param("id") Long id);
}
