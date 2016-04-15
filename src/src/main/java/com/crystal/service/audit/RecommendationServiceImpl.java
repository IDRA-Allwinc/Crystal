package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.*;
import com.crystal.model.entities.audit.Audit;
import com.crystal.model.entities.audit.Extension;
import com.crystal.model.entities.audit.Recommendation;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.RecommendationDto;
import com.crystal.model.entities.catalog.Area;
import com.crystal.model.entities.catalog.ObservationType;
import com.crystal.model.shared.Constants;
import com.crystal.model.shared.SelectList;
import com.crystal.model.shared.UploadFileGeneric;
import com.crystal.repository.account.UserRepository;
import com.crystal.repository.catalog.*;
import com.crystal.repository.catalog.AuditRepository;
import com.crystal.repository.catalog.ExtensionRepository;
import com.crystal.repository.catalog.RecommendationRepository;
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
public class RecommendationServiceImpl implements RecommendationService {


    @Autowired
    RecommendationRepository recommendationRepository;

    @Autowired
    SharedUserService sharedUserService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AreaService areaService;

    @Autowired
    AuditRepository auditRepository;

    @Autowired
    ObservationTypeRepository observationTypeRepository;
    @Autowired
    ExtensionRepository extensionRepository;

    @Autowired
    ObservationRepository observationRepository;

    @Autowired
    ResponsibilityRepository responsibilityRepository;


    @Autowired
    UploadFileGenericRepository uploadFileGenericRepository;


    @Override
    public void upsert(Long id, Long auditId, ModelAndView modelView) {
        Gson gson = new Gson();

        RecommendationDto model;
        if (id != null) {
            model = recommendationRepository.findDtoById(id);
            List<SelectList> lstSelectedAreas = areaService.getSelectedAreasByRecommendationId(id);
            modelView.addObject("lstSelectedAreas", gson.toJson(lstSelectedAreas));
        } else {
            model = new RecommendationDto();
            model.setAuditId(auditId);
        }

        modelView.addObject("model", gson.toJson(model));
    }

    @Override
    public void save(RecommendationDto modelNew, ResponseMessage response) throws ParseException {
        Recommendation model = businessValidation(modelNew, null, response);
        if (response.isHasError())
            return;
        doSave(model);
    }

    @Override
    public void doObsolete(Long id, ResponseMessage response) {
        Recommendation model = recommendationRepository.findOne(id);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("La recomendaci&oacute;n ya fue eliminada o no existe en el sistema.");
            response.setTitle("Eliminar requerimiento");
            return;
        }

        if (model.isAttended() == true) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar una recomendaci&oacute;n que ya ha sido atendida.");
            response.setTitle("Eliminar recomendaci&oacute;n");
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
                response.setMessage("No es posible eliminar una recomendaci&oacute;n que ya tiene una prórroga.");
                response.setTitle("Eliminar recomendaci&oacute;n");
                return;
            }
        }

        model.setObsolete(true);
        model.setDelAudit(sharedUserService.getLoggedUserId());

        recommendationRepository.saveAndFlush(model);
    }

    @Override
    public void upsertViewDocs(Long recommendationId, ModelAndView modelAndView) {
        RecommendationDto model = recommendationRepository.findDtoById(recommendationId);
        model.setType(Constants.UploadFile.RECOMMENDATION);
        model.setDescription("");
        Gson gson = new Gson();
        String sModel = gson.toJson(model);
        modelAndView.addObject("model", sModel);
    }

    @Override
    @Transactional
    public void doDeleteUpFile(Long recommendationId, Long upFileId, ResponseMessage response) {
        Recommendation model = recommendationRepository.findByIdAndIsObsolete(recommendationId, false);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("La recomendaci&oacute;n ya fue eliminada o no existe en el sistema.");
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

        recommendationRepository.saveAndFlush(model);
    }

    @Override
    public void showAttention(Long recommendationId, ModelAndView modelAndView) {
        Gson gson = new Gson();
        AttentionDto model = recommendationRepository.findAttentionInfoById(recommendationId);
        String a = gson.toJson(model);
        modelAndView.addObject("model", a);
    }

    @Override
    public void doAttention(AttentionDto attentionDto, ResponseMessage response) {
        Recommendation model = attentionValidation(attentionDto, response);

        if (response.isHasError())
            return;

        doSave(model);
    }

    @Override
    public boolean findByNumber(RecommendationDto recommendationDto, ResponseMessage responseMessage) {
        if (recommendationDto.getId() != null && recommendationRepository.findByNumberWithId(recommendationDto.getNumber(), recommendationDto.getId()) != null) {
            responseMessage.setHasError(true);
            responseMessage.setMessage("Ya existe una recomendaci&oacute;n con el numeral indicado. Por favor revise la informaci&oacute;n e intente de nuevo.");
            return true;
        }

        if (recommendationDto.getId() == null && recommendationRepository.findByNumberAndIsObsolete(recommendationDto.getNumber(), false) != null) {
            responseMessage.setHasError(true);
            responseMessage.setMessage("Ya existe una recomendaci&oacute;n con el numeral indicado. Por favor revise la informaci&oacute;n e intente de nuevo.");
            return true;
        }

        return false;
    }

    private Recommendation businessValidation(RecommendationDto recommendationDto, AttentionDto attentionDto, ResponseMessage responseMessage) throws ParseException {
        Recommendation recommendation = null;

        if (recommendationDto != null) {
            Long id = recommendationDto.getId();

            if (id != null) {
                recommendation = recommendationRepository.findByIdAndIsObsolete(id, false);
                if (recommendation == null) {
                    responseMessage.setHasError(true);
                    responseMessage.setMessage("La recomendaci&oacute;n ya fue eliminada o no existe en el sistema.");
                    return null;
                } else if (recommendation.isAttended() == true) {
                    responseMessage.setHasError(true);
                    responseMessage.setMessage("No es posible modificar una recomendaci&oacute;n que ya ha sido atendida.");
                    return null;
                }

                recommendation.setUpdAudit(sharedUserService.getLoggedUserId());

            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                recommendation = new Recommendation();
                recommendation.setCreateDate(Calendar.getInstance());
                recommendation.setInsAudit(sharedUserService.getLoggedUserId());

                Calendar init = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                init.setTime(sdf.parse(recommendationDto.getInitDate()));
                end.setTime(sdf.parse(recommendationDto.getEndDate()));
                recommendation.setInitDate(init);
                recommendation.setEndDate(end);

                Extension e = new Extension();
                e.setInsAudit(sharedUserService.getLoggedUserId());
                e.setInitial(true);
                e.setCreateDate(Calendar.getInstance());
                e.setComment("Fecha de fin inicial.");
                e.setEndDate(recommendation.getEndDate());
                List<Extension> lstExtension = new ArrayList<>();
                lstExtension.add(e);
                recommendation.setLstExtension(lstExtension);
                recommendation.setInsAudit(sharedUserService.getLoggedUserId());

            }

            List<SelectList> lstSelectedAreas = new Gson().fromJson(recommendationDto.getLstSelectedAreas(), new TypeToken<List<SelectList>>() {
            }.getType());

            if (recommendation.getLstAreas() != null) {
                recommendation.setLstAreas(null);
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

            recommendation.merge(recommendationDto, null, null);
            recommendation.setLstAreas(lstNewSelectedAreas);
            Audit a = auditRepository.findOne(recommendationDto.getAuditId());
            recommendation.setAudit(a);
        }

        if (attentionDto != null) {
            recommendation = recommendationRepository.findOne(attentionDto.getId());
            recommendation.merge(null, attentionDto, userRepository.findOne(sharedUserService.getLoggedUserId()));
        }

        return recommendation;
    }

    @Transactional
    private void doSave(Recommendation model) {
        recommendationRepository.saveAndFlush(model);
    }

    private Recommendation attentionValidation(AttentionDto attentionDto, ResponseMessage response) {

        Recommendation model;

        if (attentionDto.getId() == null) {
            response.setHasError(true);
            response.setMessage("La recomendaci&oacute;n no existe o ya fue eliminada.");
            return null;
        } else {

            model = recommendationRepository.findByIdAndIsObsolete(attentionDto.getId(), false);
            if (model == null) {
                response.setHasError(true);
                response.setMessage("La recomendaci&oacute;n no existe o ya fue eliminada.");
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
        AttentionDto model = recommendationRepository.findAttentionInfoById(id);
        String a = gson.toJson(model);
        modelAndView.addObject("lstObservationType", gson.toJson(observationTypeRepository.findNoObsolete()));
        modelAndView.addObject("model", a);
    }

    @Override
    public void doReplication(AttentionDto attentionDto, ResponseMessage response) throws ParseException {
        if (!(attentionDto.getReplicateAs().equals(Constants.OBSERVATION_R) ||
                attentionDto.getReplicateAs().equals(Constants.RESPONSIBILITY_R))) {
            response.setHasError(true);
            response.setMessage("Debe de elegir una opci&oacute;n valida para replicar.");
            return;
        }

        attentionDto.setReplication(true);

        Recommendation model = attentionValidation(attentionDto, response);
        if (response.isHasError())
            return;

        doSaveAndReplication(model, attentionDto);
    }

    @Transactional
    private void doSaveAndReplication(Recommendation model, AttentionDto attentionDto) throws ParseException {


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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();

        if (model.getReplicatedAs().equals(Constants.OBSERVATION_R)) {
            Observation observation = new Observation();

            observation.setNumber(model.getNumber());
            observation.setDescription(model.getDescription());
            observation.setCreateDate(Calendar.getInstance());
            observation.setInsAudit(sharedUserService.getLoggedUserId());
            observation.setLstAreas(lstNewSelectedAreas);
            observation.setAudit(model.getAudit());

            calendar.setTime(sdf.parse(attentionDto.getInitDate()));
            observation.setInitDate(calendar);

            calendar.setTime(sdf.parse(attentionDto.getEndDate()));
            observation.setEndDate(model.getEndDate());

            Extension e = new Extension();
            e.setInsAudit(sharedUserService.getLoggedUserId());
            e.setInitial(true);
            e.setCreateDate(Calendar.getInstance());
            e.setComment("Fecha de fin inicial.");
            e.setEndDate(observation.getEndDate());
            List<Extension> lstExtension = new ArrayList<>();
            lstExtension.add(e);
            observation.setLstExtension(lstExtension);
            observation.setInsAudit(sharedUserService.getLoggedUserId());

            ObservationType observationType = new ObservationType();
            observationType.setId(attentionDto.getObservationTypeId());
            observation.setObservationType(observationType);
            observation.setObsolete(false);
            observation.setRecommendation(model);

            observationRepository.saveAndFlush(observation);

        } else if (model.getReplicatedAs().equals(Constants.RESPONSIBILITY_R)) {
            Responsibility responsibility = new Responsibility();

            responsibility.setNumber(model.getNumber());
            responsibility.setDescription(model.getDescription());
            responsibility.setCreateDate(Calendar.getInstance());
            responsibility.setInsAudit(sharedUserService.getLoggedUserId());
            responsibility.setLstAreas(lstNewSelectedAreas);
            responsibility.setAudit(model.getAudit());

            calendar.setTime(sdf.parse(attentionDto.getInitDate()));
            responsibility.setInitDate(calendar);

            calendar.setTime(sdf.parse(attentionDto.getEndDate()));
            responsibility.setEndDate(model.getEndDate());

            responsibility.setObsolete(false);
            responsibility.setRecommendation(model);

            Extension e = new Extension();
            e.setInsAudit(sharedUserService.getLoggedUserId());
            e.setInitial(true);
            e.setCreateDate(Calendar.getInstance());
            e.setComment("Fecha de fin inicial.");
            e.setEndDate(responsibility.getEndDate());
            List<Extension> lstExtension = new ArrayList<>();
            lstExtension.add(e);
            responsibility.setLstExtension(lstExtension);
            responsibility.setInsAudit(sharedUserService.getLoggedUserId());

            responsibilityRepository.saveAndFlush(responsibility);

        }

        recommendationRepository.saveAndFlush(model);
    }

    @Override
    public void extension(Long recommendationId, ModelAndView modelAndView) {
        RecommendationDto model = recommendationRepository.findDtoById(recommendationId);
        Long lastExtensionId = recommendationRepository.findLastExtensionIdByRecommendationId(recommendationId);
        if (lastExtensionId != null && lastExtensionId > 0)
            model.setHasExtension(true);

        model.setType(Constants.UploadFile.EXTENSION_RECOMMENDATION);
        Gson gson = new Gson();
        String sModel = gson.toJson(model);
        modelAndView.addObject("model", sModel);
    }

    @Override
    @Transactional
    public void doDeleteExtension(Long recommendationId, Long extensionId, ResponseMessage response) {
        Recommendation model = recommendationRepository.findByIdAndIsObsolete(recommendationId, false);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("La observación ya fue eliminada o no existe en el sistema.");
            response.setTitle("Eliminar prórroga");
            return;
        }

        if (model.isAttended() == true) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar la prórroga debido a que la observación ya fue atendida");
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

        Long lastSecondId = recommendationRepository.findSecondLastExtensionIdByRecommendationId(recommendationId, extensionId);

        if (lastSecondId == null || lastSecondId == 0) {
            response.setHasError(true);
            response.setMessage("No es posible recuperar la fecha de fin anterior.");
            response.setTitle("Eliminar prórroga");
            return;
        }

        Long lastId = recommendationRepository.findLastExtensionIdByRecommendationId(recommendationId);

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
        recommendationRepository.saveAndFlush(model);
        uploadFileGenericRepository.delete(lastExtensionFileId);
    }

    public ResponseMessage refreshExtensionRecommendation(Long id, ResponseMessage responseMessage){
        Gson gson = new Gson();
        RecommendationDto model = recommendationRepository.findDtoById(id);
        Long lastExtensionId = recommendationRepository.findLastExtensionIdByRecommendationId(id);
        if (lastExtensionId != null && lastExtensionId > 0)
            model.setHasExtension(true);

        model.setType(Constants.UploadFile.EXTENSION_RECOMMENDATION);

        responseMessage.setHasError(false);
        responseMessage.setReturnData(gson.toJson(model));

        return responseMessage;
    }
}
