package com.crystal.controller.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.audit.dto.EventDto;
import com.crystal.model.entities.audit.view.EventUploadFileView;
import com.crystal.model.entities.audit.view.EventView;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.audit.EventService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import com.crystal.service.shared.UpDwFileGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class EventController {

    @Autowired
    GridService gridService;
    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    EventService eventService;
    @Autowired
    UpDwFileGenericService upDwFileGenericService;

    @RequestMapping(value = "/audit/event/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/audit/event/index");
        return modelAndView;
    }

    @RequestMapping(value = "/audit/event/list", method = RequestMethod.GET)
    public Object commentList(@RequestParam(required = true) Long id) {
        return gridService.toGrid(EventView.class, "auditId", id);
    }

    @RequestMapping(value = "/audit/event/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id, @RequestParam(required = true) Long auditId) {
        ModelAndView modelView = new ModelAndView("/audit/event/upsert");
        try {
            eventService.upsert(id, auditId, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelView;
    }

    @RequestMapping(value = "/audit/event/doUpsert", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseMessage doUpsert(@Valid EventDto modelNew, BindingResult result) {

        ResponseMessage response = new ResponseMessage();

        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            eventService.save(modelNew, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

    @RequestMapping(value = "/audit/event/doObsolete", method = RequestMethod.POST)
    public ResponseMessage doObsolete(@RequestParam(required = true) Long id) {

        ResponseMessage response = new ResponseMessage();

        try {
            eventService.doObsolete(id, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise l    a información e intente de nuevo");
        }

        return response;
    }

    @RequestMapping(value = "/audit/event/upsertViewDocs", method = RequestMethod.POST)
    public ModelAndView upsertViewDocs(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/audit/event/upsertViewDocs");
        try {
            eventService.upsertViewDocs(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsertViewDocs", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/event/doDeleteUpFile", method = RequestMethod.POST)
    public ResponseMessage doDeleteUpFile(@RequestParam(required = true) Long eventId, @RequestParam(required = true) Long upfileId) {
        ResponseMessage response = new ResponseMessage();
        try {
            eventService.doDeleteUpFile(eventId, upfileId, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doDeleteUpFile", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/audit/event/listUfEvent", method = RequestMethod.GET)
    public Object listUfEvent(@RequestParam(required = true) Long eventId) {
        return gridService.toGrid(EventUploadFileView.class, "eventId", eventId);
    }



}