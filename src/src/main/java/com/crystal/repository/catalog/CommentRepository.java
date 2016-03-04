package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Comment;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{

    @Query("select new com.crystal.model.entities.audit.dto.CommentDto(c.id, c.number, c.description, c.initDate,c.endDate, c.isAttended, c.audit.id) from Comment c where c.id=:commentId and c.isObsolete = false")
    public CommentDto findDtoById(@Param("commentId") Long commentId);

    Comment findByIdAndIsObsolete(Long id, boolean bIsObsolete);

    @Query("SELECT e.id FROM Comment c " +
            "inner join c.lstEvidences e WHERE c.id=:id")
    List<Long> findAllFilesIdsByCommentId(@Param("id") Long id);

    @Query("select c.isAttended from Comment c where c.id = :commentId and c.isObsolete = false")
    Boolean isAttendedById(@Param("commentId") Long commentId);

    //5000 referencia a com.crystal.model.shared.Constants
    @Query("select new  com.crystal.model.entities.audit.dto.AttentionDto(c.id, c.attentionComment, c.isAttended, c.attentionDate, c.attentionUser.fullName, a.name, c.number, 5000) from Comment c " +
            "left join c.attentionUser u " +
            "inner join c.audit a " +
            "where c.id=:commentId and c.isObsolete = false")
    public AttentionDto findAttentionInfoById(@Param("commentId") Long commentId);

    @Query("select max(e.id) from Comment c " +
            "inner join c.lstExtension e " +
            "where c.id=:commentId and e.id <> :extensionId and e.isObsolete = false")
    public Long findSecondLastExtensionIdByCommentId(@Param("commentId")Long commentId, @Param("extensionId")Long extensionId);

    @Query("select max(e.id) from Comment c " +
            "inner join c.lstExtension e " +
            "where c.id=:commentId and e.isObsolete = false and e.isInitial = false ")
    public Long findLastExtensionIdByCommentId(@Param("commentId")Long commentId);
}
