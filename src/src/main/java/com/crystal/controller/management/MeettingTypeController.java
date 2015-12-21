package com.crystal.controller.management;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.catalog.EventType;
import com.crystal.model.entities.catalog.MeetingTypeDto;
import com.crystal.model.entities.catalog.MeetingType;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.catalog.EventTypeService;
import com.crystal.service.catalog.MeetingTypeService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class MeettingTypeController {

    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    MeetingTypeService service;
    @Autowired
    private GridService gridService;

    @RequestMapping(value = "/management/meetingType/index", method = RequestMethod.GET)
    public String index() {
        return "/management/meetingType/index";
    }

    @RequestMapping(value = "/management/meetingType/list", method = RequestMethod.GET/*, params = {"limit", "offset", "sort", "order", "search", "filter"}*/)
    public
    @ResponseBody
    Object list() {
        return gridService.toGrid(MeetingType.class);
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
    public
    @ResponseBody
    ResponseMessage doUpsert(@Valid MeetingTypeDto modelNew, BindingResult result, Model m) {

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
    public
    @ResponseBody
    ResponseMessage doObsolete(@RequestParam(required = true) Long id) {

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
