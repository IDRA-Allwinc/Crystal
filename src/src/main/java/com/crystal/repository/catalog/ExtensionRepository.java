package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Extension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtensionRepository extends JpaRepository<Extension,Long>{

}
