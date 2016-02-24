package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.Audit;
import com.crystal.model.entities.audit.Observation;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.ObservationDto;
import com.crystal.model.entities.catalog.Area;
import com.crystal.model.entities.catalog.ObservationType;
import com.crystal.model.shared.Constants;
import com.crystal.model.shared.SelectList;
import com.crystal.model.shared.UploadFileGeneric;
import com.crystal.repository.account.UserRepository;
import com.crystal.repository.catalog.AuditRepository;
import com.crystal.repository.catalog.ObservationRepository;
import com.crystal.repository.catalog.ObservationTypeRepository;
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
public class ObservationServiceImpl implements ObservationService {

    @Autowired
    ObservationRepository observationRepository;

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

    @Override
    public void upsert(Long id, Long auditId, ModelAndView modelView) {
        Gson gson = new Gson();

        ObservationDto model;
        if (id != null) {
            model = observationRepository.findDtoById(id);
            List<SelectList> lstSelectedAreas = areaService.getSelectedAreasByObservationId(id);
            modelView.addObject("lstSelectedAreas", gson.toJson(lstSelectedAreas));
        } else {
            model = new ObservationDto();
            model.setAuditId(auditId);
        }

        modelView.addObject("lstObservationType", gson.toJson(observationTypeRepository.findNoObsolete()));
        modelView.addObject("model", gson.toJson(model));
    }

    @Override
    public void save(ObservationDto modelNew, ResponseMessage response) {

        Observation model = businessValidation(modelNew, null, response);

        if (response.isHasError())
            return;

        doSave(model);
    }

    public void doObsolete(Long id, ResponseMessage response) {

        Observation model = observationRepository.findOne(id);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("El pliego de observaciones ya fue eliminada o no existe en el sistema.");
            response.setTitle("Eliminar pliego de observaciones");
            return;
        }

        if (model.isAttended() == true) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar un pliego de observaciones que ya ha sido atendido.");
            response.setTitle("Eliminar pliego de observaciones");
            return;
        }

        if (model.getLstExtension() != null && model.getLstExtension().size() > 0) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar un pliego de observaciones que ya tiene una prorroga.");
            response.setTitle("Eliminar pliego de observaciones");
            return;
        }

        model.setObsolete(true);
        model.setDelAudit(sharedUserService.getLoggedUserId());

        observationRepository.saveAndFlush(model);
    }

    @Transactional
    private void doSave(Observation model) {
        observationRepository.saveAndFlush(model);
    }

    private Observation businessValidation(ObservationDto observationDto, AttentionDto attentionDto, ResponseMessage responseMessage) {
        Observation observation = null;

        if (observationDto != null) {
            Long id = observationDto.getId();

            if (id != null) {
                observation = observationRepository.findByIdAndIsObsolete(id, false);
                if (observation == null) {
                    responseMessage.setHasError(true);
                    responseMessage.setMessage("El pliego de observaciones ya fue eliminado o no existe en el sistema.");
                    return null;
                } else if (observation.isAttended() == true) {
                    responseMessage.setHasError(true);
                    responseMessage.setMessage("No es posible modificar un pliego de observaciones que ya ha sido atendido.");
                    return null;
                }

                observation.setUpdAudit(sharedUserService.getLoggedUserId());

            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                observation = new Observation();
                observation.setCreateDate(Calendar.getInstance());
                observation.setInsAudit(sharedUserService.getLoggedUserId());
                try {
                    Calendar init = Calendar.getInstance();
                    Calendar end = Calendar.getInstance();
                    init.setTime(sdf.parse(observationDto.getInitDate()));
                    end.setTime(sdf.parse(observationDto.getEndDate()));
                    observation.setInitDate(init);
                    observation.setEndDate(end);
                } catch (Exception e) {
                    return null;
                }

            }

            List<SelectList> lstSelectedAreas = new Gson().fromJson(observationDto.getLstSelectedAreas(), new TypeToken<List<SelectList>>() {
            }.getType());

            if (observation.getLstAreas() != null) {
                observation.setLstAreas(null);
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


            ObservationType ot =  new ObservationType();
            ot.setId(observationDto.getObservationTypeId());
            observation.setObservationType(ot);

            observation.merge(observationDto, null, null);
            observation.setLstAreas(lstNewSelectedAreas);
            Audit a = auditRepository.findOne(observationDto.getAuditId());
            observation.setAudit(a);
        }

        if (attentionDto != null) {
            observation = observationRepository.findOne(attentionDto.getId());
            observation.merge(null, attentionDto, userRepository.findOne(sharedUserService.getLoggedUserId()));
        }

        return observation;
    }

    @Override
    public void upsertViewDocs(Long observationId, ModelAndView modelAndView) {
        ObservationDto model = observationRepository.findDtoById(observationId);
        model.setType(Constants.UploadFile.OBSERVATION);
        model.setDescription("");
        Gson gson = new Gson();
        String sModel = gson.toJson(model);
        modelAndView.addObject("model", sModel);
    }

    @Override
    @Transactional
    public void doDeleteUpFile(Long observationId, Long upFileId, ResponseMessage response) {
        Observation model = observationRepository.findByIdAndIsObsolete(observationId, false);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("El pliego de observaciones ya fue eliminado o no existe en el sistema.");
            response.setTitle("Eliminar documento");
            return;
        }

        if (model.isAttended() == true) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar el archivo debido a que el pliego de observaciones ya fue atendido");
            response.setTitle("Eliminar documento");
            return;
        }

        List<UploadFileGeneric> lstFiles = model.getLstEvidences();

        for (int i = lstFiles.size() - 1; i >= 0; i--) {
            if (lstFiles.get(i).getId().equals(upFileId))
                lstFiles.remove(i);
        }

        observationRepository.saveAndFlush(model);
    }

    @Override
    public void showAttention(Long id, ModelAndView modelAndView) {
        Gson gson = new Gson();
        AttentionDto model = observationRepository.findAttentionInfoById(id);
        String a = gson.toJson(model);
        modelAndView.addObject("model", a);
    }

    @Override
    public void doAttention(AttentionDto attentionDto, ResponseMessage response) {

        Observation model = attentionValidation(attentionDto, response);

        if (response.isHasError())
            return;

        doSave(model);
    }

    private Observation attentionValidation(AttentionDto attentionDto, ResponseMessage response) {

        Observation model;

        if (attentionDto.getId() == null) {
            response.setHasError(true);
            response.setMessage("El pliego de observaciones no existe o ya fue eliminada.");
            return null;
        } else {

            model = observationRepository.findByIdAndIsObsolete(attentionDto.getId(), false);
            if (model == null) {
                response.setHasError(true);
                response.setMessage("El pliego de observaciones no existe o ya fue eliminada.");
                return null;
            }

            model.setAttentionDate(Calendar.getInstance());
            model.setAttentionComment(attentionDto.getAttentionComment());
            model.setAttended(true);
            model.setAttentionUser(userRepository.findOne(sharedUserService.getLoggedUserId()));
        }

        return model;
    }
}
