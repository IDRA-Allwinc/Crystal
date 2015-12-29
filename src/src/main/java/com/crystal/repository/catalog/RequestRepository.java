package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request,Long>{

}
