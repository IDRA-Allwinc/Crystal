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
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;

public interface UpDwFileGenericService {
    boolean isValidRequestFile(Iterator<String> itr, ResponseMessage resMsg);
    boolean isValidExtension(MultipartFile mpf, UploadFileGeneric file, ResponseMessage resMsg);
    void fillUploadFileGeneric(MultipartFile mpf, UploadFileGeneric uploadFile, UploadFileRequest uploadRequest, User user);
    boolean hasAvailability(UploadFileGeneric file, Long userId, Integer type, ResponseMessage resMsg);
    boolean saveOnDiskUploadFile(MultipartFile mpf, String path, UploadFileGeneric uploadFile, ResponseMessage resMsg, SharedLogExceptionService logException, SharedUserService sharedUserService);
    void save(UploadFileGeneric uploadFile, UploadFileRequest uploadRequest) throws ParseException;
    UploadFileGeneric getPathAndFilename(Long id);
    File getFileToDownload(Long fileId, HttpServletRequest request, HttpServletResponse response) throws IOException;
    boolean validTypeAndFields(UploadFileRequest uploadRequest, ResponseMessage resMsg);
    boolean validateExtensions(ResponseMessage responseMessage, UploadFileRequest uploadRequest) throws ParseException;
    public File createDownloadableFileFromUploadedFile(String completeFileName, HttpServletRequest request);
    public File createDownloadableFile(String fileName, String extension, HttpServletRequest request);

 /*
    boolean isValidExtensionByCode(MultipartFile mpf, UploadFileGeneric file, ResponseMessage resMsg, String code);
    UploadFileGeneric getValidUploadFileGenericByIdAndUserId(Long userId, Long uploadFileGenericId);
    void deleteFile(String path, UploadFileGeneric uploadFile, User user);
    List<UploadFileGeneric> getUploadFilesByUserId(Long id);
    UploadFileGeneric findOne(Long fileId);
  */
}

