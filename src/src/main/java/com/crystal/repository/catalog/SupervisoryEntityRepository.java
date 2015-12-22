package com.crystal.repository.catalog;

import com.crystal.model.entities.catalog.SupervisoryEntity;
import com.crystal.model.entities.catalog.dto.SupervisoryEntityDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisoryEntityRepository extends JpaRepository<SupervisoryEntity,Long>{
    @Query("select new com.crystal.model.entities.catalog.dto.SupervisoryEntityDto(se.id, se.name, se.responsible, se. phone, se.email, se.belongsTo) from SupervisoryEntity se where se.id=:supervisoryEntityId")
    public SupervisoryEntityDto findDtoById(@Param("supervisoryEntityId")Long supervisoryEntityId);
}
