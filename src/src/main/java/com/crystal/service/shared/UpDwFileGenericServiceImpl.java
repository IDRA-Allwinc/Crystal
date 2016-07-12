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
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
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
                file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES), Constants.FILE_PREFIX_EXTENSION + userId.toString()).toString());
                return true;
            case Constants.UploadFile.EXTENSION_COMMENT:
                file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES), Constants.FILE_PREFIX_EXTENSION + userId.toString()).toString());
                return true;
            case Constants.UploadFile.EXTENSION_RECOMMENDATION:
                file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES), Constants.FILE_PREFIX_EXTENSION + userId.toString()).toString());
                return true;
            case Constants.UploadFile.EXTENSION_OBSERVATION:
                file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES), Constants.FILE_PREFIX_EXTENSION + userId.toString()).toString());
                return true;
            case Constants.UploadFile.EXTENSION_RESPONSIBILITY:
                file.setPath(new File(Constants.SystemSettings.Map.get(Constants.SystemSettings.PATH_TO_SAVE_UPLOAD_FILES), Constants.FILE_PREFIX_EXTENSION + userId.toString()).toString());
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
    @Autowired
    LetterUploadFileGenericRelRepository letterUploadFileGenericRelRepository;

    @Override
    @Transactional
    public void save(UploadFileGeneric uploadFile, UploadFileRequest uploadRequest) throws ParseException {
        Integer type = uploadRequest.getType();
        if (type == null) {
            uploadFileGenericRepository.saveAndFlush(uploadFile);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
        Calendar endDate = Calendar.getInstance();

        switch (type) {
            case Constants.UploadFile.EXTENSION_REQUEST:
                Request r = requestRepository.findOne(uploadRequest.getId());
                List<Extension> lstExtensionRq = r.getLstExtension();
                if (lstExtensionRq == null) lstExtensionRq = new ArrayList<>();
                Extension e = new Extension();
                e.setCreateDate(Calendar.getInstance());
                e.setObsolete(false);
                e.setInitial(false);
                e.setComment(uploadRequest.getExtensionComment());
                uploadFile.setObsolete(false);
                e.setUploadFileGeneric(uploadFile);
                e.setInsAudit(sharedUserService.getLoggedUserId());


                endDate.setTime(sdf.parse(uploadRequest.getEndDate()));
                e.setEndDate(endDate);
                r.setEndDate(endDate);


                lstExtensionRq.add(e);
                requestRepository.saveAndFlush(r);
                break;
            case Constants.UploadFile.EXTENSION_COMMENT:
                Comment c = commentRepository.findOne(uploadRequest.getId());
                List<Extension> lstExtensionCm = c.getLstExtension();
                if (lstExtensionCm == null) lstExtensionCm = new ArrayList<>();
                Extension eC = new Extension();
                eC.setCreateDate(Calendar.getInstance());
                eC.setObsolete(false);
                eC.setInitial(false);
                eC.setComment(uploadRequest.getExtensionComment());
                uploadFile.setObsolete(false);
                eC.setUploadFileGeneric(uploadFile);
                eC.setInsAudit(sharedUserService.getLoggedUserId());

                endDate.setTime(sdf.parse(uploadRequest.getEndDate()));
                eC.setEndDate(endDate);
                c.setEndDate(endDate);

                lstExtensionCm.add(eC);
                commentRepository.saveAndFlush(c);
                break;
            case Constants.UploadFile.EXTENSION_RECOMMENDATION:
                Recommendation rec = recommendationRepository.findOne(uploadRequest.getId());
                List<Extension> lstExtensionRec = rec.getLstExtension();
                if (lstExtensionRec == null) lstExtensionRec = new ArrayList<>();
                Extension eRec = new Extension();
                eRec.setCreateDate(Calendar.getInstance());
                eRec.setObsolete(false);
                eRec.setInitial(false);
                eRec.setComment(uploadRequest.getExtensionComment());
                uploadFile.setObsolete(false);
                eRec.setUploadFileGeneric(uploadFile);
                eRec.setInsAudit(sharedUserService.getLoggedUserId());

                endDate.setTime(sdf.parse(uploadRequest.getEndDate()));
                eRec.setEndDate(endDate);
                rec.setEndDate(endDate);


                lstExtensionRec.add(eRec);
                recommendationRepository.saveAndFlush(rec);
                break;
            case Constants.UploadFile.EXTENSION_OBSERVATION:
                Observation obs = observationRepository.findOne(uploadRequest.getId());
                List<Extension> lstExtensionObs = obs.getLstExtension();
                if (lstExtensionObs == null) lstExtensionObs = new ArrayList<>();
                Extension eObs = new Extension();
                eObs.setCreateDate(Calendar.getInstance());
                eObs.setObsolete(false);
                eObs.setInitial(false);
                eObs.setComment(uploadRequest.getExtensionComment());
                uploadFile.setObsolete(false);
                eObs.setUploadFileGeneric(uploadFile);
                eObs.setInsAudit(sharedUserService.getLoggedUserId());

                endDate.setTime(sdf.parse(uploadRequest.getEndDate()));
                eObs.setEndDate(endDate);
                obs.setEndDate(endDate);

                lstExtensionObs.add(eObs);
                observationRepository.saveAndFlush(obs);
                break;
            case Constants.UploadFile.EXTENSION_RESPONSIBILITY:
                Responsibility resp = responsibilityRepository.findOne(uploadRequest.getId());
                List<Extension> lstExtensionResp = resp.getLstExtension();
                if (lstExtensionResp == null) lstExtensionResp = new ArrayList<>();
                Extension eResp = new Extension();
                eResp.setCreateDate(Calendar.getInstance());
                eResp.setObsolete(false);
                eResp.setInitial(false);
                eResp.setComment(uploadRequest.getExtensionComment());
                uploadFile.setObsolete(false);
                eResp.setUploadFileGeneric(uploadFile);
                eResp.setInsAudit(sharedUserService.getLoggedUserId());

                endDate.setTime(sdf.parse(uploadRequest.getEndDate()));
                eResp.setEndDate(endDate);
                resp.setEndDate(endDate);


                lstExtensionResp.add(eResp);
                responsibilityRepository.saveAndFlush(resp);
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
                LetterUploadFileGenericRel letterFileRel = new LetterUploadFileGenericRel();
                uploadFile.setObsolete(false);
                letterFileRel.setLetter(lt);
                letterFileRel.setAdditional(true);
                letterFileRel.setUploadFileGeneric(uploadFile);
                uploadFileGenericRepository.save(uploadFile);
                letterUploadFileGenericRelRepository.saveAndFlush(letterFileRel);
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

    //VALIDACIONES PARA
    //  DETECTAR SI  YA EXISTE UNA PRORROGA PARA REQUERIMIENTOS, OBSERVACIONES, RECOMENDACIONES, PLIEGOS Y PROMOCIONES
    //  VERIFICAR QUE LA FECHA SEA MAYOR A LA FECHA ACTUAL DEL ELEMENTO
    @Override
    public boolean validateExtensions(ResponseMessage responseMessage, UploadFileRequest uploadRequest) throws ParseException {
        Integer type = uploadRequest.getType();

        boolean result = false;

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
        Calendar newEndDate = Calendar.getInstance();

        if (type != null) {

            switch (type) {
                case Constants.UploadFile.EXTENSION_REQUEST:

                    Long lastIdReqExt = requestRepository.findLastExtensionIdByRequestId(uploadRequest.getId());

                    if (lastIdReqExt != null && lastIdReqExt > 0) {
                        responseMessage.setHasError(true);
                        responseMessage.setTitle("Agregar prórroga");
                        responseMessage.setMessage("No es posible agregar una prórroga. El elemento ya cuenta con una prórroga previamente registrada.");
                        result = true;
                        break;
                    }

                    Request req = requestRepository.findOne(uploadRequest.getId());
                    newEndDate.setTime(sdf.parse(uploadRequest.getEndDate()));

                    if (!newEndDate.after(req.getEndDate())) {
                        responseMessage.setHasError(true);
                        responseMessage.setTitle("Agregar prórroga");
                        responseMessage.setMessage("La nueva fecha límite debe ser mayor a la fecha límite actual del elemento.");
                        result = true;
                        break;
                    }

                    break;
                case Constants.UploadFile.EXTENSION_COMMENT:
                    Long lastIdCommExt = commentRepository.findLastExtensionIdByCommentId(uploadRequest.getId());

                    if (lastIdCommExt != null && lastIdCommExt > 0) {
                        responseMessage.setHasError(true);
                        responseMessage.setTitle("Agregar prórroga");
                        responseMessage.setMessage("No es posible agregar una prórroga. El elemento ya cuenta con una prórroga previamente registrada.");
                        result = true;
                        break;
                    }

                    Comment com = commentRepository.findOne(uploadRequest.getId());
                    newEndDate.setTime(sdf.parse(uploadRequest.getEndDate()));

                    if (!newEndDate.after(com.getEndDate())) {
                        responseMessage.setHasError(true);
                        responseMessage.setTitle("Agregar prórroga");
                        responseMessage.setMessage("La nueva fecha límite debe ser mayor a la fecha límite actual del elemento.");
                        result = true;
                        break;
                    }

                    break;
                case Constants.UploadFile.EXTENSION_RECOMMENDATION:
                    Long lastIdRecExt = recommendationRepository.findLastExtensionIdByRecommendationId(uploadRequest.getId());

                    if (lastIdRecExt != null && lastIdRecExt > 0) {
                        responseMessage.setHasError(true);
                        responseMessage.setTitle("Agregar prórroga");
                        responseMessage.setMessage("No es posible agregar una prórroga. El elemento ya cuenta con una prórroga previamente registrada.");
                        result = true;
                        break;
                    }

                    Recommendation rec = recommendationRepository.findOne(uploadRequest.getId());
                    newEndDate.setTime(sdf.parse(uploadRequest.getEndDate()));

                    if (!newEndDate.after(rec.getEndDate())) {
                        responseMessage.setHasError(true);
                        responseMessage.setTitle("Agregar prórroga");
                        responseMessage.setMessage("La nueva fecha límite debe ser mayor a la fecha límite actual del elemento.");
                        result = true;
                        break;
                    }
                    break;
                case Constants.UploadFile.EXTENSION_OBSERVATION:
                    Long lastIdObsExt = observationRepository.findLastExtensionIdByObservationId(uploadRequest.getId());

                    if (lastIdObsExt != null && lastIdObsExt > 0) {
                        responseMessage.setHasError(true);
                        responseMessage.setTitle("Agregar prórroga");
                        responseMessage.setMessage("No es posible agregar una prórroga. El elemento ya cuenta con una prórroga previamente registrada.");
                        result = true;
                        break;
                    }

                    Observation obs = observationRepository.findOne(uploadRequest.getId());
                    newEndDate.setTime(sdf.parse(uploadRequest.getEndDate()));

                    if (!newEndDate.after(obs.getEndDate())) {
                        responseMessage.setHasError(true);
                        responseMessage.setTitle("Agregar prórroga");
                        responseMessage.setMessage("La nueva fecha límite debe ser mayor a la fecha límite actual del elemento.");
                        result = true;
                        break;
                    }

                    break;
                case Constants.UploadFile.EXTENSION_RESPONSIBILITY:
                    Long lastIdResExt = responsibilityRepository.findLastExtensionIdByResponsibilityId(uploadRequest.getId());

                    if (lastIdResExt != null && lastIdResExt > 0) {
                        responseMessage.setHasError(true);
                        responseMessage.setTitle("Agregar prórroga");
                        responseMessage.setMessage("No es posible agregar una prórroga. El elemento ya cuenta con una prórroga previamente registrada.");
                        result = true;
                        break;
                    }

                    Responsibility res = responsibilityRepository.findOne(uploadRequest.getId());
                    newEndDate.setTime(sdf.parse(uploadRequest.getEndDate()));

                    if (!newEndDate.after(res.getEndDate())) {
                        responseMessage.setHasError(true);
                        responseMessage.setTitle("Agregar prórroga");
                        responseMessage.setMessage("La nueva fecha límite debe ser mayor a la fecha límite actual del elemento.");
                        result = true;
                        break;
                    }

                    break;
            }

        }

        return result;
    }

    @Override
    public UploadFileGeneric getPathAndFilename(Long id) {
        UploadFileGeneric file = uploadFileGenericRepository.getPathAndFilename(id);
        return file;
    }

    @Override
    public File createDownloadableFile(String fileName, String extension, HttpServletRequest request) {

        String temporalPath = request.getSession().getServletContext().getRealPath("") + Constants.TEMPORAL_PATH_FILES;

        File tempPath = new File(temporalPath);

        //crea la carpeta donde se almacenara la imagen si no existe
        if (!tempPath.exists()) {
            if (tempPath.mkdirs() == false) {
                return null;
            }
        }

        String guidId = UUID.randomUUID().toString();

        File file = new File(temporalPath + fileName + "_" + guidId + extension);
        return file;
    }

    @Override
    public File getFileToDownload(Long fileId, HttpServletRequest request, HttpServletResponse response) throws IOException {

            UploadFileGeneric realFile = uploadFileGenericRepository.getPathAndFilename(fileId);
            File finalFile = createDownloadableFileFromUploadedFile(realFile.getFileName(), request);
            FileOutputStream fos = new FileOutputStream(finalFile);

            byte[] buffer = new byte[1024];

            String path = request.getSession().getServletContext().getRealPath("");
            File fileIn = new File(path, realFile.getPath());
            FileInputStream in = new FileInputStream(new File(fileIn, realFile.getRealFileName()));

            int len;
            while ((len = in.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            in.close();
            fos.close();

            response.setContentType("application/force-download");
            response.setContentLength((int) finalFile.length());
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + realFile.getFileName() + "\"");//fileName);

            finalFile.deleteOnExit();
            return finalFile;
//    }
//
//        response.setContentType("application/force-download");
//        response.setContentLength((int) finalFile.length());
//        response.setHeader("Content-Transfer-Encoding", "binary");
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"");

//        return finalFile;
    }

    @Override
    public File createDownloadableFileFromUploadedFile(String completeFileName, HttpServletRequest request) {

        String extension = "", fileName = "";
        String[] completeFileNameArr = null;

        completeFileNameArr = completeFileName.split("\\.");
        extension = completeFileNameArr[completeFileNameArr.length - 1];

        for (int i = 0; i < completeFileNameArr.length - 1; i++) {
            if (!fileName.equals(""))
                fileName += ".";
            fileName += completeFileNameArr[i];
        }

        String temporalPath = request.getSession().getServletContext().getRealPath("") + Constants.TEMPORAL_PATH_FILES;

        File tempPath = new File(temporalPath);

        //crea la carpeta donde se almacenara la imagen si no existe
        if (!tempPath.exists()) {
            if (tempPath.mkdirs() == false) {
                return null;
            }
        }

        String guidId = UUID.randomUUID().toString();

        File file = new File(temporalPath + fileName + "_" + guidId + "."+ extension);
        return file;
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
