package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRepository extends JpaRepository<Letter,Long>{

}
