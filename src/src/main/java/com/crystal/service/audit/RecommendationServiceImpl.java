package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.*;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.ObservationDto;
import com.crystal.model.entities.audit.dto.RecommendationDto;
import com.crystal.model.entities.audit.dto.ResponsibilityDto;
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

    @Autowired
    ObservationService observationService;

    @Autowired
    ResponsibilityService responsibilityService;


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

        if (recommendationDto.getId() != null) {
            if (recommendationRepository.findByNumberWithId(recommendationDto.getNumber(), recommendationDto.getId(), recommendationDto.getAuditId()) != null) {
                responseMessage.setHasError(true);
                responseMessage.setMessage("Ya existe una recomendaci&oacute;n con el numeral indicado para esta auditoría. Por favor revise la informaci&oacute;n e intente de nuevo.");
                return true;
            }
        } else if (recommendationRepository.findByNumberWithoutId(recommendationDto.getNumber(), recommendationDto.getAuditId()) != null) {
            responseMessage.setHasError(true);
            responseMessage.setMessage("Ya existe una recomendaci&oacute;n con el numeral indicado para esta auditoría. Por favor revise la informaci&oacute;n e intente de nuevo.");
            return true;
        }

        return false;
    }

    @Override
    public Recommendation businessValidation(RecommendationDto recommendationDto, AttentionDto attentionDto, ResponseMessage responseMessage) throws ParseException {
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

        AttentionDto attention = recommendationRepository.findAttentionInfoById(id);
        RecommendationDto model = recommendationRepository.findDtoById(id);
        List<SelectList> lstSelectedAreas = areaService.getSelectedAreasByRecommendationId(id);
        modelAndView.addObject("model", gson.toJson(model));
        modelAndView.addObject("attention", gson.toJson(attention));
        modelAndView.addObject("lstSelectedAreas", gson.toJson(lstSelectedAreas));
        modelAndView.addObject("lstObservationType", gson.toJson(observationTypeRepository.findNoObsolete()));
    }

    @Override
    public void doReplication(RecommendationDto recommendationDto, AttentionDto attentionDto, ResponseMessage response) throws ParseException {
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

        doSaveAndReplication(recommendationDto, model, response);
    }

    @Transactional
    private void doSaveAndReplication(RecommendationDto recommendationDto, Recommendation model, ResponseMessage responseMessage) throws ParseException {

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


        if (model.getReplicatedAs().equals(Constants.OBSERVATION_R)) {

            ObservationDto observationDto = new ObservationDto();

            observationDto.setNumber(recommendationDto.getNumber());
            observationDto.setDescription(recommendationDto.getDescription());
            observationDto.setInitDate(recommendationDto.getInitDate());
            observationDto.setEndDate(recommendationDto.getEndDate());
            observationDto.setLstSelectedAreas(recommendationDto.getLstSelectedAreas());
            observationDto.setAuditId(recommendationDto.getAuditId());
            observationDto.setObservationTypeId(recommendationDto.getObservationTypeId());

            Observation observation = observationService.businessValidation(observationDto, null, responseMessage);


            if (responseMessage.isHasError() == true)
                return;

            if (observationService.findByNumber(observationDto, responseMessage) == true) {
                return;
            }

            observationRepository.saveAndFlush(observation);

        } else if (model.getReplicatedAs().equals(Constants.RESPONSIBILITY_R)) {

            ResponsibilityDto responsibilityDto = new ResponsibilityDto();

            responsibilityDto.setNumber(recommendationDto.getNumber());
            responsibilityDto.setDescription(recommendationDto.getDescription());
            responsibilityDto.setInitDate(recommendationDto.getInitDate());
            responsibilityDto.setEndDate(recommendationDto.getEndDate());
            responsibilityDto.setLstSelectedAreas(recommendationDto.getLstSelectedAreas());
            responsibilityDto.setAuditId(recommendationDto.getAuditId());

            Responsibility responsibility = responsibilityService.businessValidation(responsibilityDto, null, responseMessage);


            if (responseMessage.isHasError() == true)
                return;

            if (responsibilityService.findByNumber(responsibilityDto, responseMessage) == true) {
                return;
            }

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

    public ResponseMessage refreshExtensionRecommendation(Long id, ResponseMessage responseMessage) {
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
