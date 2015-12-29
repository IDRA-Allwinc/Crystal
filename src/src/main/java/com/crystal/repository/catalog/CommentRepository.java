package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{

}
