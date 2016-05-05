package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.Extension;
import com.crystal.model.entities.audit.Letter;
import com.crystal.model.entities.audit.Request;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.RequestDto;
import com.crystal.model.entities.catalog.Area;
import com.crystal.model.shared.Constants;
import com.crystal.model.shared.SelectList;
import com.crystal.model.shared.UploadFileGeneric;
import com.crystal.repository.account.UserRepository;
import com.crystal.repository.catalog.ExtensionRepository;
import com.crystal.repository.catalog.LetterRepository;
import com.crystal.repository.catalog.RequestRepository;
import com.crystal.repository.shared.UploadFileGenericRepository;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.catalog.AreaService;
import com.crystal.service.catalog.AuditedEntityService;
import com.crystal.service.catalog.SupervisoryEntityService;
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
public class RequestServiceImpl implements RequestService {

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    AreaService areaService;
    @Autowired
    LetterRepository letterRepository;
    @Autowired
    ExtensionRepository extensionRepository;

    @Autowired
    SharedUserService sharedUserService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuditedEntityService auditedEntityService;
    @Autowired
    SupervisoryEntityService supervisoryEntityService;

    @Autowired
    UploadFileGenericRepository uploadFileGenericRepository;

    @Override
    public void upsert(Long letterId, Long id, ModelAndView modelView) {

        Gson gson = new Gson();

        RequestDto model;
        if (id != null) {
            model = requestRepository.findDtoById(id);
            List<SelectList> lstSelectedAreas = areaService.getSelectedAreasByRequestId(id);
            modelView.addObject("lstSelectedAreas", gson.toJson(lstSelectedAreas));
        } else {
            model = new RequestDto();
            model.setLetterId(letterId);
        }

        modelView.addObject("model", gson.toJson(model));
    }

    @Override
    @Transactional
    public void doSave(Request request) {
        requestRepository.save(request);
    }

    @Override
    public void save(RequestDto requestDto, AttentionDto attentionDto, ResponseMessage responseMessage) {
        Request request = null;

        if (requestDto != null)
            request = businessValidation(requestDto, null, responseMessage);

        if (attentionDto != null)
            request = businessValidation(null, attentionDto, responseMessage);

        doSave(request);
    }


    @Override
    public void doObsolete(Long id, ResponseMessage response) {
        Request model = requestRepository.findOne(id);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("El requerimiento ya fue eliminado o no existe en el sistema.");
            response.setTitle("Eliminar requerimiento");
            return;
        } else if (model.isAttended() == true) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar un requerimiento que ya ha sido atendido.");
            response.setTitle("Eliminar requerimiento");
            return;
        } else if (model.getLstExtension() != null) {

            boolean hasExtension = false;

            for (Extension e : model.getLstExtension()) {
                if (e.isInitial() == false && e.isObsolete() == false)
                    hasExtension = true;
            }

            if (hasExtension) {
                response.setHasError(true);
                response.setMessage("No es posible eliminar un requerimiento que ya tiene una prórroga.");
                response.setTitle("Eliminar requerimiento");
                return;
            }
        }

        model.setObsolete(true);
        model.setDelAudit(sharedUserService.getLoggedUserId());
        requestRepository.saveAndFlush(model);
    }

    private Request businessValidation(RequestDto requestDto, AttentionDto attentionDto, ResponseMessage responseMessage) {
        Request request = null;

        if (requestDto != null) {
            Long id = requestDto.getId();

            if (id != null) {
                request = requestRepository.findByIdAndIsObsolete(id, false);
                if (request == null) {
                    responseMessage.setHasError(true);
                    responseMessage.setMessage("El requerimiento ya fue eliminado o no existe en el sistema.");
                    return null;
                } else if (request.isAttended() == true) {
                    responseMessage.setHasError(true);
                    responseMessage.setMessage("No es posible modificar un requerimiento que ya ha sido atendido..");
                    return null;
                }

                request.setUpdAudit(sharedUserService.getLoggedUserId());

            } else {
                SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
                request = new Request();
                request.setCreateDate(Calendar.getInstance());
                try {
                    Calendar init = Calendar.getInstance();
                    Calendar end = Calendar.getInstance();
                    init.setTime(sdf.parse(requestDto.getInitDate()));
                    end.setTime(sdf.parse(requestDto.getEndDate()));
                    request.setInitDate(init);
                    request.setEndDate(end);
                } catch (Exception e) {
                    return null;
                }

                Extension e = new Extension();
                e.setInsAudit(sharedUserService.getLoggedUserId());
                e.setInitial(true);
                e.setCreateDate(Calendar.getInstance());
                e.setComment("Fecha de fin inicial.");
                e.setEndDate(request.getEndDate());
                List<Extension> lstExtension = new ArrayList<>();
                lstExtension.add(e);
                request.setLstExtension(lstExtension);
                request.setInsAudit(sharedUserService.getLoggedUserId());
            }

            List<SelectList> lstSelectedAreas = new Gson().fromJson(requestDto.getLstSelectedAreas(), new TypeToken<List<SelectList>>() {
            }.getType());

            if (request.getLstAreas() != null) {
                request.setLstAreas(null);
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

            request.merge(requestDto, null, null);
            request.setLstAreas(lstNewSelectedAreas);
            Letter letter = letterRepository.findOne(requestDto.getLetterId());
            request.setLetter(letter);
        }

        if (attentionDto != null)

        {
            request = requestRepository.findOne(attentionDto.getId());
            request.merge(null, attentionDto, userRepository.findOne(sharedUserService.getLoggedUserId()));
        }

        return request;
    }


    @Override
    public List<SelectList> findAreasByRole(SharedUserService sharedUserService, String areaStr) {
        List<SelectList> lstAreas = null;
        if (sharedUserService.loggedUserHasAuthority(Constants.AUTHORITY_DGPOP))
            lstAreas = areaService.getDGPOPAreas(areaStr);
        else if (sharedUserService.loggedUserHasAuthority(Constants.AUTHORITY_LINK))
            lstAreas = areaService.geAreasByAuditedEntityId(sharedUserService.getAuditedEntityIdByLoggedUserId(sharedUserService.getLoggedUserId()), areaStr);
        return lstAreas;
    }

    @Override
    public boolean findByNumberAndLetterId(RequestDto requestDto, ResponseMessage responseMessage) {

        Long existId = requestRepository.findByNumberWithIdAndLetterId(requestDto.getNumber(), requestDto.getLetterId());

        if (existId != null && requestDto.getId()!= null && !existId.equals(requestDto.getId())){
                responseMessage.setHasError(true);
                responseMessage.setMessage("Ya existe un requerimiento con el numeral indicado para este oficio. Por favor revise la informaci&oacute;n e intente de nuevo.");
                return true;
        }else if(existId != null && requestDto.getId() == null ){
            responseMessage.setHasError(true);
            responseMessage.setMessage("Ya existe un requerimiento con el numeral indicado para este oficio. Por favor revise la informaci&oacute;n e intente de nuevo.");
            return true;
        }

        return false;
    }

    @Override
    public void upsertViewDocs(Long requestId, ModelAndView modelAndView) {
        RequestDto model = requestRepository.findDtoAttById(requestId);
        model.setType(Constants.UploadFile.REQUEST);
        Gson gson = new Gson();
        String sModel = gson.toJson(model);
        modelAndView.addObject("model", sModel);
    }

    @Override
    public void attention(Long id, ModelAndView modelView) {
        Gson gson = new Gson();
        AttentionDto model = requestRepository.findAttentionInfoById(id);
        modelView.addObject("model", gson.toJson(model));
    }

    @Override
    @Transactional
    public void doAttention(AttentionDto attentionDto, ResponseMessage responseMessage) {
        Request model = requestRepository.findOne(attentionDto.getId());
        requestRepository.saveAndFlush(model);
        responseMessage.setHasError(false);
    }

    @Override
    public void extension(Long requestId, ModelAndView modelAndView) {
        RequestDto model = requestRepository.findDtoAttById(requestId);
        Long lastExtensionId = requestRepository.findLastExtensionIdByRequestId(requestId);
        if (lastExtensionId != null && lastExtensionId > 0)
            model.setHasExtension(true);
        model.setType(Constants.UploadFile.EXTENSION_REQUEST);
        Gson gson = new Gson();
        String sModel = gson.toJson(model);
        modelAndView.addObject("model", sModel);
    }

    @Override
    @Transactional
    public void doDeleteExtension(Long requestId, Long extensionId, ResponseMessage response) {
        Request model = requestRepository.findByIdAndIsObsolete(requestId, false);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("El requerimiento ya fue eliminado o no existe en el sistema.");
            response.setTitle("Eliminar prórroga");
            return;
        }

        if (model.isAttended() == true) {
            response.setHasError(true);
            response.setMessage("No es posible eliminar la prórroga debido a que el requerimiento ya fue atendido");
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

        Long lastSecondId = requestRepository.findSecondLastExtensionIdByRequestId(requestId, extensionId);

        if (lastSecondId == null || lastSecondId == 0) {
            response.setHasError(true);
            response.setMessage("No es posible recuperar la fecha de fin anterior.");
            response.setTitle("Eliminar prórroga");
            return;
        }

        Long lastId = requestRepository.findLastExtensionIdByRequestId(requestId);

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
        requestRepository.saveAndFlush(model);
        uploadFileGenericRepository.delete(lastExtensionFileId);
    }

    @Override
    @Transactional
    public void doDeleteUpFile(Long requestId, Long upFileId, ResponseMessage response) {
        Request model = requestRepository.findByIdAndIsObsolete(requestId, false);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("El requerimiento ya fue eliminado o no existe en el sistema.");
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

        requestRepository.saveAndFlush(model);
    }


    @Override
    public List<SelectList> findPossibleAssistants(String assistantStr) {

        List<SelectList> lstAssistants = auditedEntityService.getPossibleAssistant(assistantStr);

        lstAssistants.addAll(supervisoryEntityService.getPossibleAssistant(assistantStr));

        return lstAssistants;
    }

    //SE AGREGA PARA ACTUALZIAR EL ESTADO DEL ELEMENTO Y OCULTAR LOS CAMPOS PARA AGREGAR PRORROGA
    @Override
    public ResponseMessage refreshExtensionRequest(Long requestId, ResponseMessage responseMessage){
        Gson gson = new Gson();
        RequestDto model = requestRepository.findDtoById(requestId);
        Long lastExtensionId = requestRepository.findLastExtensionIdByRequestId(requestId);
        if (lastExtensionId != null && lastExtensionId > 0)
            model.setHasExtension(true);

        model.setType(Constants.UploadFile.EXTENSION_REQUEST);

        responseMessage.setHasError(false);
        responseMessage.setReturnData(gson.toJson(model));

        return responseMessage;
    }
}
