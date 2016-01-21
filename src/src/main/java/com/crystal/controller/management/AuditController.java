package com.crystal.controller.management;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.audit.dto.AuditDto;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.shared.AuditService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class AuditController {

    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    GridService gridService;
    @Autowired
    AuditService auditservice;

    @RequestMapping(value = "/audit/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/audit/index");
        return modelAndView;
    }

    @RequestMapping(value = "/audit/list", method = RequestMethod.GET)
    public Object list() {
        return auditservice.getAuditsByRole(gridService);
    }

    @RequestMapping(value = "/audit/getSupervisoryEntities", method = RequestMethod.GET)
    public String getSupervisoryEntities(@RequestParam(required = true) String supervisoryStr) {
        return new Gson().toJson(auditservice.getSupervisoryEntities(supervisoryStr));
    }

    @RequestMapping(value = "/audit/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id) {
        ModelAndView modelView = new ModelAndView("/audit/upsert");
        try {
            auditservice.upsert(id, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelView;
    }

    @RequestMapping(value = "/audit/doUpsert", method = RequestMethod.POST)
    public ResponseMessage doUpsert(@Valid AuditDto modelNew, BindingResult result) {
        ResponseMessage response = new ResponseMessage();
        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            auditservice.save(modelNew, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/audit/doObsolete", method = RequestMethod.POST)
    public ResponseMessage doObsolete(@RequestParam(required = true) Long id) {
        ResponseMessage response = new ResponseMessage();
        try {
            Long userId = sharedUserService.getLoggedUserId();
            auditservice.doObsolete(id, userId, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }


    @RequestMapping(value = "/audit/fillAudit", method = RequestMethod.POST)
    public ModelAndView fillAudit(@RequestParam(required = true) Long id) {
        ModelAndView modelView = new ModelAndView("/audit/fillAudit");
        try {
            auditservice.fillAudit(id, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "fillAudit", sharedUserService);
        }
        return modelView;
    }

}
