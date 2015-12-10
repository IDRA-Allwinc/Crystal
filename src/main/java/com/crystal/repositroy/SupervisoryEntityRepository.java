package com.crystal.repositroy;

import com.crystal.model.entities.catalog.SupervisoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("supervisoryEntityRepository")
public interface SupervisoryEntityRepository extends JpaRepository<SupervisoryEntity,Long>{

}
