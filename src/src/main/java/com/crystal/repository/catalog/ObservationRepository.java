package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservationRepository extends JpaRepository<Observation,Long>{

}
