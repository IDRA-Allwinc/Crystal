package com.crystal.service.shared;

import com.crystal.infrastructure.extensions.StringExt;
import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.account.User;
import com.crystal.model.entities.audit.*;
import com.crystal.model.entities.catalog.CatFileType;
import com.crystal.model.shared.Constants;
import com.crystal.model.shared.UploadFileGeneric;
import com.crystal.model.shared.UploadFileRequest;
import com.crystal.repository.account.UserRepository;
import com.crystal.repository.catalog.*;
import com.crystal.repository.shared.UploadFileGenericRepository;
import com.crystal.service.account.SharedUserService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public boolean hasAvailability(UploadFileGeneric file, Long userId, Integer type, ResponseMessage resMsg) {
        if (type == null) {
            file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES), Constants.FILE_PREFIX_USER + userId.toString()).toString());
            return true;
        }

        switch (type) {
            case Constants.UploadFile.EXTENSION_REQUEST:
                file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES), Constants.FILE_PREFIX_EXTENSION+ userId.toString()).toString());
                return true;
            case Constants.UploadFile.EXTENSION_COMMENT:
                file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES), Constants.FILE_PREFIX_EXTENSION+ userId.toString()).toString());
                return true;
            case Constants.UploadFile.EXTENSION_RECOMMENDATION:
                file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES), Constants.FILE_PREFIX_EXTENSION+ userId.toString()).toString());
                return true;
            case Constants.UploadFile.EXTENSION_OBSERVATION:
                file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES), Constants.FILE_PREFIX_EXTENSION+ userId.toString()).toString());
                return true;
            case Constants.UploadFile.EXTENSION_RESPONSIBILITY:
                file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES), Constants.FILE_PREFIX_EXTENSION+ userId.toString()).toString());
                return true;
            case Constants.UploadFile.REQUEST:
                file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES), Constants.FILE_PREFIX_REQUEST + userId.toString()).toString());
                return true;
            case Constants.UploadFile.COMMENT:
                file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES), Constants.FILE_PREFIX_COMMENT + userId.toString()).toString());
                return true;
            case Constants.UploadFile.RECOMMENDATION:
                file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES), Constants.FILE_PREFIX_RECOMMENDATION + userId.toString()).toString());
                return true;
            case Constants.UploadFile.OBSERVATION:
                file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES), Constants.FILE_PREFIX_OBSERVATION + userId.toString()).toString());
                return true;
            case Constants.UploadFile.RESPONSIBILITY:
                file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES), Constants.FILE_PREFIX_RESPONSABILITY + userId.toString()).toString());
                return true;
        }

        file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES), Constants.FILE_PREFIX_NONE + userId.toString()).toString());
        return true;
    }


    @Override
    public void fillUploadFileGeneric(MultipartFile mpf, UploadFileGeneric file, UploadFileRequest uploadRequest, User user) {
        file.setFileName(mpf.getOriginalFilename());
        file.setSize(mpf.getSize());
        file.setObsolete(true); //Se pone obsoleto mientras no esté asociado al oficio
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

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    LetterRepository letterRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    RecommendationRepository recommendationRepository;
    @Autowired
    ObservationRepository observationRepository;
    @Autowired
    ResponsibilityRepository responsibilityRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SharedUserService sharedUserService;

    @Override
    @Transactional
    public void save(UploadFileGeneric uploadFile, UploadFileRequest uploadRequest) {
        Integer type = uploadRequest.getType();
        if (type == null) {
            uploadFileGenericRepository.saveAndFlush(uploadFile);
            return;
        }

        switch (type) {
            case Constants.UploadFile.EXTENSION_REQUEST:
                Request r =  requestRepository.findOne(uploadRequest.getId());
                List<Extension> lstExtension = r.getLstExtension();
                if (lstExtension == null) lstExtension = new ArrayList<>();
                Extension e=new Extension();
                e.setCreateDate(Calendar.getInstance());
                e.setObsolete(false);
                e.setInitial(false);
                e.setComment(uploadRequest.getExtensionComment());
                uploadFile.setObsolete(false);
                e.setUploadFileGeneric(uploadFile);
                e.setInsAudit(sharedUserService.getLoggedUserId());
                try{
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    Calendar endDate = Calendar.getInstance();
                    endDate.setTime(sdf.parse(uploadRequest.getEndDate()));
                    e.setEndDate(endDate);
                    r.setEndDate(endDate);
                }catch (Exception ex){
                    return;
                }

                lstExtension.add(e);
                requestRepository.saveAndFlush(r);
                break;
            case Constants.UploadFile.EXTENSION_COMMENT:
                //crear objeto extension y guardarlo
                uploadFileGenericRepository.saveAndFlush(uploadFile);
                break;
            case Constants.UploadFile.EXTENSION_RECOMMENDATION:
                //crear objeto extension y guardarlo
                uploadFileGenericRepository.saveAndFlush(uploadFile);
                break;
            case Constants.UploadFile.EXTENSION_OBSERVATION:
                //crear objeto extension y guardarlo
                uploadFileGenericRepository.saveAndFlush(uploadFile);
                break;
            case Constants.UploadFile.EXTENSION_RESPONSIBILITY:
                //crear objeto extension y guardarlo
                uploadFileGenericRepository.saveAndFlush(uploadFile);
                break;
            case Constants.UploadFile.REQUEST:
                Request rq = requestRepository.findOne(uploadRequest.getId());
                List<UploadFileGeneric> lstEvidences = rq.getLstEvidences();
                if (lstEvidences == null) lstEvidences = new ArrayList<>();
                uploadFile.setObsolete(false);
                lstEvidences.add(uploadFile);
                requestRepository.saveAndFlush(rq);
                break;
            case Constants.UploadFile.LETTER:
                Letter lt = letterRepository.findOne(uploadRequest.getId());
                List<LetterUploadFileGenericRel> lstFilesRel = lt.getLstFiles();
                if (lstFilesRel == null) lstFilesRel = new ArrayList<>();
                uploadFile.setObsolete(false);
                LetterUploadFileGenericRel letterFileRel = new LetterUploadFileGenericRel();
                letterFileRel.setLetter(lt);
                letterFileRel.setAdditional(true);
                uploadFileGenericRepository.saveAndFlush(uploadFile);
                letterFileRel.setUploadFileGeneric(uploadFile);
                lstFilesRel.add(letterFileRel);
                letterRepository.saveAndFlush(lt);
                break;
            case Constants.UploadFile.COMMENT:
                Comment cm = commentRepository.findOne(uploadRequest.getId());
                List<UploadFileGeneric> lstEvidencesComm = cm.getLstEvidences();
                if (lstEvidencesComm == null) lstEvidencesComm = new ArrayList<>();
                uploadFile.setObsolete(false);
                lstEvidencesComm.add(uploadFile);
                commentRepository.saveAndFlush(cm);
                break;
            case Constants.UploadFile.RECOMMENDATION:
                Recommendation rm = recommendationRepository.findOne(uploadRequest.getId());
                List<UploadFileGeneric> lstEvidencesRecommendation = rm.getLstEvidences();
                if (lstEvidencesRecommendation == null) lstEvidencesRecommendation = new ArrayList<>();
                uploadFile.setObsolete(false);
                lstEvidencesRecommendation.add(uploadFile);
                recommendationRepository.saveAndFlush(rm);
                break;
            case Constants.UploadFile.OBSERVATION:
                Observation o = observationRepository.findOne(uploadRequest.getId());
                List<UploadFileGeneric> lstEvidencesObservation = o.getLstEvidences();
                if (lstEvidencesObservation == null) lstEvidencesObservation = new ArrayList<>();
                uploadFile.setObsolete(false);
                lstEvidencesObservation.add(uploadFile);
                observationRepository.saveAndFlush(o);
                break;
            case Constants.UploadFile.RESPONSIBILITY:
                Responsibility rp = responsibilityRepository.findOne(uploadRequest.getId());
                List<UploadFileGeneric> lstEvidencesResponsibility = rp.getLstEvidences();
                if (lstEvidencesResponsibility == null) lstEvidencesResponsibility = new ArrayList<>();
                uploadFile.setObsolete(false);
                lstEvidencesResponsibility.add(uploadFile);
                responsibilityRepository.saveAndFlush(rp);
                break;
            case Constants.UploadFile.EVENT:
                Event ev = eventRepository.findOne(uploadRequest.getId());
                List<UploadFileGeneric> lstEvidencesEvent = ev.getLstFiles();
                if (lstEvidencesEvent == null) lstEvidencesEvent = new ArrayList<>();
                uploadFile.setObsolete(false);
                lstEvidencesEvent.add(uploadFile);
                eventRepository.saveAndFlush(ev);
                break;
        }

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

    @Override
    public boolean validTypeAndFields(UploadFileRequest uploadRequest, ResponseMessage resMsg) {
        Integer type = uploadRequest.getType();

        if (type == null)
            return true;

        switch (type) {
            case Constants.UploadFile.EXTENSION_REQUEST: {

                break;
            }
            case Constants.UploadFile.REQUEST: {
                Long requestId = uploadRequest.getId();
                if (requestId == null) {
                    resMsg.setMessage("El archivo no está asociado a un requerimiento");
                    resMsg.setHasError(true);
                    return false;
                }

                Boolean isAttended = requestRepository.isAttendedById(requestId);

                if (isAttended == null) {
                    resMsg.setMessage("El requerimiento fue eliminado o no existe");
                    resMsg.setHasError(true);
                    return false;
                }

                if (isAttended == true) {
                    resMsg.setMessage("No es posible agregar un archivo debido a que el requerimiento ya fue atendido");
                    resMsg.setHasError(true);
                    return false;
                }

                if (StringExt.isNullOrWhiteSpace(uploadRequest.getDescription())) {
                    resMsg.setMessage("Descripción es un campo requerido");
                    resMsg.setHasError(true);
                    return false;
                }
                return true;
            }
            case Constants.UploadFile.LETTER: {
                Long requestId = uploadRequest.getId();
                if (requestId == null) {
                    resMsg.setMessage("El archivo no está asociado a un oficio");
                    resMsg.setHasError(true);
                    return false;
                }

                Boolean isAttended = letterRepository.isAttendedById(requestId);

                if (isAttended == null) {
                    resMsg.setMessage("El oficio fue eliminado o no existe");
                    resMsg.setHasError(true);
                    return false;
                }

                if (isAttended == true) {
                    resMsg.setMessage("No es posible agregar un archivo debido a que el oficio ya fue atendido");
                    resMsg.setHasError(true);
                    return false;
                }

                if (StringExt.isNullOrWhiteSpace(uploadRequest.getDescription())) {
                    resMsg.setMessage("Descripción es un campo requerido");
                    resMsg.setHasError(true);
                    return false;
                }
                return true;
            }
            case Constants.UploadFile.COMMENT: {
                Long commentId = uploadRequest.getId();
                if (commentId == null) {
                    resMsg.setMessage("El archivo no está asociado a una observaci&oacute;n");
                    resMsg.setHasError(true);
                    return false;
                }

                Boolean isAttended = commentRepository.isAttendedById(commentId);

                if (isAttended == null) {
                    resMsg.setMessage("La observaci&oacute;n fue eliminada o no existe");
                    resMsg.setHasError(true);
                    return false;
                }

                if (isAttended == true) {
                    resMsg.setMessage("No es posible agregar un archivo debido a que la observaci&oacute;n ya fue atendida");
                    resMsg.setHasError(true);
                    return false;
                }

                if (StringExt.isNullOrWhiteSpace(uploadRequest.getDescription())) {
                    resMsg.setMessage("Descripción es un campo requerido");
                    resMsg.setHasError(true);
                    return false;
                }
                return true;
            }
            case Constants.UploadFile.RECOMMENDATION: {
                Long recommendationId = uploadRequest.getId();
                if (recommendationId == null) {
                    resMsg.setMessage("El archivo no está asociado a una recomendaci&oacute;n");
                    resMsg.setHasError(true);
                    return false;
                }

                Boolean isAttended = recommendationRepository.isAttendedById(recommendationId);

                if (isAttended == null) {
                    resMsg.setMessage("La recomendaci&oacute;n fue eliminada o no existe");
                    resMsg.setHasError(true);
                    return false;
                }

                if (isAttended == true) {
                    resMsg.setMessage("No es posible agregar un archivo debido a que la recomendaci&oacute;n ya fue atendida");
                    resMsg.setHasError(true);
                    return false;
                }

                if (StringExt.isNullOrWhiteSpace(uploadRequest.getDescription())) {
                    resMsg.setMessage("Descripción es un campo requerido");
                    resMsg.setHasError(true);
                    return false;
                }
                return true;
            }
            case Constants.UploadFile.OBSERVATION: {
                Long observationId = uploadRequest.getId();
                if (observationId == null) {
                    resMsg.setMessage("El archivo no está asociado a un pliego de observaciones");
                    resMsg.setHasError(true);
                    return false;
                }

                Boolean isAttended = observationRepository.isAttendedById(observationId);

                if (isAttended == null) {
                    resMsg.setMessage("El pliego de observaciones fue eliminada o no existe");
                    resMsg.setHasError(true);
                    return false;
                }

                if (isAttended == true) {
                    resMsg.setMessage("No es posible agregar un archivo debido a que el pliego de observaciones ya fue atendido");
                    resMsg.setHasError(true);
                    return false;
                }

                if (StringExt.isNullOrWhiteSpace(uploadRequest.getDescription())) {
                    resMsg.setMessage("Descripción es un campo requerido");
                    resMsg.setHasError(true);
                    return false;
                }
                return true;
            }
            case Constants.UploadFile.RESPONSIBILITY: {
                Long responsibilityId = uploadRequest.getId();
                if (responsibilityId == null) {
                    resMsg.setMessage("El archivo no está asociado a una promoci&oacute;n");
                    resMsg.setHasError(true);
                    return false;
                }

                Boolean isAttended = responsibilityRepository.isAttendedById(responsibilityId);

                if (isAttended == null) {
                    resMsg.setMessage("La promoci&oacute;n fue eliminada o no existe");
                    resMsg.setHasError(true);
                    return false;
                }

                if (isAttended == true) {
                    resMsg.setMessage("No es posible agregar un archivo debido a que la promoci&oacute;n ya fue atendida");
                    resMsg.setHasError(true);
                    return false;
                }

                if (StringExt.isNullOrWhiteSpace(uploadRequest.getDescription())) {
                    resMsg.setMessage("Descripción es un campo requerido");
                    resMsg.setHasError(true);
                    return false;
                }
                return true;
            }
            case Constants.UploadFile.EVENT: {
                Long eventId = uploadRequest.getId();
                if (eventId == null) {
                    resMsg.setMessage("El archivo no está asociado a un evento");
                    resMsg.setHasError(true);
                    return false;
                }

                if (StringExt.isNullOrWhiteSpace(uploadRequest.getDescription())) {
                    resMsg.setMessage("Descripción es un campo requerido");
                    resMsg.setHasError(true);
                    return false;
                }
                return true;
            }
        }
        return true;
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
