package com.crystal.controller.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.ObservationDto;
import com.crystal.model.entities.audit.view.ObservationExtensionView;
import com.crystal.model.entities.audit.view.ObservationUploadFileView;
import com.crystal.model.entities.audit.view.ObservationView;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.audit.ObservationService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import com.crystal.service.shared.UpDwFileGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class ObservationController {

    @Autowired
    GridService gridService;
    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    ObservationService observationService;
    @Autowired
    UpDwFileGenericService upDwFileGenericService;

    @RequestMapping(value = "/audit/observation/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/audit/observation/index");
        return modelAndView;
    }

    @RequestMapping(value = "/audit/observation/list", method = RequestMethod.GET)
    public Object observationList(@RequestParam(required = true) Long id) {
        return gridService.toGrid(ObservationView.class, "auditId", id);
    }

    @RequestMapping(value = "/audit/observation/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id, @RequestParam(required = true) Long auditId) {
        ModelAndView modelView = new ModelAndView("/audit/observation/upsert");
        try {
            observationService.upsert(id, auditId, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelView;
    }

    @RequestMapping(value = "/audit/observation/doUpsert", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseMessage doUpsert(@Valid ObservationDto modelNew, BindingResult result) {

        ResponseMessage response = new ResponseMessage();

        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            if (observationService.findByNumber(modelNew, response) == true) {
                return response;
            }


            observationService.save(modelNew, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

    @RequestMapping(value = "/audit/observation/doObsolete", method = RequestMethod.POST)
    public ResponseMessage doObsolete(@RequestParam(required = true) Long id) {

        ResponseMessage response = new ResponseMessage();

        try {
            observationService.doObsolete(id, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

    @RequestMapping(value = "/audit/observation/upsertViewDocs", method = RequestMethod.POST)
    public ModelAndView upsertViewDocs(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/audit/observation/upsertViewDocs");
        try {
            observationService.upsertViewDocs(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsertViewDocs", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/observation/doDeleteUpFile", method = RequestMethod.POST)
    public ResponseMessage doDeleteUpFile(@RequestParam(required = true) Long observationId, @RequestParam(required = true) Long upfileId) {
        ResponseMessage response = new ResponseMessage();
        try {
            observationService.doDeleteUpFile(observationId, upfileId, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doDeleteUpFile", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/audit/observation/listUfObservation", method = RequestMethod.GET)
    public Object listUfObservation(@RequestParam(required = true) Long observationId) {
        return gridService.toGrid(ObservationUploadFileView.class, "observationId", observationId);
    }

    @RequestMapping(value = "/audit/observation/attention", method = RequestMethod.POST)
    public ModelAndView attentionRequestAudit(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/audit/observation/attention");
        try {
            observationService.showAttention(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "attentionRequestAudit", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/observation/doAttention", method = RequestMethod.POST)
    public ResponseMessage doAttention(@Valid AttentionDto attentionDto, BindingResult result) {
        ResponseMessage response = new ResponseMessage();
        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            observationService.doAttention(attentionDto, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            logException.Write(ex, this.getClass(), "doAttention", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/audit/observation/extension", method = RequestMethod.POST)
    public ModelAndView extension(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/audit/observation/extension");
        try {
            observationService.extension(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "extension", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/observation/doDeleteExtension", method = RequestMethod.POST)
    public ResponseMessage doDeleteExtension(@RequestParam(required = true) Long observationId, @RequestParam(required = true) Long extensionId) {
        ResponseMessage response = new ResponseMessage();
        try {
            observationService.doDeleteExtension(observationId, extensionId, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doDeleteExtension", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/audit/observation/extension/list", method = RequestMethod.GET)
    public Object observationExtensionList(@RequestParam(required = true) Long id) {
        return gridService.toGrid(ObservationExtensionView.class, "observationId", id);
    }

    @RequestMapping(value = "/audit/observation/replicate", method = RequestMethod.POST)
    public ModelAndView replicateRequest(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/audit/observation/replicate");
        try {
            observationService.showReplication(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "attentionRequestAudit", sharedUserService);
        }
        return modelAndView;
    }


    @RequestMapping(value = "/audit/observation/doReplication", method = RequestMethod.POST)
    public ResponseMessage doReplication(@Valid ObservationDto observationDto, @Valid AttentionDto attentionDto, BindingResult result) {
        ResponseMessage response = new ResponseMessage();
        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            observationService.doReplication(observationDto, attentionDto, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            logException.Write(ex, this.getClass(), "doAttention", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/audit/observation/refresh", method = RequestMethod.POST)
    public ResponseMessage refreshExtensionRecommendation(@RequestParam(required = true) Long id) {
        ResponseMessage responseMessage = new ResponseMessage();
        return observationService.refreshExtensionObservation(id, responseMessage);
    }

}