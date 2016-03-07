package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.Audit;
import com.crystal.model.entities.audit.Notification;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.NotificationDto;
import com.crystal.model.entities.catalog.Area;
import com.crystal.model.shared.Constants;
import com.crystal.model.shared.LogException;
import com.crystal.model.shared.SelectList;
import com.crystal.repository.account.UserRepository;
import com.crystal.repository.catalog.AuditRepository;
import com.crystal.repository.catalog.NotificationRepository;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.catalog.AreaService;
import com.crystal.service.shared.EmailHubService;
import com.crystal.service.shared.SharedLogExceptionService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    SharedUserService sharedUserService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AreaService areaService;

    @Autowired
    AuditRepository auditRepository;

    @Autowired
    EmailHubService emailService;

    @Override
    public void upsert(Long id, Long auditId, ModelAndView modelView) {
        Gson gson = new Gson();
        NotificationDto model;
        List<SelectList> lstDestination = areaService.getAreasWithResponsibleByAuditId(auditId);

        if (id != null) {
            model = notificationRepository.findDtoById(id);
            List<SelectList> lstSelectedAreas = areaService.getSelectedAreasByNotificationId(id);
            for (SelectList item : lstDestination){
                for (SelectList selected : lstSelectedAreas){
                    if(item.getId().equals(selected.getId()))
                        item.setIsSelected(true);
                }
            }
        } else {
            model = new NotificationDto();
            model.setAuditId(auditId);
            for (SelectList item : lstDestination)
                item.setIsSelected(true);
        }

        modelView.addObject("model", gson.toJson(model));
        modelView.addObject("lstDestination", gson.toJson(lstDestination));
    }

    @Override
    public Notification save(NotificationDto modelNew, ResponseMessage response) {
        Notification model = businessValidation(modelNew, response);
        if (response.isHasError())
            return null;

        doSave(model);

        return model;
    }

    @Override
    public void doObsolete(Long id, ResponseMessage response) {
        Notification model = notificationRepository.findOne(id);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("La notificación ya fue eliminada o no existe en el sistema.");
            response.setTitle("Eliminar notificación");
            return;
        }

        if (model.isObsolete() == true) {
            response.setHasError(true);
            response.setMessage("La notificación ya fue eliminada.");
            response.setTitle("Eliminar notificación");
            return;
        }

        model.setObsolete(true);
        model.setDelAudit(sharedUserService.getLoggedUserId());

        doSave(model);
    }

    @Override
    public void sendNotification(Notification model, SharedLogExceptionService logException, ResponseMessage response) {
        List<Area> lstDestinations = model.getLstAreas();
        for (Area dest :  lstDestinations){
            try {
                emailService.sendEmail(dest.getEmail(), Constants.SystemSettings.Map.get(Constants.SystemSettings.EMAIL_NOTIFICATION_SENDER), model.getTitle(),
                        String.format(Constants.SystemSettings.Map.get(Constants.SystemSettings.EMAIL_NOTIFICATION_TEMPLATE_BODY), dest.getResponsible(), model.getMessage()));
            }catch (Exception ex){
                logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
                response.setMessage("No fue posible enviar el correo a todos los destinatarios, por favor intente de nuevo");
                response.setHasError(true);
            }
        }
    }

    @Override
    public void sendNotificationById(Long id, SharedLogExceptionService logException, ResponseMessage responseMessage) {
        Notification model = notificationRepository.findByIdAndIsObsolete(id, false);
        if (model == null) {
            responseMessage.setHasError(true);
            responseMessage.setMessage("La notificación ya fue eliminada o no existe en el sistema.");
            return;
        }

        sendNotification(model, logException, responseMessage);
    }


    private Notification businessValidation(NotificationDto modelNew,ResponseMessage responseMessage) {
        Notification model;

        List<Long> lstDestinationAreaIds = modelNew.getDestinationIds();

        if(lstDestinationAreaIds == null || lstDestinationAreaIds.size() == 0){
            responseMessage.setHasError(true);
            responseMessage.setMessage("Debe seleccionar al menos un destinatario.");
            return null;
        }

        Long auditId = modelNew.getAuditId();

        if(auditId == null){
            responseMessage.setHasError(true);
            responseMessage.setMessage("No se ha asignado a una auditoría.");
            return null;
        }

        Long id = modelNew.getId();
        if(id != null){
            model = notificationRepository.findByIdAndIsObsolete(id, false);
            if (model == null) {
                responseMessage.setHasError(true);
                responseMessage.setMessage("La notificación ya fue eliminada o no existe en el sistema.");
                return null;
            }
            model.setUpdAudit(sharedUserService.getLoggedUserId());
        }else{
            model = new Notification();
            model.setInsAudit(sharedUserService.getLoggedUserId());
            model.setTitle(modelNew.getTitle());
            model.setMessage(modelNew.getMessage());
            Audit audit = new Audit();
            audit.setId(auditId);
            model.setAudit(audit);
        }

        //Validate destination
        List<SelectList> lstDestination = areaService.getAreasWithEmailResponsibleByAuditId(auditId);
        List<Area> lstAreas = new ArrayList<>();
        Area areaToAdd;

        boolean bIsFound;
        for (Long idArea : lstDestinationAreaIds){
            bIsFound = false;
            for(SelectList item : lstDestination){
                if (!item.getId().equals(idArea))
                    continue;

                areaToAdd = new Area();
                areaToAdd.setId(idArea);
                areaToAdd.setEmail(item.getName());
                areaToAdd.setResponsible(item.getDescription());
                lstAreas.add(areaToAdd);

                bIsFound = true;
            }

            if(bIsFound == false){
                responseMessage.setHasError(true);
                responseMessage.setMessage("Al menos uno de los destinatarios no es válido o no fue eliminado de la auditoría (ID: " + idArea + ").");
                return null;
            }
        }

        model.setLstAreas(lstAreas);

        return model;
    }

    @Transactional
    private void doSave(Notification model) {
        notificationRepository.saveAndFlush(model);
    }

}
