package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.Notification;
import com.crystal.model.entities.audit.dto.NotificationDto;
import com.crystal.service.shared.SharedLogExceptionService;
import org.springframework.web.servlet.ModelAndView;

public interface NotificationService {

    void upsert(Long id, Long auditId, ModelAndView modelView);

    Notification save(NotificationDto modelNew, ResponseMessage response);

    void doObsolete(Long id, ResponseMessage response);

    void sendNotification(Notification model, SharedLogExceptionService logException, ResponseMessage response);

    void sendNotificationById(Long id, SharedLogExceptionService logException, ResponseMessage response);
}
