package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.LetterUploadFileGenericRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterUploadFileGenericRelRepository extends JpaRepository<LetterUploadFileGenericRel, Long> {
}
