package com.crystal.repository.catalog;

import com.crystal.model.entities.audit.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long>{

}
