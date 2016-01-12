package com.crystal.service.shared;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.account.User;
import com.crystal.model.shared.UploadFileGeneric;
import com.crystal.model.shared.UploadFileRequest;
import com.crystal.service.account.SharedUserService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Iterator;

public interface UpDwFileGenericService {
    boolean isValidRequestFile(Iterator<String> itr, ResponseMessage resMsg);
    boolean isValidExtension(MultipartFile mpf, UploadFileGeneric file, ResponseMessage resMsg);
    void fillUploadFileGeneric(MultipartFile mpf, UploadFileGeneric uploadFile, UploadFileRequest uploadRequest, User user);
    boolean hasAvailability(UploadFileGeneric itr, ResponseMessage resMsg, Long userId);
    boolean saveOnDiskUploadFile(MultipartFile mpf, String path, UploadFileGeneric uploadFile, ResponseMessage resMsg, SharedLogExceptionService logException, SharedUserService sharedUserService);
    void save(UploadFileGeneric uploadFile);
    UploadFileGeneric getPathAndFilename(Long id);
    File getFileToDownload(Long fileId, HttpServletRequest request, HttpServletResponse response);

 /*
    boolean isValidExtensionByCode(MultipartFile mpf, UploadFileGeneric file, ResponseMessage resMsg, String code);
    UploadFileGeneric getValidUploadFileGenericByIdAndUserId(Long userId, Long uploadFileGenericId);
    void deleteFile(String path, UploadFileGeneric uploadFile, User user);
    List<UploadFileGeneric> getUploadFilesByUserId(Long id);
    UploadFileGeneric findOne(Long fileId);
  */
}

