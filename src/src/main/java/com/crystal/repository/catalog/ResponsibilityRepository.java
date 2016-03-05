package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Responsibility;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.ResponsibilityDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponsibilityRepository extends JpaRepository<Responsibility,Long>{


    @Query("select new com.crystal.model.entities.audit.dto.ResponsibilityDto(r.id, r.number, r.description, r.initDate, r.endDate, r.isAttended, r.audit.id) from Responsibility r where r.id=:responsibilityId and r.isObsolete = false")
    ResponsibilityDto findDtoById(@Param("responsibilityId")Long responsibilityId);


    Responsibility findByIdAndIsObsolete(Long id, boolean bIsObsolete);


    @Query("select r from Responsibility r where r.id <> :responsibilityId and r.number=:numberStr and r.isObsolete = false")
    Responsibility findByNumberWithId(@Param("numberStr") String numberStr, @Param("responsibilityId") Long responsibilityId);


    Responsibility findByNumberAndIsObsolete(String number, boolean b);


    @Query("SELECT e.id FROM Responsibility r " +
            "inner join r.lstEvidences e WHERE r.id=:id")
    List<Long> findAllFilesIdsByResponsibilityId(@Param("id") Long id);

    @Query("select r.isAttended from Responsibility r where r.id = :responsibilityId and r.isObsolete = false")
    Boolean isAttendedById(@Param("responsibilityId") Long responsibilityId);


    //8000 referencia a com.crystal.model.shared.Constants
    @Query("select new  com.crystal.model.entities.audit.dto.AttentionDto(r.id, r.attentionComment, r.isAttended, r.attentionDate, r.attentionUser.fullName, a.name, r.number, 8000) from Responsibility r " +
            "left join r.attentionUser u " +
            "inner join r.audit a " +
            "where r.id=:responsibilityId and r.isObsolete = false")
    public AttentionDto findAttentionInfoById(@Param("responsibilityId") Long responsibilityId);


    @Query("select max(e.id) from Responsibility r " +
            "inner join r.lstExtension e " +
            "where r.id=:responsibilityId and e.id <> :extensionId and e.isObsolete = false")
    public Long findSecondLastExtensionIdByResponsibilityId(@Param("responsibilityId")Long responsibilityId, @Param("extensionId")Long extensionId);

    @Query("select max(e.id) from Responsibility r " +
            "inner join r.lstExtension e " +
            "where r.id=:responsibilityId and e.isObsolete = false and e.isInitial = false ")
    public Long findLastExtensionIdByResponsibilityId(@Param("responsibilityId")Long responsibilityId);

    
}
