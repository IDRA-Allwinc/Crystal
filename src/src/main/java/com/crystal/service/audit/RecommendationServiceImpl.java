package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.Audit;
import com.crystal.model.entities.audit.Comment;
import com.crystal.model.entities.audit.Recommendation;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.RecommendationDto;
import com.crystal.model.entities.catalog.Area;
import com.crystal.model.shared.SelectList;
import com.crystal.repository.account.UserRepository;
import com.crystal.repository.catalog.AuditRepository;
import com.crystal.repository.catalog.RecommendationRepository;
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
    public void save(RecommendationDto modelNew, ResponseMessage response) {
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

        if (model.getLstExtension() != null && model.getLstExtension().size() > 0) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar una recomendaci&oacute;n que ya tiene una prorroga.");
            response.setTitle("Eliminar recomendaci&oacute;n");
            return;
        }

        model.setObsolete(true);
        model.setDelAudit(sharedUserService.getLoggedUserId());

        recommendationRepository.saveAndFlush(model);
    }

    @Override
    public void upsertViewDocs(Long recommendationId, ModelAndView modelAndView) {

    }

    @Override
    public void showAttention(Long recommendationId, ModelAndView modelAndView) {

    }

    @Override
    public void doAttention(AttentionDto attentionDto, ResponseMessage response) {

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

    private Recommendation businessValidation(RecommendationDto recommendationDto, AttentionDto attentionDto, ResponseMessage responseMessage) {
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
                try {
                    Calendar init = Calendar.getInstance();
                    Calendar end = Calendar.getInstance();
                    init.setTime(sdf.parse(recommendationDto.getInitDate()));
                    end.setTime(sdf.parse(recommendationDto.getEndDate()));
                    recommendation.setInitDate(init);
                    recommendation.setEndDate(end);
                } catch (Exception e) {
                    return null;
                }

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
}
