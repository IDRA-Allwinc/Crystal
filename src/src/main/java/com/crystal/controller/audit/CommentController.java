package com.crystal.controller.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.CommentDto;
import com.crystal.model.entities.audit.view.CommentView;
import com.crystal.model.entities.audit.view.LetterUploadFileView;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.audit.CommentService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import com.crystal.service.shared.UpDwFileGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
public class CommentController {

    @Autowired
    GridService gridService;
    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    CommentService commentService;
    @Autowired
    UpDwFileGenericService upDwFileGenericService;

    @RequestMapping(value = "/audit/comment/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/audit/comment/index");
        return modelAndView;
    }

    @RequestMapping(value = "/audit/comment/list", method = RequestMethod.GET)
    public Object commentList(@RequestParam(required = true) Long id) {
        return gridService.toGrid(CommentView.class, "auditId", id);
    }

    @RequestMapping(value = "/audit/comment/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id, @RequestParam(required = true) Long auditId) {
        ModelAndView modelView = new ModelAndView("/audit/comment/upsert");
        try {
            commentService.upsert(id, auditId, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelView;
    }

    @RequestMapping(value = "/audit/comment/doUpsert", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseMessage doUpsert(@Valid CommentDto modelNew, BindingResult result) {

        ResponseMessage response = new ResponseMessage();

        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;
            commentService.save(modelNew, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

    @RequestMapping(value = "/audit/comment/doObsolete", method = RequestMethod.POST)
    public ResponseMessage doObsolete(@RequestParam(required = true) Long id) {

        ResponseMessage response = new ResponseMessage();

        try {
//            Long userId = sharedUserService.getLoggedUserId();
//            serviceLetter.doObsolete(id, userId, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise l    a información e intente de nuevo");
        }

        return response;
    }

    @RequestMapping(value = "/audit/comment/upsertViewDocs", method = RequestMethod.POST)
    public ModelAndView upsertViewDocs(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/audit/letter/upsertViewDocs");
        try {
//            serviceLetter.upsertViewDocs(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsertViewDocs", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/comment/listUfLetter", method = RequestMethod.GET)
    public Object listUfComment(@RequestParam(required = true) Long letterId) {
        HashMap<String, Object> filters = new HashMap();
        filters.put("letterId", letterId);
        filters.put("isAdditional", true);
        return gridService.toGrid(LetterUploadFileView.class, filters);
    }

    @RequestMapping(value = "/audit/comment/attention", method = RequestMethod.POST)
    public ModelAndView attentionRequestAudit(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/audit/letter/attention");
        try {
//            serviceLetter.showAttention(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "attentionRequestAudit", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/comment/doAttention", method = RequestMethod.POST)
    public ResponseMessage doAttention(@Valid AttentionDto attentionDto, BindingResult result) {
        ResponseMessage response = new ResponseMessage();
        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

//            serviceLetter.doAttention(attentionDto, response);

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