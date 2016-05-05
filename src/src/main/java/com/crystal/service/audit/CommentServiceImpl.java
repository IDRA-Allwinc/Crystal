package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.*;
import com.crystal.model.entities.audit.dto.*;
import com.crystal.model.entities.catalog.Area;
import com.crystal.model.shared.Constants;
import com.crystal.model.shared.SelectList;
import com.crystal.model.shared.UploadFileGeneric;
import com.crystal.repository.account.UserRepository;
import com.crystal.repository.catalog.*;
import com.crystal.repository.shared.UploadFileGenericRepository;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.catalog.AreaService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UploadFileGenericRepository repositoryUf;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AreaService areaService;

    @Autowired
    AuditRepository auditRepository;

    @Autowired
    SharedUserService sharedUserService;

    @Autowired
    ObservationTypeRepository observationTypeRepository;

    @Autowired
    RecommendationRepository recommendationRepository;

    @Autowired
    ObservationRepository observationRepository;

    @Autowired
    ResponsibilityRepository responsibilityRepository;

    @Autowired
    ExtensionRepository extensionRepository;

    @Autowired
    UploadFileGenericRepository uploadFileGenericRepository;

    @Autowired
    RecommendationService recommendationService;

    @Autowired
    ObservationService observationService;

    @Autowired
    ResponsibilityService responsibilityService;

    @Override
    public void upsert(Long id, Long auditId, ModelAndView modelView) {
        Gson gson = new Gson();

        CommentDto model;
        if (id != null) {
            model = commentRepository.findDtoById(id);
            List<SelectList> lstSelectedAreas = areaService.getSelectedAreasByCommentId(id);
            modelView.addObject("lstSelectedAreas", gson.toJson(lstSelectedAreas));
        } else {
            model = new CommentDto();
            model.setAuditId(auditId);
        }

        modelView.addObject("model", gson.toJson(model));
    }

    @Override
    public void save(CommentDto modelNew, ResponseMessage response) throws ParseException {

        Comment model = businessValidation(modelNew, null, response);

        if (response.isHasError())
            return;

        doSave(model);
    }

    public void doObsolete(Long id, ResponseMessage response) {

        Comment model = commentRepository.findOne(id);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("La observaci&oacute;n ya fue eliminada o no existe en el sistema.");
            response.setTitle("Eliminar observaci&oacute;n");
            return;
        }

        if (model.isAttended() == true) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar una observaci&oacute;n que ya ha sido atendida.");
            response.setTitle("Eliminar observaci&oacute;n");
            return;
        }

        if (model.getLstExtension() != null) {

            boolean hasExtension = false;

            for (Extension e : model.getLstExtension()) {
                if (e.isInitial() == false && e.isObsolete() == false)
                    hasExtension = true;
            }

            if (hasExtension) {
                response.setHasError(true);
                response.setMessage("No es posible eliminar una observación que ya tiene una prórroga.");
                response.setTitle("Eliminar observaci&oacute;n");
                return;
            }
        }

        model.setObsolete(true);
        model.setDelAudit(sharedUserService.getLoggedUserId());

        //TODO HACER OBSOLETOS LOS ARCHIVOS
        commentRepository.saveAndFlush(model);
    }

    @Transactional
    private void doSave(Comment model) {
        commentRepository.saveAndFlush(model);
    }

    public Comment businessValidation(CommentDto commentDto, AttentionDto attentionDto, ResponseMessage responseMessage) throws ParseException {
        Comment comment = null;

        if (commentDto != null) {
            Long id = commentDto.getId();

            if (id != null) {
                comment = commentRepository.findByIdAndIsObsolete(id, false);
                if (comment == null) {
                    responseMessage.setHasError(true);
                    responseMessage.setMessage("La observaci&oacute;n ya fue eliminado o no existe en el sistema.");
                    return null;
                } else if (comment.isAttended() == true) {
                    responseMessage.setHasError(true);
                    responseMessage.setMessage("No es posible modificar una observaci&oacute;n que ya ha sido atendida.");
                    return null;
                }

                comment.setUpdAudit(sharedUserService.getLoggedUserId());

            } else {
                SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
                comment = new Comment();
                comment.setCreateDate(Calendar.getInstance());
                comment.setInsAudit(sharedUserService.getLoggedUserId());
                Calendar init = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                init.setTime(sdf.parse(commentDto.getInitDate()));
                end.setTime(sdf.parse(commentDto.getEndDate()));
                comment.setInitDate(init);
                comment.setEndDate(end);

                Extension e = new Extension();
                e.setInsAudit(sharedUserService.getLoggedUserId());
                e.setInitial(true);
                e.setCreateDate(Calendar.getInstance());
                e.setComment("Fecha de fin inicial.");
                e.setComment("Fecha de fin inicial.");
                e.setEndDate(comment.getEndDate());
                List<Extension> lstExtension = new ArrayList<>();
                lstExtension.add(e);
                comment.setLstExtension(lstExtension);
                comment.setInsAudit(sharedUserService.getLoggedUserId());
            }

            List<SelectList> lstSelectedAreas = new Gson().fromJson(commentDto.getLstSelectedAreas(), new TypeToken<List<SelectList>>() {
            }.getType());

            if (comment.getLstAreas() != null) {
                comment.setLstAreas(null);
            }

            List<Area> lstNewSelectedAreas;
            if (lstSelectedAreas != null && lstSelectedAreas.size() > 0) {
                lstNewSelectedAreas = new ArrayList<>();
                for (SelectList item : lstSelectedAreas) {
                    Area a = new Area();
                    a.setId(item.getId());
                    lstNewSelectedAreas.add(a);
                }
            } else {
                responseMessage.setHasError(true);
                responseMessage.setMessage("Debe seleccionar al menos un &aacute;rea.");
                return null;
            }

            comment.merge(commentDto, null, null);
            comment.setLstAreas(lstNewSelectedAreas);
            Audit a = auditRepository.findOne(commentDto.getAuditId());
            comment.setAudit(a);
        }

        if (attentionDto != null) {
            comment = commentRepository.findOne(attentionDto.getId());
            comment.merge(null, attentionDto, userRepository.findOne(sharedUserService.getLoggedUserId()));
        }

        return comment;
    }

    @Override
    public void upsertViewDocs(Long commmentId, ModelAndView modelAndView) {
        CommentDto model = commentRepository.findDtoById(commmentId);
        model.setType(Constants.UploadFile.COMMENT);
        model.setDescription("");
        Gson gson = new Gson();
        String sModel = gson.toJson(model);
        modelAndView.addObject("model", sModel);
    }

    @Override
    @Transactional
    public void doDeleteUpFile(Long commentId, Long upFileId, ResponseMessage response) {
        Comment model = commentRepository.findByIdAndIsObsolete(commentId, false);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("La observaci&oacute;n ya fue eliminada o no existe en el sistema.");
            response.setTitle("Eliminar documento");
            return;
        }

        if (model.isAttended() == true) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar el archivo debido a que el requerimiento ya fue atendido");
            response.setTitle("Eliminar documento");
            return;
        }

        List<UploadFileGeneric> lstFiles = model.getLstEvidences();

        for (int i = lstFiles.size() - 1; i >= 0; i--) {
            if (lstFiles.get(i).getId().equals(upFileId))
                lstFiles.remove(i);
        }

        commentRepository.saveAndFlush(model);
    }

    @Override
    public void showAttention(Long id, ModelAndView modelAndView) {
        Gson gson = new Gson();
        AttentionDto model = commentRepository.findAttentionInfoById(id);
        String a = gson.toJson(model);
        modelAndView.addObject("model", a);
    }

    @Override
    public void doAttention(AttentionDto attentionDto, ResponseMessage response) {

        Comment model = attentionValidation(attentionDto, response);

        if (response.isHasError())
            return;

        doSave(model);
    }

    private Comment attentionValidation(AttentionDto attentionDto, ResponseMessage response) {

        Comment model;

        if (attentionDto.getId() == null) {
            response.setHasError(true);
            response.setMessage("La observaci&oacute;n no existe o ya fue eliminada.");
            return null;
        } else {

            model = commentRepository.findByIdAndIsObsolete(attentionDto.getId(), false);
            if (model == null) {
                response.setHasError(true);
                response.setMessage("La observaci&oacute;n no existe o ya fue eliminada.");
                return null;
            }

            model.setAttentionDate(Calendar.getInstance());
            model.setAttentionComment(attentionDto.getAttentionComment());
            model.setAttended(true);
            model.setAttentionUser(userRepository.findOne(sharedUserService.getLoggedUserId()));


            if (attentionDto.isReplication() == true) {
                model.setReplicated(true);
                model.setReplicatedAs(attentionDto.getReplicateAs());
            }

        }

        return model;
    }

    @Override
    public void showReplication(Long id, ModelAndView modelAndView) {
        Gson gson = new Gson();

        AttentionDto attention = commentRepository.findAttentionInfoById(id);
        CommentDto model = commentRepository.findDtoById(id);
        List<SelectList> lstSelectedAreas = areaService.getSelectedAreasByCommentId(id);
        modelAndView.addObject("model", gson.toJson(model));
        modelAndView.addObject("attention", gson.toJson(attention));
        modelAndView.addObject("lstSelectedAreas", gson.toJson(lstSelectedAreas));
        modelAndView.addObject("lstObservationType", gson.toJson(observationTypeRepository.findNoObsolete()));

    }

    @Override
    public void doReplication(CommentDto commentDto, AttentionDto attentionDto, ResponseMessage response) throws ParseException {

        if (!(attentionDto.getReplicateAs().equals(Constants.RECOMMENDATION_R) ||
                attentionDto.getReplicateAs().equals(Constants.OBSERVATION_R) ||
                attentionDto.getReplicateAs().equals(Constants.RESPONSIBILITY_R))) {
            response.setHasError(true);
            response.setMessage("Debe de elegir una opci&oacute;n valida para replicar.");
            return;
        }

        attentionDto.setReplication(true);

        Comment model = attentionValidation(attentionDto, response);
        if (response.isHasError())
            return;

        doSaveAndReplication(commentDto, model, response);
    }

    @Transactional
    private void doSaveAndReplication(CommentDto commentDto, Comment model, ResponseMessage responseMessage) throws ParseException {


        List<Area> lstSelectedAreas = model.getLstAreas();
        List<Area> lstNewSelectedAreas = null;

        if (lstSelectedAreas != null && lstSelectedAreas.size() > 0) {
            lstNewSelectedAreas = new ArrayList<>();
            for (Area item : lstSelectedAreas) {
                Area a = new Area();
                a.setId(item.getId());
                lstNewSelectedAreas.add(a);
            }
        }

        if (model.getReplicatedAs().equals(Constants.RECOMMENDATION_R)) {

            RecommendationDto recommendationDto = new RecommendationDto();

            recommendationDto.setNumber(commentDto.getNumber());
            recommendationDto.setDescription(commentDto.getDescription());
            recommendationDto.setInitDate(commentDto.getInitDate());
            recommendationDto.setEndDate(commentDto.getEndDate());
            recommendationDto.setLstSelectedAreas(commentDto.getLstSelectedAreas());
            recommendationDto.setAuditId(commentDto.getAuditId());

            Recommendation recommendation = recommendationService.businessValidation(recommendationDto, null, responseMessage);


            if (responseMessage.isHasError() == true)
                return;

            if (recommendationService.findByNumber(recommendationDto, responseMessage) == true) {
                return;
            }

            recommendationRepository.saveAndFlush(recommendation);

        } else if (model.getReplicatedAs().equals(Constants.OBSERVATION_R)) {

            ObservationDto observationDto = new ObservationDto();

            observationDto.setNumber(commentDto.getNumber());
            observationDto.setDescription(commentDto.getDescription());
            observationDto.setInitDate(commentDto.getInitDate());
            observationDto.setEndDate(commentDto.getEndDate());
            observationDto.setLstSelectedAreas(commentDto.getLstSelectedAreas());
            observationDto.setAuditId(commentDto.getAuditId());
            observationDto.setObservationTypeId(commentDto.getObservationTypeId());

            Observation observation = observationService.businessValidation(observationDto, null, responseMessage);


            if (responseMessage.isHasError() == true)
                return;

            if (observationService.findByNumber(observationDto, responseMessage) == true) {
                return;
            }

            observationRepository.saveAndFlush(observation);

        } else if (model.getReplicatedAs().equals(Constants.RESPONSIBILITY_R)) {

            ResponsibilityDto responsibilityDto = new ResponsibilityDto();

            responsibilityDto.setNumber(commentDto.getNumber());
            responsibilityDto.setDescription(commentDto.getDescription());
            responsibilityDto.setInitDate(commentDto.getInitDate());
            responsibilityDto.setEndDate(commentDto.getEndDate());
            responsibilityDto.setLstSelectedAreas(commentDto.getLstSelectedAreas());
            responsibilityDto.setAuditId(commentDto.getAuditId());

            Responsibility responsibility = responsibilityService.businessValidation(responsibilityDto, null, responseMessage);


            if (responseMessage.isHasError() == true)
                return;

            if (responsibilityService.findByNumber(responsibilityDto, responseMessage) == true) {
                return;
            }

            responsibilityRepository.saveAndFlush(responsibility);

        }

        commentRepository.saveAndFlush(model);
    }

    @Override
    public void extension(Long commentId, ModelAndView modelAndView) {
        CommentDto model = commentRepository.findDtoById(commentId);
        Long lastExtensionId = commentRepository.findLastExtensionIdByCommentId(commentId);
        if (lastExtensionId != null && lastExtensionId > 0)
            model.setHasExtension(true);

        model.setType(Constants.UploadFile.EXTENSION_COMMENT);
        Gson gson = new Gson();
        String sModel = gson.toJson(model);
        modelAndView.addObject("model", sModel);
    }

    @Override
    @Transactional
    public void doDeleteExtension(Long commentId, Long extensionId, ResponseMessage response) {
        Comment model = commentRepository.findByIdAndIsObsolete(commentId, false);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("La observación ya fue eliminada o no existe en el sistema.");
            response.setTitle("Eliminar prórroga");
            return;
        }

        if (model.isAttended() == true) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar la prórroga debido a que La observación ya fue atendida");
            response.setTitle("Eliminar prórroga");
            return;
        }

        Extension e = extensionRepository.findByIdAndIsObsolete(extensionId, false);

        if (e == null) {
            response.setHasError(true);
            response.setMessage("La prórroga fue ya fue eliminada o no existe en el sistema.");
            response.setTitle("Eliminar prórroga");
            return;
        }

        if (model.isObsolete() == true) {
            response.setHasError(true);
            response.setMessage("La prórroga fue ya fue eliminada o no existe en el sistema.");
            response.setTitle("Eliminar prórroga");
            return;
        }

        Long lastSecondId = commentRepository.findSecondLastExtensionIdByCommentId(commentId, extensionId);

        if (lastSecondId == null || lastSecondId == 0) {
            response.setHasError(true);
            response.setMessage("No es posible recuperar la fecha de fin anterior.");
            response.setTitle("Eliminar prórroga");
            return;
        }

        Long lastId = commentRepository.findLastExtensionIdByCommentId(commentId);

        if (!lastId.equals(extensionId)) {
            response.setHasError(true);
            response.setMessage("La prórroga que intenta eliminar no es la última registrada.");
            response.setTitle("Eliminar prórroga");
            return;
        }


        Extension lastExtension = extensionRepository.findOne(extensionId);
        Long lastExtensionFileId = lastExtension.getUploadFileGeneric().getId();

        lastExtension.setObsolete(true);
        lastExtension.setUploadFileGeneric(null);
        lastExtension.setUserDel(userRepository.findOne(sharedUserService.getLoggedUserId()));

        Extension lastSecondExtension = extensionRepository.findOne(lastSecondId);

        model.setEndDate(lastSecondExtension.getEndDate());
        model.setUserUpd(userRepository.findOne(sharedUserService.getLoggedUserId()));

        extensionRepository.save(lastExtension);
        commentRepository.saveAndFlush(model);
        uploadFileGenericRepository.delete(lastExtensionFileId);
    }

    @Override
    public ResponseMessage refreshExtensionComment(Long commentId, ResponseMessage responseMessage) {
        Gson gson = new Gson();
        CommentDto model = commentRepository.findDtoById(commentId);
        Long lastExtensionId = commentRepository.findLastExtensionIdByCommentId(commentId);
        if (lastExtensionId != null && lastExtensionId > 0)
            model.setHasExtension(true);

        model.setType(Constants.UploadFile.EXTENSION_COMMENT);

        responseMessage.setHasError(false);
        responseMessage.setReturnData(gson.toJson(model));

        return responseMessage;
    }

    @Override
    public boolean findByNumber(CommentDto commentDtoDto, ResponseMessage responseMessage) {

        if (commentDtoDto.getId() != null) {
            if (commentRepository.findByNumberWithId(commentDtoDto.getNumber(), commentDtoDto.getId(), commentDtoDto.getAuditId()) != null) {
                responseMessage.setHasError(true);
                responseMessage.setMessage("Ya existe una observaci&oacute;n con el numeral indicado para esta auditoría. Por favor revise la informaci&oacute;n e intente de nuevo.");
                return true;
            }
        } else if (commentDtoDto.getId() != null && commentRepository.findByNumberWithoutId(commentDtoDto.getNumber(), commentDtoDto.getAuditId()) != null) {
            responseMessage.setHasError(true);
            responseMessage.setMessage("Ya existe una observaci&oacute;n con el numeral indicado para esta auditoría. Por favor revise la informaci&oacute;n e intente de nuevo.");
            return true;
        }

        return false;
    }

}
