package com.crystal.service.shared;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.account.User;
import com.crystal.model.entities.catalog.CatFileType;
import com.crystal.model.shared.Constants;
import com.crystal.model.shared.UploadFileGeneric;
import com.crystal.model.shared.UploadFileRequest;
import com.crystal.repository.catalog.CatFileTypeRepository;
import com.crystal.repository.shared.UploadFileGenericRepository;
import com.crystal.service.account.SharedUserService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.UUID;

@Service
public class UpDwFileGenericServiceImpl implements UpDwFileGenericService {

    @Autowired
    UploadFileGenericRepository uploadFileGenericRepository;

    @Override
    public boolean isValidRequestFile(Iterator<String> itr, ResponseMessage resMsg) {
        if (itr.hasNext() == false) {
            resMsg.setHasError(true);
            resMsg.setMessage("No existe un archivo en la solicitud, por favor revise e intente de nuevo");
            return false;
        }
        return true;
    }


    @Override
    public boolean hasAvailability(UploadFileGeneric file, ResponseMessage resMsg, Long userId) {
        file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES),
                Constants.FILE_PREFIX_USER + userId.toString()).toString());
        return true;
    }


    @Override
    public void fillUploadFileGeneric(MultipartFile mpf, UploadFileGeneric file, UploadFileRequest uploadRequest, User user) {
        file.setFileName(mpf.getOriginalFilename());
        file.setSize(mpf.getSize());
        file.setObsolete(true); //Se pone obsoleto mientras no esté asociado al reporte
        file.setDescription(uploadRequest.getDescription());
        file.setCreationTime(Calendar.getInstance());
        file.setCreationUser(user);
        //Crear y guardar el nombre del archivo real
        file.setRealFileName(UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(mpf.getOriginalFilename()));
    }


    @Autowired
    CatFileTypeRepository catFileTypeRepository;

    @Override
    public boolean isValidExtension(MultipartFile mpf, UploadFileGeneric file, ResponseMessage resMsg) {
        String filename = mpf.getOriginalFilename();
        String extension = FilenameUtils.getExtension(filename);

        if (extension == null || extension.isEmpty()) {
            resMsg.setMessage("El archivo no tiene extensión y no puede ser almacendo");
            resMsg.setHasError(true);
            return false;
        }

        final Long fileTypeId = catFileTypeRepository.countByFileTypeContainingIgnoreCase(extension);

        if (fileTypeId == null || fileTypeId.longValue() <= 0) {
            resMsg.setMessage("Tipo de archivo no permitido");
            resMsg.setHasError(true);
            return false;
        }

        file.setFileType(new CatFileType() {{
            setId(fileTypeId);
        }});
        return true;
    }

    /*
    @Override
    public boolean isValidExtensionByCode(MultipartFile mpf, UploadFileGeneric file, ResponseMessage resMsg, String code) {
        String filename = mpf.getOriginalFilename();
        String extension = FilenameUtils.getExtension(filename);

        if (extension == null || extension.isEmpty()) {
            resMsg.setMessage("El archivo no tiene extensión y no puede ser almacendo");
            resMsg.setHasError(true);
            return false;
        }

        extension = extension.toLowerCase();
        final Long fileTypeId = catFileTypeRepository.findByExtensionCode(extension, code);

        if (fileTypeId == null || fileTypeId.longValue() <= 0) {
            resMsg.setMessage("Tipo de archivo no permitido");
            resMsg.setHasError(true);
            return false;
        }

        file.setFileType(new CatFileType() {{
            setId(fileTypeId);
        }});
        return true;
    } */

    @Override
    public boolean saveOnDiskUploadFile(MultipartFile mpf, String path, UploadFileGeneric uploadFile, ResponseMessage resMsg,
                                        SharedLogExceptionService logException, SharedUserService sharedUserService) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                if (file.mkdirs() == false) {
                    resMsg.setHasError(true);
                    resMsg.setMessage("Se presentó un problema con la ruta del archivo, por favor intente de nuevo o contacte a soporte técnico");
                    return false;
                }
            }

            File fileReal = new File(file, uploadFile.getRealFileName());
            FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(fileReal));

        } catch (Exception ex) {
            logException.Write(ex, this.getClass(), "saveOnDiskUploadFile", sharedUserService);
            resMsg.setHasError(true);
            resMsg.setMessage("Se presentó un problema al momento de guardar el archivo, por favor intente de nuevo o contacte a soporte técnico");
            return false;
        }

        return true;
    }

    @Override
    public void save(UploadFileGeneric uploadFile) {
        uploadFileGenericRepository.save(uploadFile);
        uploadFileGenericRepository.flush();
    }

    @Override
    public UploadFileGeneric getPathAndFilename(Long id) {
        UploadFileGeneric file = uploadFileGenericRepository.getPathAndFilename(id);
        return file;
    }

    @Override
    public File getFileToDownload(Long fileId, HttpServletRequest request, HttpServletResponse response) {
        UploadFileGeneric file = getPathAndFilename(fileId);
        String path = new File(file.getPath(), file.getRealFileName()).toString();
        File finalFile = new File(request.getSession().getServletContext().getRealPath(""), path);

        response.setContentType("application/force-download");
        response.setContentLength((int) finalFile.length());
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"");

        return finalFile;
    }
    /*
    @Override
    public List<UploadFileGeneric> getUploadFilesByUserId(Long id) {
        return uploadFileGenericRepository.getUploadFilesByUserId(id);
    }

    @Override
    public UploadFileGeneric findOne(Long fileId) {
        return uploadFileGenericRepository.findOne(fileId);
    }
     */

}
