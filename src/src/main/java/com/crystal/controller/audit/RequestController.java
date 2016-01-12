package com.crystal.controller.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.audit.dto.RequestDto;
import com.crystal.model.entities.audit.view.RequestView;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.audit.RequestService;
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

/**
 * Created by Administrator on 1/5/2016.
 */
@RestController
public class RequestController {

    @Autowired
    GridService gridService;
    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    RequestService requestService;


    @RequestMapping(value = "/audit/request/list", method = RequestMethod.GET)
    public Object requestList(@RequestParam(required = true) Long idLetter) {
        return gridService.toGrid(RequestView.class, "idLetter", idLetter);
    }

    @RequestMapping(value = "/audit/request/upsert", method = RequestMethod.POST)
    public ModelAndView upsertRequest(@RequestParam(required = true) Long idLetter, @RequestParam(required = false) Long idRequest) {
        ModelAndView modelAndView = new ModelAndView("/audit/request/upsert");
        try {
            requestService.upsert(idLetter, idRequest, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/request/getAreas", method = RequestMethod.GET)
    public String findAreas(@RequestParam(required = true) String areaStr) {
        return new Gson().toJson(requestService.findAreasByRole(sharedUserService, areaStr));
    }

    @RequestMapping(value = "/audit/request/doUpsert", method = RequestMethod.POST)
    public ResponseMessage doUpsert(@Valid RequestDto modelNew, BindingResult result) {
        ResponseMessage response = new ResponseMessage();
        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            if (requestService.findByNumber(modelNew, response) == true) {
                return response;
            }

            requestService.save(modelNew, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/audit/request/doObsolete", method = RequestMethod.POST)
    public ResponseMessage doObsolete(@RequestParam(required = true) Long id) {
        ResponseMessage response = new ResponseMessage();
        try {
            requestService.doObsolete(id, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

}