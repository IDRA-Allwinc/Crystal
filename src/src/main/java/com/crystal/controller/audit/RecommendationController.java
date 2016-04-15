package com.crystal.controller.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.RecommendationDto;
import com.crystal.model.entities.audit.view.RecommendationExtensionView;
import com.crystal.model.entities.audit.view.RecommendationUploadFileView;
import com.crystal.model.entities.audit.view.RecommendationView;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.audit.RecommendationService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class RecommendationController {

    @Autowired
    GridService gridService;
    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    RecommendationService recommendationService;

    @RequestMapping(value = "/audit/recommendation/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/audit/recommendation/index");
        return modelAndView;
    }

    @RequestMapping(value = "/audit/recommendation/list", method = RequestMethod.GET)
    public Object recommendationList(@RequestParam(required = true) Long id) {
        return gridService.toGrid(RecommendationView.class, "auditId", id);
    }

    @RequestMapping(value = "/audit/recommendation/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id, @RequestParam(required = true) Long auditId) {
        ModelAndView modelView = new ModelAndView("/audit/recommendation/upsert");
        try {
            recommendationService.upsert(id, auditId, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelView;
    }

    @RequestMapping(value = "/audit/recommendation/doUpsert", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseMessage doUpsert(@Valid RecommendationDto modelNew, BindingResult result) {

        ResponseMessage response = new ResponseMessage();

        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            if (recommendationService.findByNumber(modelNew, response) == true) {
                return response;
            }

            recommendationService.save(modelNew, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

    @RequestMapping(value = "/audit/recommendation/doObsolete", method = RequestMethod.POST)
    public ResponseMessage doObsolete(@RequestParam(required = true) Long id) {

        ResponseMessage response = new ResponseMessage();
        try {
            recommendationService.doObsolete(id, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

    @RequestMapping(value = "/audit/recommendation/upsertViewDocs", method = RequestMethod.POST)
    public ModelAndView upsertViewDocs(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/audit/recommendation/upsertViewDocs");
        try {
            recommendationService.upsertViewDocs(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsertViewDocs", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/recommendation/doDeleteUpFile", method = RequestMethod.POST)
    public ResponseMessage doDeleteUpFile(@RequestParam(required = true) Long recommendationId, @RequestParam(required = true) Long upfileId) {
        ResponseMessage response = new ResponseMessage();
        try {
            recommendationService.doDeleteUpFile(recommendationId, upfileId, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doDeleteUpFile", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/audit/recommendation/listUfRecommendation", method = RequestMethod.GET)
    public Object listUfComment(@RequestParam(required = true) Long recommendationId) {
        return gridService.toGrid(RecommendationUploadFileView.class, "recommendationId", recommendationId);
    }

    @RequestMapping(value = "/audit/recommendation/attention", method = RequestMethod.POST)
    public ModelAndView attentionRequestAudit(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/audit/recommendation/attention");
        try {
            recommendationService.showAttention(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "attentionRequestAudit", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/recommendation/doAttention", method = RequestMethod.POST)
    public ResponseMessage doAttention(@Valid AttentionDto attentionDto, BindingResult result) {
        ResponseMessage response = new ResponseMessage();
        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            recommendationService.doAttention(attentionDto, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            logException.Write(ex, this.getClass(), "doAttention", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/audit/recommendation/replicate", method = RequestMethod.POST)
    public ModelAndView replicateRequest(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/audit/recommendation/replicate");
        try {
            recommendationService.showReplication(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "attentionRequestAudit", sharedUserService);
        }
        return modelAndView;
    }


    @RequestMapping(value = "/audit/recommendation/doReplication", method = RequestMethod.POST)
    public ResponseMessage doReplication(@Valid AttentionDto attentionDto, BindingResult result) {
        ResponseMessage response = new ResponseMessage();
        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            recommendationService.doReplication(attentionDto, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            logException.Write(ex, this.getClass(), "doAttention", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/audit/recommendation/refresh", method = RequestMethod.POST)
    public ResponseMessage refreshExtensionRecommendation(@RequestParam(required = true) Long id) {
        ResponseMessage responseMessage = new ResponseMessage();
        return recommendationService.refreshExtensionRecommendation(id, responseMessage);
    }

    @RequestMapping(value = "/audit/recommendation/extension", method = RequestMethod.POST)
    public ModelAndView extension(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/audit/recommendation/extension");
        try {
            recommendationService.extension(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "extension", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/recommendation/doDeleteExtension", method = RequestMethod.POST)
    public ResponseMessage doDeleteExtension(@RequestParam(required = true) Long recommendationId, @RequestParam(required = true) Long extensionId) {
        ResponseMessage response = new ResponseMessage();
        try {
            recommendationService.doDeleteExtension(recommendationId, extensionId, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doDeleteExtension", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/audit/recommendation/extension/list", method = RequestMethod.GET)
    public Object recomendationExtensionList(@RequestParam(required = true) Long id) {
        return gridService.toGrid(RecommendationExtensionView.class, "recommendationId", id);
    }

}
