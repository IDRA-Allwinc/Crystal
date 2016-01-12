package com.crystal.controller.management;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.catalog.dto.AuditedEntityDto;
import com.crystal.model.entities.catalog.view.AuditedEntityView;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.catalog.AuditedEntityService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class AuditedEntityController {

    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    AuditedEntityService auditedEntityService;
    @Autowired
    GridService gridService;

    @RequestMapping(value = "/management/auditedEntity/index", method = RequestMethod.GET)
      public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/management/auditedEntity/index");
        return modelAndView;
    }

    @RequestMapping(value = "/management/auditedEntity/list", method = RequestMethod.GET)
    public Object list() {
        return gridService.toGrid(AuditedEntityView.class);
    }

    @RequestMapping(value = "/management/auditedEntity/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id) {
        ModelAndView modelView = new ModelAndView("/management/auditedEntity/upsert");
        try {
            auditedEntityService.upsert(id, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelView;
    }

    @RequestMapping(value = "/management/auditedEntity/doUpsert", method = RequestMethod.POST)
    public ResponseMessage doUpsert(@Valid AuditedEntityDto modelNew, BindingResult result) {
        ResponseMessage response = new ResponseMessage();
        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            auditedEntityService.save(modelNew, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/management/auditedEntity/doObsolete", method = RequestMethod.POST)
    public ResponseMessage doObsolete(@RequestParam(required = true) Long id) {
        ResponseMessage response = new ResponseMessage();
        try {
            auditedEntityService.doObsolete(id,response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

}
