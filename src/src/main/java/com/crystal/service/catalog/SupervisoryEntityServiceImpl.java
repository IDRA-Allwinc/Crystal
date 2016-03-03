package com.crystal.service.catalog;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.catalog.SupervisoryEntity;
import com.crystal.model.entities.catalog.dto.SupervisoryEntityDto;
import com.crystal.model.shared.SelectList;
import com.crystal.repository.catalog.SupervisoryEntityRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class SupervisoryEntityServiceImpl implements SupervisoryEntityService {

    @Autowired
    SupervisoryEntityRepository supervisoryEntityRepository;

    public void upsert(Long id, ModelAndView modelAndView){

        SupervisoryEntityDto se = null;
        Gson gson = new Gson();

        if(id!=null) {
            se = supervisoryEntityRepository.findDtoById(id);
            modelAndView.addObject("model",gson.toJson(se));
        }
    }

    @Transactional
    public ResponseMessage doObsolete(Long id, ResponseMessage responseMessage) {
        responseMessage.setHasError(false);
        responseMessage.setMessage("Se ha eliminado la información correctamente.");
        SupervisoryEntity se  = supervisoryEntityRepository.findOne(id);
        se.setObsolete(true);
        supervisoryEntityRepository.save(se);
        return responseMessage;
    }

    @Transactional
    public ResponseMessage save(SupervisoryEntityDto supervisoryEntityDto, ResponseMessage responseMessage) {
        responseMessage.setHasError(false);
        responseMessage.setMessage("Se ha guardado la información correctamente.");
        supervisoryEntityRepository.save(mergeSupervisoryEntity(supervisoryEntityDto));
        return responseMessage;
    }

    private SupervisoryEntity mergeSupervisoryEntity(SupervisoryEntityDto supervisoryEntityDto) {

        SupervisoryEntity se = new SupervisoryEntity();
        if (supervisoryEntityDto.getId() != null) {
            se = supervisoryEntityRepository.findOne(supervisoryEntityDto.getId());
        }

        se.setName(supervisoryEntityDto.getName());
        se.setResponsible(supervisoryEntityDto.getResponsible());
        se.setPhone(supervisoryEntityDto.getPhone());
        se.setEmail(supervisoryEntityDto.getEmail());
        se.setBelongsTo(supervisoryEntityDto.getBelongsTo());

        return se;
    }

    public List<SelectList> getPossibleAssistant(String assistantStr) {
        assistantStr = "%" + assistantStr + "%";
        return supervisoryEntityRepository.findAssistantsByStr(assistantStr, new PageRequest(0, 10));
    }
}


