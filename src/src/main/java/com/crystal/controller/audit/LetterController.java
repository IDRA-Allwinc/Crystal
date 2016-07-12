package com.crystal.controller.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.audit.LetterDto;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.view.LetterAuditView;
import com.crystal.model.entities.audit.view.LetterUploadFileView;
import com.crystal.model.entities.audit.view.LetterView;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.audit.LetterService;
import com.crystal.service.shared.GridService;
import com.crystal.service.shared.SharedLogExceptionService;
import com.crystal.service.shared.UpDwFileGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

@RestController
public class LetterController {

    @Autowired
    GridService gridService;
    @Autowired
    SharedLogExceptionService logException;
    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    LetterService serviceLetter;
    @Autowired
    UpDwFileGenericService upDwFileGenericService;

    /* requerimientos previos*/
    @RequestMapping(value = "/previousRequest/letter/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/previousRequest/letter/index");
        return modelAndView;
    }

    @RequestMapping(value = "/previousRequest/letter/list", method = RequestMethod.GET)
    public Object letterList() {
        HashMap<String, Object> filters = new HashMap<>();
        Long roleId = sharedUserService.getRoleIdForUser();
        return gridService.toGrid(LetterView.class, "roleId", roleId);
    }

    @RequestMapping(value = "/previousRequest/letter/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id) {
        ModelAndView modelView = new ModelAndView("/previousRequest/letter/upsert");
        try {
            serviceLetter.upsert(id, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelView;
    }

    @RequestMapping(value = "/previousRequest/letter/doUpsert", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseMessage doUpsert(@Valid LetterDto modelNew, BindingResult result) {

        ResponseMessage response = new ResponseMessage();

        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;
            Long userId = sharedUserService.getLoggedUserId();
            serviceLetter.save(modelNew, response, userId, sharedUserService.getRoleIdForUserId(userId));
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

    @RequestMapping(value = "/previousRequest/letter/doObsolete", method = RequestMethod.POST)
    public ResponseMessage doObsolete(@RequestParam(required = true) Long id) {

        ResponseMessage response = new ResponseMessage();

        try {
            Long userId = sharedUserService.getLoggedUserId();
            serviceLetter.doObsolete(id, userId, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }
    /* requerimientos previos */

    /*oficios asociados a auditoria*/

    @RequestMapping(value = "/audit/letter/index", method = RequestMethod.GET)
    public ModelAndView letterAuditIndex() {
        ModelAndView modelAndView = new ModelAndView("/audit/letter/index");
        return modelAndView;
    }

    @RequestMapping(value = "/audit/letter/list", method = RequestMethod.GET)
    public Object letterAuditList(@RequestParam(required = true) Long id) {
        Long roleId = sharedUserService.getRoleIdForUser();
        HashMap<String, Object> filters = new HashMap<>();
        filters.put("roleId", roleId);
        filters.put("auditId", id);
        return gridService.toGrid(LetterAuditView.class, filters);
    }

    @RequestMapping(value = "/audit/letter/upsert", method = RequestMethod.POST)
    public ModelAndView letterAuditUpsert(@RequestParam(required = true) Long auditId, @RequestParam(required = false) Long id) {
        ModelAndView modelView = new ModelAndView("/audit/letter/upsert");
        try {
            serviceLetter.upsertAudit(auditId, id, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelView;
    }

    @RequestMapping(value = "/audit/letter/doUpsert", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseMessage letterAuditDoUpsert(@Valid LetterDto modelNew, BindingResult result) {

        ResponseMessage response = new ResponseMessage();

        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;
            Long userId = sharedUserService.getLoggedUserId();
            serviceLetter.save(modelNew, response, userId, sharedUserService.getRoleIdForUserId(userId));
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

    @RequestMapping(value = "/audit/letter/doObsolete", method = RequestMethod.POST)
    public ResponseMessage letterAuditDoObsolete(@RequestParam(required = true) Long id) {

        ResponseMessage response = new ResponseMessage();

        try {
            Long userId = sharedUserService.getLoggedUserId();
            serviceLetter.doObsolete(id, userId, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

    /*oficios asociados a auditoria*/

    @RequestMapping(value = {"/previousRequest/letter/downloadFile", "/audit/letter/downloadFile"}, method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getFile(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response) {
        try {
            Long fileId = serviceLetter.findFileIdByLetterId(id);
            File finalFile = upDwFileGenericService.getFileToDownload(fileId, request, response);
            finalFile.deleteOnExit();
            return new FileSystemResource(finalFile);
        } catch (Exception e) {
            logException.Write(e, this.getClass(), "downloadFileByCase", sharedUserService);
            try {
                File file = upDwFileGenericService.createDownloadableFile("errorDescarga", ".doc", request);
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write("<html><body><h3>Ocurrió un error al momento de descargar el documento. Por favor intente de nuevo o contacte a soporte técnico.</h3></body></html>");
                writer.flush();
                writer.close();
                file.deleteOnExit();
                return new FileSystemResource(file);
            } catch (IOException ex) {
                logException.Write(ex, this.getClass(), "getFile", sharedUserService);
                return null;
            }
        }
    }


    @RequestMapping(value = "/audit/letter/upsertViewDocs", method = RequestMethod.POST)
    public ModelAndView upsertViewDocs(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/audit/letter/upsertViewDocs");
        try {
            serviceLetter.upsertViewDocs(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsertViewDocs", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/letter/listUfLetter", method = RequestMethod.GET)
    public Object listUfLetter(@RequestParam(required = true) Long letterId) {
        HashMap<String, Object> filters = new HashMap();
        filters.put("letterId", letterId);
        filters.put("isAdditional", true);
        return gridService.toGrid(LetterUploadFileView.class, filters);
    }

    @RequestMapping(value = "/audit/letter/doDeleteUpFile", method = RequestMethod.POST)
    public ResponseMessage doDeleteUpFile(@RequestParam(required = true) Long letterId, @RequestParam(required = true) Long upfileId) {
        ResponseMessage response = new ResponseMessage();
        try {
            serviceLetter.doDeleteUpFile(letterId, upfileId, response);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doDeleteUpFile", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo.");
        } finally {
            return response;
        }
    }

    @RequestMapping(value = "/audit/letter/attention", method = RequestMethod.POST)
    public ModelAndView attentionRequestAudit(@RequestParam(required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/audit/letter/attention");
        try {
            serviceLetter.showAttention(id, modelAndView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "attentionRequestAudit", sharedUserService);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/audit/letter/doAttention", method = RequestMethod.POST)
    public ResponseMessage doAttention(@Valid AttentionDto attentionDto, BindingResult result) {
        ResponseMessage response = new ResponseMessage();
        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;

            serviceLetter.doAttention(attentionDto, response);

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