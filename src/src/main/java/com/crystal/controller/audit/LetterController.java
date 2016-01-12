package com.crystal.controller.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.infrastructure.validation.DtoValidator;
import com.crystal.model.entities.audit.LetterDto;
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
import java.io.File;

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

    @RequestMapping(value = "/audit/letter/index", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/audit/letter/index");
        return modelAndView;
    }

    @RequestMapping(value = "/audit/letter/list", method = RequestMethod.GET)
    public Object letterList() {
        return gridService.toGrid(LetterView.class);
    }

    @RequestMapping(value = "/audit/letter/upsert", method = RequestMethod.POST)
    public ModelAndView upsert(@RequestParam(required = false) Long id) {
        ModelAndView modelView = new ModelAndView("/audit/letter/upsert");
        try {
            serviceLetter.upsert(id, modelView);
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "upsert", sharedUserService);
        }
        return modelView;
    }

    @RequestMapping(value = "/audit/letter/doUpsert", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseMessage doUpsertActivity(@Valid LetterDto modelNew, BindingResult result) {

        ResponseMessage response = new ResponseMessage();

        try {
            if (DtoValidator.isValid(result, response) == false)
                return response;
            serviceLetter.save(modelNew, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUpsert", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }

    @RequestMapping(value = "/audit/letter/doObsolete", method = RequestMethod.POST)
    public ResponseMessage doObsolete(@RequestParam(required = true) Long id) {

        ResponseMessage response = new ResponseMessage();

        try {
            serviceLetter.doObsolete(id, response);
            return response;
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doObsolete", sharedUserService);
            response.setHasError(true);
            response.setMessage("Se presentó un error inesperado. Por favor revise la información e intente de nuevo");
        }

        return response;
    }


    @RequestMapping(value = "/audit/letter/downloadFile", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getFile(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response) {

        Long fileId = serviceLetter.findFileIdByLetterId(id);
        File finalFile = upDwFileGenericService.getFileToDownload(fileId, request, response);
        finalFile.deleteOnExit();
        return new FileSystemResource(finalFile);
    }
}