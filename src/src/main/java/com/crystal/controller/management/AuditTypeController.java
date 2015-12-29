package com.crystal.controller.management;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.catalog.dto.AuditTypeDto;
import com.crystal.model.entities.catalog.view.AuditTypeView;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.catalog.AuditTypeService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class AuditTypeController {

    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    AuditTypeService auditTypeService;
    @Autowired
    GridService gridService;

    @RequestMapping(value = "/management/auditType/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/management/auditType/index");
        return modelAndView;
    }

    @RequestMapping(value = "/management/auditType/list", method = RequestMethod.GET)
    public Object list() {
        return gridService.toGrid(AuditTypeView.class);
    }

    @RequestMapping(value = "/management/auditType/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id) {
        ModelAndView modelView = new ModelAndView("/management/auditType/upsert");
        try {
            auditTypeService.upsert(id, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelView;
    }

    @RequestMapping(value = "/management/auditType/doUpsert", method = RequestMethod.POST)
    public ResponseMessage doUpsert(@Valid AuditTypeDto modelNew, BindingResult result) {
        ResponseMessage response = new ResponseMessage();
        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            auditTypeService.save(modelNew, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/management/auditType/doObsolete", method = RequestMethod.POST)
    public ResponseMessage doObsolete(@RequestParam(required = true) Long id) {
        ResponseMessage response = new ResponseMessage();
        try {
            auditTypeService.doObsolete(id, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }


}
