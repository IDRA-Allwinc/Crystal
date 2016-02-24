package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.ObservationType;
import com.crystal.model.shared.SelectList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationTypeRepository extends JpaRepository<ObservationType,Long>{

    ObservationType findByCode(String code);


    @Query("select new com.crystal.model.shared.SelectList(ot.code, ot.name, ot.id) from ObservationType ot where ot.isObsolete = false")
    List<SelectList> findNoObsolete();

}
