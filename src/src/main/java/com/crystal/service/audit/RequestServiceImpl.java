package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.Letter;
import com.crystal.model.entities.audit.Request;
import com.crystal.model.entities.audit.dto.RequestDto;
import com.crystal.model.entities.catalog.Area;
import com.crystal.model.shared.Constants;
import com.crystal.model.shared.SelectList;
import com.crystal.repository.catalog.LetterRepository;
import com.crystal.repository.catalog.RequestRepository;
import com.crystal.service.account.SharedUserService;
import com.crystal.service.catalog.AreaService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

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
    public void save(RequestDto requestDto, ResponseMessage response) {
        requestRepository.saveAndFlush(mergeRequest(requestDto));
    }

    private Request mergeRequest(RequestDto requestDto) {
        Request request;

        Long id = requestDto.getId();
        if (id != null)
            request = requestRepository.findOne(id);
        else {
            request = new Request();
            request.setCreateDate(Calendar.getInstance());
        }

        request.setNumber(requestDto.getNumber());
        request.setDescription(requestDto.getDescription());
        request.setLimitTimeDays(requestDto.getLimitTimeDays());

        List<SelectList> lstSelectedAreas = new Gson().fromJson(requestDto.getLstSelectedAreas(), new TypeToken<List<SelectList>>() {
        }.getType());

        if (request.getLstAreas() != null) {
            request.setLstAreas(null);
        }

        List<Area> lstNewSelectedAreas = null;
        for (SelectList item : lstSelectedAreas) {
            lstNewSelectedAreas = new ArrayList<>();
            Area a = new Area();
            a.setId(item.getId());
            lstNewSelectedAreas.add(a);
        }

        request.setLstAreas(lstNewSelectedAreas);
        Letter letter = letterRepository.findOne(requestDto.getLetterId());
        request.setLetter(letter);

        return request;
    }

    @Override
    public void doObsolete(Long id, ResponseMessage response) {
        Request model = requestRepository.findOne(id);

        if (model == null) {
            response.setHasError(true);
            response.setMessage("El requrimiento ya fue eliminado o no existe en el sistema");
            response.setTitle("Eliminar requerimiento");
            return;
        }

        model.setObsolete(true);
        requestRepository.saveAndFlush(model);
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
    public boolean findByNumber(RequestDto requestDto, ResponseMessage responseMessage) {

        if (requestDto.getId() != null && requestRepository.findByNumberWithId(requestDto.getNumber(), requestDto.getId()) != null) {
            responseMessage.setHasError(true);
            responseMessage.setMessage("Ya existe un requerimiento con el numeral indicado. Por favor revise la informaci&oacute;n e intente de nuevo.");
            return true;
        }

        if (requestDto.getId() == null && requestRepository.findByNumber(requestDto.getNumber()) != null) {
            responseMessage.setHasError(true);
            responseMessage.setMessage("Ya existe un requerimiento con el numeral indicado. Por favor revise la informaci&oacute;n e intente de nuevo.");
            return true;
        }

        return false;
    }

    @Override
    public void upsertViewDocs(Long requestId, ModelAndView modelAndView) {
        RequestDto model = new RequestDto();
        model.setId(requestId);
        model.setType(Constants.UploadFile.REQUEST);
        model.setNumber(requestRepository.findNumberById(requestId));
        Gson gson = new Gson();
        String sModel = gson.toJson(model);
        modelAndView.addObject("model", sModel);
    }
}
