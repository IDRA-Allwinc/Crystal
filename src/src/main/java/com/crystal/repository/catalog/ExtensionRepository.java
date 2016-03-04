package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Extension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtensionRepository extends JpaRepository<Extension,Long>{

    public Extension findByIdAndIsObsolete(Long id, boolean b);

    @Query("select max(e.id) from Request r " +
            "inner join r.lstExtension e " +
            "where r.id=:requestId and e.id <> :extensionId and e.isObsolete = false")
    public Long findSecondLastExtensionIdByRequestId(@Param("requestId")Long requestId, @Param("extensionId")Long extensionId);

    @Query("select max(e.id) from Request r " +
            "inner join r.lstExtension e " +
            "where r.id=:requestId and e.isObsolete = false and e.isInitial = false ")
    public Long findLastExtensionIdByRequestId(@Param("requestId")Long requestId);

}
