package com.crystal.controller.management;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.catalog.dto.AuditedEntityDto;
import com.crystal.repository.catalog.AuditedEntityTypeRepository;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.catalog.AuditedEntityService;
import com.crystal.service.shared.SharedLogExceptionService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AuditedEntityController {

    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    AuditedEntityService auditedEntityService;

    @Autowired
    AuditedEntityTypeRepository auditedEntityTypeRepository;

    @RequestMapping(value = "/management/auditedEntity/index", method = RequestMethod.GET)
    public String index() {
        return "/management/auditedEntity/index";
    }

    @RequestMapping(value = "/management/auditedEntity/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id) {
        ModelAndView modelView = new ModelAndView("/management/auditedEntity/upsert");
        Gson gson = new Gson();
        modelView.addObject("lstAuditedEntityTypes",gson.toJson(auditedEntityTypeRepository.findNoObsolete()));
        return modelView;
    }

    @RequestMapping(value = "/management/auditedEntity/doUpsert", method = RequestMethod.POST)
    public ResponseMessage doUpsert(@Valid AuditedEntityDto modelNew, BindingResult result) {
        ResponseMessage response = new ResponseMessage();
        try {

        }catch (Exception e){
            response.setHasError(true);
        }finally {
            return response;
        }
    }
}
