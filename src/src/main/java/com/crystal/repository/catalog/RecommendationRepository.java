package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Recommendation;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.RecommendationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation,Long>{

    @Query("select new com.crystal.model.entities.audit.dto.RecommendationDto(r.id, r.number, r.description, r.initDate, r.endDate, r.isAttended, r.audit.id) from Recommendation r where r.id=:recommendationId and r.isObsolete = false")
    RecommendationDto findDtoById(@Param("recommendationId")Long recommendationId);


    Recommendation findByIdAndIsObsolete(Long id, boolean bIsObsolete);


    @Query("select r from Recommendation r where r.id <> :recommendationId and r.number=:numberStr and r.isObsolete = false")
    Recommendation findByNumberWithId(@Param("numberStr") String numberStr, @Param("recommendationId") Long recommendationId);


    Recommendation findByNumberAndIsObsolete(String number, boolean b);


    @Query("SELECT e.id FROM Recommendation r " +
            "inner join r.lstEvidences e WHERE r.id=:id")
    List<Long> findAllFilesIdsByRecommendationId(@Param("id") Long id);

    @Query("select r.isAttended from Recommendation r where r.id = :recommendationId and r.isObsolete = false")
    Boolean isAttendedById(@Param("recommendationId") Long recommendationId);


    //6000 referencia a com.crystal.model.shared.Constants
    @Query("select new  com.crystal.model.entities.audit.dto.AttentionDto(r.id, r.attentionComment, r.isAttended, r.attentionDate, r.attentionUser.fullName, a.name, r.number, 6000, r.isReplicated, r.replicatedAs) from Recommendation r " +
            "left join r.attentionUser u " +
            "inner join r.audit a " +
            "where r.id=:recommendationId and r.isObsolete = false")
    public AttentionDto findAttentionInfoById(@Param("recommendationId") Long recommendationId);

    @Query("select max(e.id) from Recommendation r " +
            "inner join r.lstExtension e " +
            "where r.id=:recommendationId and e.id <> :extensionId and e.isObsolete = false")
    public Long findSecondLastExtensionIdByRecommendationId(@Param("recommendationId")Long recommendationId, @Param("extensionId")Long extensionId);

    @Query("select max(e.id) from Recommendation r " +
            "inner join r.lstExtension e " +
            "where r.id=:recommendationId and e.isObsolete = false and e.isInitial = false ")
    public Long findLastExtensionIdByRecommendationId(@Param("recommendationId")Long recommendationId);

}
