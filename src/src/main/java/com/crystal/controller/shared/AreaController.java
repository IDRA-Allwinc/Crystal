package com.crystal.controller.shared;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.catalog.dto.AreaDto;
import com.crystal.model.entities.catalog.view.AreaView;
import com.crystal.model.shared.Constants;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.catalog.AreaService;
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
import java.util.HashMap;

@RestController
public class AreaController {

    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    AreaService areaService;
    @Autowired
    GridService gridService;

    @RequestMapping(value = "/shared/area/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/shared/area/index");
        return modelAndView;
    }

    @RequestMapping(value = "/shared/area/list", method = RequestMethod.GET)
    public Object list() {

        HashMap<String, Object> filters = new HashMap<>();

        filters.put("isObsolete", false);

        if (sharedUserService.loggedUserHasAuthority(Constants.AUTHORITY_DGPOP))
            filters.put("auditedEntityTypeCode", Constants.ENTITY_TYPE_UNDERSECRETARY);
        else
            filters.put("auditedEntityId", sharedUserService.getAuditedEntityIdByLoggedUserId(sharedUserService.getLoggedUserId()));

        return gridService.toGrid(AreaView.class, filters);
    }

    @RequestMapping(value = "/shared/area/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id) {
        ModelAndView modelView = new ModelAndView("/shared/area/upsert");
        try {
            areaService.upsert(id, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelView;
    }

    @RequestMapping(value = "/shared/area/doUpsert", method = RequestMethod.POST)
    public ResponseMessage doUpsert(@Valid AreaDto modelNew, BindingResult result) {
        ResponseMessage response = new ResponseMessage();
        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            areaService.save(modelNew, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/shared/area/doObsolete", method = RequestMethod.POST)
    public ResponseMessage doObsolete(@RequestParam(required = true) Long id) {
        ResponseMessage response = new ResponseMessage();
        try {
            areaService.doObsolete(id, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }


}
