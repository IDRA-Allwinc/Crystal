package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.Audit;
import com.crystal.model.entities.audit.Extension;
import com.crystal.model.entities.audit.Responsibility;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.ResponsibilityDto;
import com.crystal.model.entities.catalog.Area;
import com.crystal.model.shared.Constants;
import com.crystal.model.shared.SelectList;
import com.crystal.model.shared.UploadFileGeneric;
import com.crystal.repository.account.UserRepository;
import com.crystal.repository.catalog.AuditRepository;
import com.crystal.repository.catalog.ExtensionRepository;
import com.crystal.repository.catalog.ResponsibilityRepository;
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
public class ResponsibilityServiceImpl implements ResponsibilityService {

    @Autowired
    ResponsibilityRepository responsibilityRepository;

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
    ExtensionRepository extensionRepository;

    @Autowired
    UploadFileGenericRepository uploadFileGenericRepository;


    @Override
    public void upsert(Long id, Long auditId, ModelAndView modelView) {
        Gson gson = new Gson();

        ResponsibilityDto model;
        if (id != null) {
            model = responsibilityRepository.findDtoById(id);
            List<SelectList> lstSelectedAreas = areaService.getSelectedAreasByResponsibilityId(id);
            modelView.addObject("lstSelectedAreas", gson.toJson(lstSelectedAreas));
        } else {
            model = new ResponsibilityDto();
            model.setAuditId(auditId);
        }

        modelView.addObject("model", gson.toJson(model));
    }

    @Override
    public void save(ResponsibilityDto modelNew, ResponseMessage response) {

        Responsibility model = businessValidation(modelNew, null, response);

        if (response.isHasError())
            return;

        doSave(model);
    }

    public void doObsolete(Long id, ResponseMessage response) {

        Responsibility model = responsibilityRepository.findOne(id);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("La promoci&oacute;n ya fue eliminada o no existe en el sistema.");
            response.setTitle("Eliminar promoci&oacute;n");
            return;
        }

        if (model.isAttended() == true) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar una promoci&oacute;n que ya ha sido atendida.");
            response.setTitle("Eliminar promoci&oacute;n");
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
                response.setMessage("No es posible eliminar una promoci&oacute;n que ya tiene una prorroga.");
                response.setTitle("Eliminar promoci&oacute;n");
                return;
            }
        }

        model.setObsolete(true);
        model.setDelAudit(sharedUserService.getLoggedUserId());

        //TODO HACER OBSOLETOS LOS ARCHIVOS
        responsibilityRepository.saveAndFlush(model);
    }

    @Transactional
    private void doSave(Responsibility model) {
        responsibilityRepository.saveAndFlush(model);
    }

    private Responsibility businessValidation(ResponsibilityDto responsibilityDto, AttentionDto attentionDto, ResponseMessage responseMessage) {
        Responsibility responsibility = null;

        if (responsibilityDto != null) {
            Long id = responsibilityDto.getId();

            if (id != null) {
                responsibility = responsibilityRepository.findByIdAndIsObsolete(id, false);
                if (responsibility == null) {
                    responseMessage.setHasError(true);
                    responseMessage.setMessage("La promoci&oacute;n ya fue eliminado o no existe en el sistema.");
                    return null;
                } else if (responsibility.isAttended() == true) {
                    responseMessage.setHasError(true);
                    responseMessage.setMessage("No es posible modificar una promoci&oacute;n que ya ha sido atendida.");
                    return null;
                }

                responsibility.setUpdAudit(sharedUserService.getLoggedUserId());

            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                responsibility = new Responsibility();
                responsibility.setCreateDate(Calendar.getInstance());
                responsibility.setInsAudit(sharedUserService.getLoggedUserId());
                try {
                    Calendar init = Calendar.getInstance();
                    Calendar end = Calendar.getInstance();
                    init.setTime(sdf.parse(responsibilityDto.getInitDate()));
                    end.setTime(sdf.parse(responsibilityDto.getEndDate()));
                    responsibility.setInitDate(init);
                    responsibility.setEndDate(end);
                } catch (Exception e) {
                    return null;
                }

                Extension e = new Extension();
                e.setInsAudit(sharedUserService.getLoggedUserId());
                e.setInitial(true);
                e.setCreateDate(Calendar.getInstance());
                e.setComment("Fecha de fin inicial.");
                e.setEndDate(responsibility.getEndDate());
                List<Extension> lstExtension =  new ArrayList<>();
                lstExtension.add(e);
                responsibility.setLstExtension(lstExtension);
                responsibility.setInsAudit(sharedUserService.getLoggedUserId());

            }

            List<SelectList> lstSelectedAreas = new Gson().fromJson(responsibilityDto.getLstSelectedAreas(), new TypeToken<List<SelectList>>() {
            }.getType());

            if (responsibility.getLstAreas() != null) {
                responsibility.setLstAreas(null);
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

            responsibility.merge(responsibilityDto, null, null);
            responsibility.setLstAreas(lstNewSelectedAreas);
            Audit a = auditRepository.findOne(responsibilityDto.getAuditId());
            responsibility.setAudit(a);
        }

        if (attentionDto != null) {
            responsibility = responsibilityRepository.findOne(attentionDto.getId());
            responsibility.merge(null, attentionDto, userRepository.findOne(sharedUserService.getLoggedUserId()));
        }

        return responsibility;
    }

    @Override
    public void upsertViewDocs(Long responsibilityId, ModelAndView modelAndView) {
        ResponsibilityDto model = responsibilityRepository.findDtoById(responsibilityId);
        model.setType(Constants.UploadFile.RESPONSIBILITY);
        model.setDescription("");
        Gson gson = new Gson();
        String sModel = gson.toJson(model);
        modelAndView.addObject("model", sModel);
    }

    @Override
    @Transactional
    public void doDeleteUpFile(Long responsibilityId, Long upFileId, ResponseMessage response) {
        Responsibility model = responsibilityRepository.findByIdAndIsObsolete(responsibilityId, false);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("La promoci&oacute;n ya fue eliminada o no existe en el sistema.");
            response.setTitle("Eliminar documento");
            return;
        }

        if (model.isAttended() == true) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar el archivo debido a que la promoci&oacute;n ya fue atendido");
            response.setTitle("Eliminar documento");
            return;
        }

        List<UploadFileGeneric> lstFiles = model.getLstEvidences();

        for (int i = lstFiles.size() - 1; i >= 0; i--) {
            if (lstFiles.get(i).getId().equals(upFileId))
                lstFiles.remove(i);
        }

        responsibilityRepository.saveAndFlush(model);
    }

    @Override
    public void showAttention(Long id, ModelAndView modelAndView) {
        Gson gson = new Gson();
        AttentionDto model = responsibilityRepository.findAttentionInfoById(id);
        String a = gson.toJson(model);
        modelAndView.addObject("model", a);
    }

    @Override
    public void doAttention(AttentionDto attentionDto, ResponseMessage response) {

        Responsibility model = attentionValidation(attentionDto, response);

        if (response.isHasError())
            return;

        doSave(model);
    }

    private Responsibility attentionValidation(AttentionDto attentionDto, ResponseMessage response) {

        Responsibility model;

        if (attentionDto.getId() == null) {
            response.setHasError(true);
            response.setMessage("La promoci&oacute;n no existe o ya fue eliminada.");
            return null;
        } else {

            model = responsibilityRepository.findByIdAndIsObsolete(attentionDto.getId(), false);
            if (model == null) {
                response.setHasError(true);
                response.setMessage("La promoci&oacute;n no existe o ya fue eliminada.");
                return null;
            }

            model.setAttentionDate(Calendar.getInstance());
            model.setAttentionComment(attentionDto.getAttentionComment());
            model.setAttended(true);
            model.setAttentionUser(userRepository.findOne(sharedUserService.getLoggedUserId()));
        }

        return model;
    }

    @Override
    public boolean findByNumber(ResponsibilityDto responsibilityDto, ResponseMessage responseMessage) {
        if (responsibilityDto.getId() != null && responsibilityRepository.findByNumberWithId(responsibilityDto.getNumber(), responsibilityDto.getId()) != null) {
            responseMessage.setHasError(true);
            responseMessage.setMessage("Ya existe una promoci&oacute;n con el numeral indicado. Por favor revise la informaci&oacute;n e intente de nuevo.");
            return true;
        }

        if (responsibilityDto.getId() == null && responsibilityRepository.findByNumberAndIsObsolete(responsibilityDto.getNumber(), false) != null) {
            responseMessage.setHasError(true);
            responseMessage.setMessage("Ya existe una promoci&oacute;n con el numeral indicado. Por favor revise la informaci&oacute;n e intente de nuevo.");
            return true;
        }

        return false;
    }

    @Override
    public void extension(Long responsibilityId, ModelAndView modelAndView) {
        ResponsibilityDto model = responsibilityRepository.findDtoById(responsibilityId);
        model.setType(Constants.UploadFile.EXTENSION_RESPONSIBILITY);
        Gson gson = new Gson();
        String sModel = gson.toJson(model);
        modelAndView.addObject("model", sModel);
    }

    @Override
    @Transactional
    public void doDeleteExtension(Long responsibilityId, Long extensionId, ResponseMessage response) {
        Responsibility model = responsibilityRepository.findByIdAndIsObsolete(responsibilityId, false);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("La promoción ya fue eliminada o no existe en el sistema.");
            response.setTitle("Eliminar prorroga");
            return;
        }

        if (model.isAttended() == true) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar la prorroga debido a que la promoción ya fue atendida");
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

        Long lastSecondId = responsibilityRepository.findSecondLastExtensionIdByResponsibilityId(responsibilityId, extensionId);

        if (lastSecondId == null || lastSecondId == 0) {
            response.setHasError(true);
            response.setMessage("No es posible recuperar la fecha de fin anterior.");
            response.setTitle("Eliminar prorroga");
            return;
        }

        Long lastId = responsibilityRepository.findLastExtensionIdByResponsibilityId(responsibilityId);

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
        responsibilityRepository.saveAndFlush(model);
        uploadFileGenericRepository.delete(lastExtensionFileId);
    }
}
