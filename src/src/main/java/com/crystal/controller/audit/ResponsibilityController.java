package com.crystal.controller.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.CommentDto;
import com.crystal.model.entities.audit.dto.ResponsibilityDto;
import com.crystal.model.entities.audit.view.CommentUploadFileView;
import com.crystal.model.entities.audit.view.CommentView;
import com.crystal.model.entities.audit.view.ResponsibilityUploadFileView;
import com.crystal.model.entities.audit.view.ResponsibilityView;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.audit.CommentService;
import com.crystal.service.audit.ResponsibilityService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import com.crystal.service.shared.UpDwFileGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class ResponsibilityController {

    @Autowired
    GridService gridService;
    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    ResponsibilityService responsibilityService;
    @Autowired
    UpDwFileGenericService upDwFileGenericService;

    @RequestMapping(value = "/audit/responsibility/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/audit/responsibility/index");
        return modelAndView;
    }

    @RequestMapping(value = "/audit/responsibility/list", method = RequestMethod.GET)
    public Object commentList(@RequestParam(required = true) Long id) {
        return gridService.toGrid(ResponsibilityView.class, "auditId", id);
    }

    @RequestMapping(value = "/audit/responsibility/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id, @RequestParam(required = true) Long auditId) {
        ModelAndView modelView = new ModelAndView("/audit/responsibility/upsert");
        try {
            responsibilityService.upsert(id, auditId, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelView;
    }

    @RequestMapping(value = "/audit/responsibility/doUpsert", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseMessage doUpsert(@Valid ResponsibilityDto modelNew, BindingResult result) {

        ResponseMessage response = new ResponseMessage();

        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            if (responsibilityService.findByNumber(modelNew, response) == true) {
                return response;
            }

            responsibilityService.save(modelNew, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

    @RequestMapping(value = "/audit/responsibility/doObsolete", method = RequestMethod.POST)
    public ResponseMessage doObsolete(@RequestParam(required = true) Long id) {

        ResponseMessage response = new ResponseMessage();

        try {
            responsibilityService.doObsolete(id, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

    @RequestMapping(value = "/audit/responsibility/upsertViewDocs", method = RequestMethod.POST)
    public ModelAndView upsertViewDocs(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/audit/responsibility/upsertViewDocs");
        try {
            responsibilityService.upsertViewDocs(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsertViewDocs", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/responsibility/doDeleteUpFile", method = RequestMethod.POST)
    public ResponseMessage doDeleteUpFile(@RequestParam(required = true) Long responsibilityId, @RequestParam(required = true) Long upfileId) {
        ResponseMessage response = new ResponseMessage();
        try {
            responsibilityService.doDeleteUpFile(responsibilityId, upfileId, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doDeleteUpFile", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/audit/responsibility/listUfResponsibility", method = RequestMethod.GET)
    public Object listUfResponsibility(@RequestParam(required = true) Long responsibilityId) {
        return gridService.toGrid(ResponsibilityUploadFileView.class, "responsibilityId", responsibilityId);
    }

    @RequestMapping(value = "/audit/responsibility/attention", method = RequestMethod.POST)
    public ModelAndView attentionRequestAudit(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/audit/responsibility/attention");
        try {
            responsibilityService.showAttention(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "attentionRequestAudit", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/responsibility/doAttention", method = RequestMethod.POST)
    public ResponseMessage doAttention(@Valid AttentionDto attentionDto, BindingResult result) {
        ResponseMessage response = new ResponseMessage();
        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            responsibilityService.doAttention(attentionDto, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            logException.Write(ex, this.getClass(), "doAttention", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

}