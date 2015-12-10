package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.SupervisoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisoryEntityRepository extends JpaRepository<SupervisoryEntity,Long>{

}
