package com.crystal.service.catalog;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.catalog.AuditType;
import com.crystal.model.entities.catalog.dto.AuditTypeDto;
import com.crystal.repository.catalog.AuditTypeRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

@Service
public class AuditTypeServiceImpl implements AuditTypeService {

    @Autowired
    AuditTypeRepository auditTypeRepository;

    public void upsert(Long id, ModelAndView modelAndView){

        AuditTypeDto at = null;
        Gson gson = new Gson();

        if(id!=null) {
            at = auditTypeRepository.findDtoById(id);
            modelAndView.addObject("model",gson.toJson(at));
        }
    }

    @Transactional
    public ResponseMessage doObsolete(Long id, ResponseMessage responseMessage) {
        responseMessage.setHasError(false);
        responseMessage.setMessage("Se ha eliminado la información correctamente.");
        AuditType at  = auditTypeRepository.findOne(id);
        at.setObsolete(true);
        auditTypeRepository.save(at);
        return responseMessage;
    }

    @Transactional
    public ResponseMessage save(AuditTypeDto auditTypeDto, ResponseMessage responseMessage) {
        responseMessage.setHasError(false);
        responseMessage.setMessage("Se ha guardado la información correctamente.");
        auditTypeRepository.save(mergeAuditType(auditTypeDto));
        return responseMessage;
    }

    private AuditType mergeAuditType(AuditTypeDto auditTypeDto) {

        AuditType at = new AuditType();
        if (auditTypeDto.getId() != null) {
            at = auditTypeRepository.findOne(auditTypeDto.getId());
        }

        at.setName(auditTypeDto.getName());
        at.setDescription(auditTypeDto.getDescription());

        return at;
    }
}


