package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.Area;
import com.crystal.model.entities.catalog.dto.AreaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AreaRepository extends JpaRepository<Area,Long>{

    @Query("select new com.crystal.model.entities.catalog.dto.AreaDto(a.id, a.name, a.responsible, a.phone, a.email, a.auditedEntity.id) from Area a where a.id=:areaId")
    public AreaDto findDtoById(@Param("areaId") Long areaId);

}
