package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.*;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.CommentDto;
import com.crystal.model.entities.catalog.Area;
import com.crystal.model.entities.catalog.ObservationType;
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
    public void save(CommentDto modelNew, ResponseMessage response) {

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
            response.setTitle("Eliminar requerimiento");
            return;
        }

        if (model.isAttended() == true) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar una observaci&oacute;n que ya ha sido atendida.");
            response.setTitle("Eliminar observaci&oacute;n");
            return;
        }

        if (model.getLstExtension() != null && model.getLstExtension().size() > 0) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar una observaci&oacute;n que ya tiene una prorroga.");
            response.setTitle("Eliminar observaci&oacute;n");
            return;
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

    private Comment businessValidation(CommentDto commentDto, AttentionDto attentionDto, ResponseMessage responseMessage) {
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
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                comment = new Comment();
                comment.setCreateDate(Calendar.getInstance());
                comment.setInsAudit(sharedUserService.getLoggedUserId());
                try {
                    Calendar init = Calendar.getInstance();
                    Calendar end = Calendar.getInstance();
                    init.setTime(sdf.parse(commentDto.getInitDate()));
                    end.setTime(sdf.parse(commentDto.getEndDate()));
                    comment.setInitDate(init);
                    comment.setEndDate(end);
                } catch (Exception e) {
                    return null;
                }

                Extension e = new Extension();
                e.setInsAudit(sharedUserService.getLoggedUserId());
                e.setInitial(true);
                e.setCreateDate(Calendar.getInstance());
                e.setComment("Fecha de fin inicial.");
                e.setEndDate(comment.getEndDate());
                List<Extension> lstExtension =  new ArrayList<>();
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


            if(attentionDto.isReplication() == true){
                model.setReplicated(true);
                model.setReplicatedAs(attentionDto.getReplicateAs());
            }

        }

        return model;
    }

    @Override
    public void showReplication(Long id, ModelAndView modelAndView) {
        Gson gson = new Gson();
        AttentionDto model = commentRepository.findAttentionInfoById(id);
        String a = gson.toJson(model);
        modelAndView.addObject("lstObservationType", gson.toJson(observationTypeRepository.findNoObsolete()));
        modelAndView.addObject("model", a);
    }

    @Override
    public void doReplication(AttentionDto attentionDto, ResponseMessage response) {
        if(!(attentionDto.getReplicateAs().equals(Constants.RECOMMENDATION_R) ||
                attentionDto.getReplicateAs().equals(Constants.OBSERVATION_R) ||
                attentionDto.getReplicateAs().equals(Constants.RESPONSIBILITY_R))){
            response.setHasError(true);
            response.setMessage("Debe de elegir una opci&oacute;n valida para replicar.");
            return;
        }

        attentionDto.setReplication(true);

        Comment model = attentionValidation(attentionDto, response);
        if (response.isHasError())
            return;

        doSaveAndReplication(model, attentionDto);
    }

    @Transactional
    private void doSaveAndReplication(Comment model, AttentionDto attentionDto) {


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



        if(model.getReplicatedAs().equals(Constants.RECOMMENDATION_R)){
            Recommendation recommendation = new Recommendation();

            recommendation.setNumber(model.getNumber());
            recommendation.setDescription(model.getDescription());
            recommendation.setCreateDate(Calendar.getInstance());
            recommendation.setInsAudit(sharedUserService.getLoggedUserId());
            recommendation.setLstAreas(lstNewSelectedAreas);
            recommendation.setAudit(model.getAudit());
            recommendation.setInitDate(model.getInitDate());
            recommendation.setEndDate(model.getEndDate());
            recommendation.setObsolete(false);

            recommendationRepository.saveAndFlush(recommendation);
        }
        else if(model.getReplicatedAs().equals(Constants.OBSERVATION_R)){
            Observation observation = new Observation();

            observation.setNumber(model.getNumber());
            observation.setDescription(model.getDescription());
            observation.setCreateDate(Calendar.getInstance());
            observation.setInsAudit(sharedUserService.getLoggedUserId());
            observation.setLstAreas(lstNewSelectedAreas);
            observation.setAudit(model.getAudit());
            observation.setInitDate(model.getInitDate());
            observation.setEndDate(model.getEndDate());
            ObservationType observationType = new ObservationType();
            observationType.setId(attentionDto.getObservationTypeId());
            observation.setObservationType(observationType);
            observation.setObsolete(false);

            observationRepository.saveAndFlush(observation);

        }
        else if(model.getReplicatedAs().equals(Constants.RESPONSIBILITY_R)){
            Responsibility responsibility = new Responsibility();

            responsibility.setNumber(model.getNumber());
            responsibility.setDescription(model.getDescription());
            responsibility.setCreateDate(Calendar.getInstance());
            responsibility.setInsAudit(sharedUserService.getLoggedUserId());
            responsibility.setLstAreas(lstNewSelectedAreas);
            responsibility.setAudit(model.getAudit());
            responsibility.setInitDate(model.getInitDate());
            responsibility.setEndDate(model.getEndDate());
            responsibility.setObsolete(false);

            responsibilityRepository.saveAndFlush(responsibility);

        }

        commentRepository.saveAndFlush(model);
    }

    @Override
    public void extension(Long commentId, ModelAndView modelAndView) {
        CommentDto model = commentRepository.findDtoById(commentId);
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
            response.setTitle("Eliminar prorroga");
            return;
        }

        if (model.isAttended() == true) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar la prorroga debido a que La observación ya fue atendida");
            response.setTitle("Eliminar prorroga");
            return;
        }

        Extension e = extensionRepository.findByIdAndIsObsolete(extensionId, false);

        if(e==null) {
            response.setHasError(true);
            response.setMessage("La prorroga fue ya fue eliminada o no existe en el sistema.");
            response.setTitle("Eliminar prorroga");
            return;
        }

        if (model.isObsolete() == true) {
            response.setHasError(true);
            response.setMessage("La prorroga fue ya fue eliminada o no existe en el sistema.");
            response.setTitle("Eliminar prorroga");
            return;
        }

        Long lastSecondId = commentRepository.findSecondLastExtensionIdByCommentId(commentId, extensionId);

        if (lastSecondId == null || lastSecondId == 0) {
            response.setHasError(true);
            response.setMessage("No es posible recuperar la fecha de fin anterior.");
            response.setTitle("Eliminar prorroga");
            return;
        }

        Long lastId = commentRepository.findLastExtensionIdByCommentId(commentId);

        if (!lastId.equals(extensionId)) {
            response.setHasError(true);
            response.setMessage("La prorroga que intenta eliminar no es la última registrada.");
            response.setTitle("Eliminar prorroga");
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

}
