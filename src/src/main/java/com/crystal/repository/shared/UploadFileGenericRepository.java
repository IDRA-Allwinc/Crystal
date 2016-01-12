package com.crystal.repository.shared;

import com.crystal.model.shared.UploadFileGeneric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UploadFileGenericRepository extends JpaRepository<UploadFileGeneric, Long> {
    @Query("SELECT COUNT(upg) FROM UploadFileGeneric upg WHERE upg.creationUser.id =:userId AND LOWER(upg.fileName) =:fileName AND upg.isObsolete = false")
    Long alreadyExistFileByUser(@Param("userId") Long userId, @Param("fileName") String fileName);

    @Query("SELECT upg FROM UploadFileGeneric upg WHERE upg.creationUser.id =:userId AND upg.id =:id AND upg.isObsolete = false")
    UploadFileGeneric getValidUploadFileGenericByIdAndUserId(@Param("userId") Long userId, @Param("id") Long id);

    @Query("SELECT new com.crystal.model.shared.UploadFileGeneric(upg.path, upg.realFileName, upg.fileName) FROM UploadFileGeneric upg WHERE upg.id =:id AND upg.isObsolete = false")
    UploadFileGeneric getPathAndFilename(@Param("id") Long id);

    @Query("SELECT new com.crystal.model.shared.UploadFileGeneric(upg.path, upg.realFileName, upg.fileName) FROM UploadFileGeneric upg WHERE upg.creationUser.id =:userId AND upg.isObsolete = false")
    List<UploadFileGeneric> getUploadFilesByUserId(@Param("userId") Long userId);

    UploadFileGeneric findById(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE UploadFileGeneric e SET e.isObsolete = true WHERE e.id IN :ids")
    void setFilesObsoleteByIds(@Param("ids") List<Long> ids);

}

