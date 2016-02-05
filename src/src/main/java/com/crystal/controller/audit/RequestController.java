package com.crystal.controller.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.RequestDto;
import com.crystal.model.entities.audit.view.RequestUploadFileView;
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


    @RequestMapping(value = {"/previousRequest/request/list", "/audit/request/list"}, method = RequestMethod.GET)
    public Object requestList(@RequestParam(required = true) Long idLetter) {
        return gridService.toGrid(RequestView.class, "idLetter", idLetter);
    }

    @RequestMapping(value = "/previousRequest/request/upsert", method = RequestMethod.POST)
    public ModelAndView upsertRequest(@RequestParam(required = true) Long idLetter, @RequestParam(required = false) Long idRequest) {
        ModelAndView modelAndView = new ModelAndView("/previousRequest/request/upsert");
        try {
            requestService.upsert(idLetter, idRequest, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/request/upsert", method = RequestMethod.POST)
    public ModelAndView upsertRequestAudit(@RequestParam(required = true) Long idLetter, @RequestParam(required = false) Long idRequest) {
        ModelAndView modelAndView = new ModelAndView("/audit/request/upsert");
        try {
            requestService.upsert(idLetter, idRequest, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/request/doUpsert", method = RequestMethod.POST)
    public ResponseMessage doUpsertAudit(@Valid RequestDto modelNew, BindingResult result) {
        ResponseMessage response = new ResponseMessage();
        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            if (requestService.findByNumber(modelNew, response) == true) {
                return response;
            }

            requestService.save(modelNew, null, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/previousRequest/request/getAreas", method = RequestMethod.GET)
    public String findAreas(@RequestParam(required = true) String areaStr) {
        return new Gson().toJson(requestService.findAreasByRole(sharedUserService, areaStr));
    }

    @RequestMapping(value = "/previousRequest/request/doUpsert", method = RequestMethod.POST)
    public ResponseMessage doUpsert(@Valid RequestDto modelNew, BindingResult result) {
        ResponseMessage response = new ResponseMessage();
        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            if (requestService.findByNumber(modelNew, response) == true) {
                return response;
            }

            requestService.save(modelNew, null, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = {"/previousRequest/request/doObsolete", "/audit/request/doObsolete"}, method = RequestMethod.POST)
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

    @RequestMapping(value = "/previousRequest/request/upsertViewDocs", method = RequestMethod.POST)
    public ModelAndView upsertViewDocs(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/previousRequest/requ1est/upsertViewDocs");
        try {
            requestService.upsertViewDocs(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsertViewDocs", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/request/upsertViewDocs", method = RequestMethod.POST)
    public ModelAndView upsertViewDocsAudit(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/audit/request/upsertViewDocs");
        try {
            requestService.upsertViewDocs(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsertViewDocsAudit", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/previousRequest/request/listUfRequest", method = RequestMethod.GET)
    public Object listUfRequest(@RequestParam(required = true) Long requestId) {
        return gridService.toGrid(RequestUploadFileView.class, "requestId", requestId);
    }

    @RequestMapping(value = "/previousRequest/request/attention", method = RequestMethod.POST)
    public ModelAndView attentionRequest(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/previousRequest/request/attention");
        try {
            requestService.attention(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "attentionRequest", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/request/attention", method = RequestMethod.POST)
    public ModelAndView attentionRequestAudit(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/audit/request/attention");
        try {
            requestService.attention(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "attentionRequestAudit", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/previousRequest/request/doAttention", "/audit/request/doAttention"}, method = RequestMethod.POST)
    public ResponseMessage doAttention(@Valid AttentionDto modelNew, BindingResult result) {
        ResponseMessage response = new ResponseMessage();
        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            requestService.save(null, modelNew, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            logException.Write(ex, this.getClass(), "doAttention", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }


    @RequestMapping(value = {"/previousRequest/request/doDeleteUpFile", "/audit/request/doDeleteUpFile"}, method = RequestMethod.POST)
    public ResponseMessage doDeleteUpFile(@RequestParam(required = true) Long requestId, @RequestParam(required = true) Long upfileId) {
        ResponseMessage response = new ResponseMessage();
        try {
            requestService.doDeleteUpFile(requestId, upfileId, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doDeleteUpFile", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }
}