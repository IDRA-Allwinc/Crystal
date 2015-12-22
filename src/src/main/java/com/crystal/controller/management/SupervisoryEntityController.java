package com.crystal.controller.management;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.catalog.dto.SupervisoryEntityDto;
import com.crystal.model.entities.catalog.view.SupervisoryEntityView;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.catalog.SupervisoryEntityService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class SupervisoryEntityController {

    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    SupervisoryEntityService supervisoryEntityService;
    @Autowired
    GridService gridService;

    @RequestMapping(value = "/management/supervisoryEntity/index", method = RequestMethod.GET)
    public String index() {
        return "/management/supervisoryEntity/index";
    }

    @RequestMapping(value = "/management/supervisoryEntity/list", method = RequestMethod.GET)
    public
    @ResponseBody
    Object list() {
        return gridService.toGrid(SupervisoryEntityView.class);
    }

    @RequestMapping(value = "/management/supervisoryEntity/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id) {
        ModelAndView modelView = new ModelAndView("/management/supervisoryEntity/upsert");
        try {
            supervisoryEntityService.upsert(id, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelView;
    }

    @RequestMapping(value = "/management/supervisoryEntity/doUpsert", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseMessage doUpsert(@Valid SupervisoryEntityDto modelNew, BindingResult result) {
        ResponseMessage response = new ResponseMessage();
        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            supervisoryEntityService.save(modelNew, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/management/supervisoryEntity/doObsolete", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseMessage doObsolete(@RequestParam(required = true) Long id) {
        ResponseMessage response = new ResponseMessage();
        try {
            supervisoryEntityService.doObsolete(id,response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }


}
