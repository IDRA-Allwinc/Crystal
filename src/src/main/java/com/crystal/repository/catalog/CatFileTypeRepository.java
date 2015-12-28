package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.CatFileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatFileTypeRepository extends JpaRepository<CatFileType,Long>{

}
