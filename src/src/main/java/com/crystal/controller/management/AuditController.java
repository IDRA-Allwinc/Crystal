package com.crystal.controller.management;

import com.crystal.service.account.SharedUserService;
import com.crystal.service.shared.AuditService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
//
//    @RequestMapping(value = "/management/auditedEntity/doUpsert", method = RequestMethod.POST)
//    public ResponseMessage doUpsert(@Valid AuditedEntityDto modelNew, BindingResult result) {
//        ResponseMessage response = new ResponseMessage();
//        try {
//            if (DtoValidator.isValid(result, response) == false)
//                return response;
//
//            auditedEntityService.save(modelNew, response);
//        } catch (Exception ex) {
//            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
//            response.setHasError(true);
//            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
//        } finally {
//            return response;
//        }
//    }
//
//    @RequestMapping(value = "/management/auditedEntity/doObsolete", method = RequestMethod.POST)
//    public ResponseMessage doObsolete(@RequestParam(required = true) Long id) {
//        ResponseMessage response = new ResponseMessage();
//        try {
//            auditedEntityService.doObsolete(id,response);
//        } catch (Exception ex) {
//            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
//            response.setHasError(true);
//            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
//        } finally {
//            return response;
//        }
//    }

}
