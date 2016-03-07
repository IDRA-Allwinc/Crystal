package com.crystal.controller.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.audit.Notification;
import com.crystal.model.entities.audit.dto.NotificationDto;
import com.crystal.model.entities.audit.view.NotificationView;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.audit.NotificationService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class NotificationController {

    @Autowired
    GridService gridService;
    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    NotificationService notificationService;


    @RequestMapping(value = "/audit/notification/list", method = RequestMethod.GET)
    public Object notificationList(@RequestParam(required = true) Long id) {
        return gridService.toGrid(NotificationView.class, "auditId", id);
    }

    @RequestMapping(value = "/audit/notification/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id, @RequestParam(required = true) Long auditId) {
        ModelAndView modelView = new ModelAndView("/audit/notification/upsert");
        try {
            notificationService.upsert(id, auditId, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelView;
    }

    @RequestMapping(value = "/audit/notification/doUpsert", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseMessage doUpsert(@RequestBody @Valid NotificationDto modelNew, BindingResult result) {

        ResponseMessage response = new ResponseMessage();

        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            Notification model = notificationService.save(modelNew, response);
            if(model == null)
                return response;

            if(modelNew.getId() == null) //Sólo cuando es nuevo se debe enviar el correo
                notificationService.sendNotification(model, logException, response);

            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

    @RequestMapping(value = "/audit/notification/resend", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseMessage resend(@RequestParam(required = false) Long id) {

        ResponseMessage response = new ResponseMessage();
        response.setTitle("Reenviar notificación");

        try {
            notificationService.sendNotificationById(id, logException, response);
            if(response.isHasError())
                return response;

            response.setMessage("La notificación se envió de forma correcta a sus destinatarios");
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "resend", sharedUserService);
            response.setHasError(true);
            response.setTitle("Reenviar notificación");
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

    @RequestMapping(value = "/audit/notification/doObsolete", method = RequestMethod.POST)
    public ResponseMessage doObsolete(@RequestParam(required = true) Long id) {

        ResponseMessage response = new ResponseMessage();
        try {
            notificationService.doObsolete(id, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setTitle("Eliminar notificación");
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }
}
