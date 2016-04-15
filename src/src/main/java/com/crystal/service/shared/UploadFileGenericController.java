package com.crystal.service.shared;


import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.account.User;
import com.crystal.model.shared.Constants;
import com.crystal.model.shared.UploadFileGeneric;
import com.crystal.model.shared.UploadFileGenericDto;
import com.crystal.model.shared.UploadFileRequest;
import com.crystal.service.account.SharedUserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

@Controller
public class UploadFileGenericController {

    @Autowired
    SharedLogExceptionService logException;

    @Autowired
    SharedUserService sharedUserService;

    @Autowired
    private SharedUserService userService;

    @Autowired
    UpDwFileGenericService upDwFileGenericService;

    @RequestMapping(value = "/shared/uploadFileGeneric/doUploadFileGeneric", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseMessage doUploadFileGeneric(@ModelAttribute UploadFileRequest uploadRequest,
                                        MultipartHttpServletRequest request) {

        ResponseMessage resMsg = new ResponseMessage();
        try {

            Long userId = sharedUserService.getLoggedUserId();
            if (!userService.isUserInRoles(userId, new ArrayList<String>() {{
                add(Constants.ROLE_ADMIN);
                add(Constants.ROLE_DGPOP);
                add(Constants.ROLE_LINK);
                add(Constants.ROLE_MANAGER);
            }})) {
                resMsg.setHasError(true);
                resMsg.setMessage("Usted no tiene permisos para realizar esta acción.");
                return resMsg;
            }

            Iterator<String> itr = request.getFileNames();

            if(!upDwFileGenericService.validTypeAndFields(uploadRequest, resMsg))
                return resMsg;

            if (!upDwFileGenericService.isValidRequestFile(itr, resMsg)) {
                return resMsg;
            }

            UploadFileGeneric uFile = new UploadFileGeneric();

            MultipartFile mpf = request.getFile(itr.next());
            if (!upDwFileGenericService.isValidExtension(mpf, uFile, resMsg))
                return resMsg;

            User user = new User();
            user.setId(userId);
            upDwFileGenericService.fillUploadFileGeneric(mpf, uFile, uploadRequest, user);

            if (!upDwFileGenericService.hasAvailability(uFile, userId, uploadRequest.getType(), resMsg))
                return resMsg;

            String path = request.getSession().getServletContext().getRealPath("");
            path = new File(path, uFile.getPath()).toString();

            //SI SE TRATA DE UN ARCHIVO ASOCIADO A UNA PRORROGA SE VALIDA
            if(upDwFileGenericService.validateExtensions(resMsg,uploadRequest)==true)
                return resMsg;


            if (!upDwFileGenericService.saveOnDiskUploadFile(mpf, path, uFile, resMsg, logException, sharedUserService))
                return resMsg;

            upDwFileGenericService.save(uFile, uploadRequest);

            resMsg.setMessage("El archivo " + uFile.getFileName() + " fue subido de forma correcta. Por favor presione el botón guardar para finalizar el proceso.");
            resMsg.setHasError(false);
            if (uploadRequest.getCloseUploadFile() != null && uploadRequest.getCloseUploadFile()) {

                resMsg.setUrlToGo("close");
                resMsg.setReturnData(uFile.getPath() + "/" + uFile.getRealFileName());
            } else {
                Gson gson = new Gson();
                UploadFileGenericDto dto = new UploadFileGenericDto(uFile.getId(), uFile.getFileName());
                resMsg.setReturnData(gson.toJson(dto));
            }
        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "doUploadFileGeneric", sharedUserService);
            resMsg.setHasError(true);
            resMsg.setMessage("Se present&oacute; un error inesperado. Por favor revise la informaci&oacute;n e intente de nuevo");
        }

        return resMsg;
    }

    @RequestMapping(value = "/shared/uploadFileGeneric/downloadFile", method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource getFile(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response) {
        UploadFileGeneric file = upDwFileGenericService.getPathAndFilename(id);
        String path = new File(file.getPath(), file.getRealFileName()).toString();
        File finalFile = new File(request.getSession().getServletContext().getRealPath(""), path);

        response.setContentType("application/force-download");
        response.setContentLength((int) finalFile.length());
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"");

        finalFile.deleteOnExit();
        return new FileSystemResource(finalFile);
    }
}

