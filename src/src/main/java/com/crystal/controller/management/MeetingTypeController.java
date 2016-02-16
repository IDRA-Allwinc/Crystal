package com.crystal.controller.management;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.catalog.MeetingType;
import com.crystal.model.entities.catalog.dto.MeetingTypeDto;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.catalog.MeetingTypeService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class MeetingTypeController {

    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    MeetingTypeService service;
    @Autowired
    GridService gridService;

    @RequestMapping(value = "/management/meetingType/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/management/meetingType/index");
        return modelAndView;
    }

    @RequestMapping(value = "/management/meetingType/list", method = RequestMethod.GET)
    public Object list() {
        return gridService.toGrid(MeetingType.class, "isObsolete",false);
    }

    @RequestMapping(value = "/management/meetingType/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id) {
        ModelAndView modelView = new ModelAndView("/management/meetingType/upsert");

        try {
            service.upsert(id, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelView;
    }

    @RequestMapping(value = "/management/meetingType/doUpsert", method = RequestMethod.POST)
    public ResponseMessage doUpsert(@Valid MeetingTypeDto modelNew, BindingResult result) {

        ResponseMessage response = new ResponseMessage();

        try {

            if (DtoValidator.isValid(result, response) == false)
                return response;

            service.save(modelNew, response);

            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

    @RequestMapping(value = "/management/meetingType/doObsolete", method = RequestMethod.POST)
    public ResponseMessage doObsolete(@RequestParam(required = true) Long id) {

        ResponseMessage response = new ResponseMessage();

        try {
            service.doObsolete(id, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

}
