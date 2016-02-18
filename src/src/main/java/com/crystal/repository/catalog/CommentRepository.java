package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Comment;
import com.crystal.model.entities.audit.Letter;
import com.crystal.model.entities.audit.dto.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{

    @Query("select new com.crystal.model.entities.audit.dto.CommentDto(c.id, c.number, c.description, c.initDate,c.endDate, c.audit.id) from Comment c where c.id=:commentId and c.isObsolete = false")
    public CommentDto findDtoById(@Param("commentId") Long commentId);

    Comment findByIdAndIsObsolete(Long id, boolean bIsObsolete);

    @Query("SELECT e.id FROM Comment c " +
            "inner join c.lstEvidences e WHERE c.id=:id")
    List<Long> findAllFilesIdsByCommentId(@Param("id") Long id);
}
