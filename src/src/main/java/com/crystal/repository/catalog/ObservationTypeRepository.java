package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.ObservationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservationTypeRepository extends JpaRepository<ObservationType,Long>{

}
