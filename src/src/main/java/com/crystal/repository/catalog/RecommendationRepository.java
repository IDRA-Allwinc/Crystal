package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Recommendation;
import com.crystal.model.entities.audit.dto.RecommendationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation,Long>{

    @Query("select new com.crystal.model.entities.audit.dto.RecommendationDto(r.id, r.number, r.description, r.initDate, r.endDate, r.audit.id) from Recommendation r where r.id=:recommendationId and r.isObsolete = false")
    RecommendationDto findDtoById(@Param("recommendationId")Long recommendationId);


    Recommendation findByIdAndIsObsolete(Long id, boolean bIsObsolete);


    @Query("select r from Recommendation r where r.id <> :recommendationId and r.number=:numberStr and r.isObsolete = false")
    Recommendation findByNumberWithId(@Param("numberStr") String numberStr, @Param("recommendationId") Long recommendationId);


    Recommendation findByNumberAndIsObsolete(String number, boolean b);
}
