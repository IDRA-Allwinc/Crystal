package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Notification;
import com.crystal.model.entities.audit.dto.NotificationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{

    Notification findByIdAndIsObsolete(Long id, boolean bIsObsolete);

    @Query("select new com.crystal.model.entities.audit.dto.NotificationDto(e.id, e.audit.id, e.title, e.message) " +
            "from Notification e where e.id=:notificationId and e.isObsolete = false")
    NotificationDto findDtoById(@Param("notificationId") Long notificationId);
}
