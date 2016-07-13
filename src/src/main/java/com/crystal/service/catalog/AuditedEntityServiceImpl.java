package com.crystal.service.catalog;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.account.User;
import com.crystal.model.entities.catalog.AuditedEntity;
import com.crystal.model.entities.catalog.dto.AuditedEntityDto;
import com.crystal.model.shared.SelectList;
import com.crystal.repository.catalog.AuditedEntityRepository;
import com.crystal.repository.catalog.AuditedEntityTypeRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class AuditedEntityServiceImpl implements AuditedEntityService {

    @Autowired
    AuditedEntityTypeRepository auditedEntityTypeRepository;
    @Autowired
    AuditedEntityRepository auditedEntityRepository;

    public void upsert(Long id, ModelAndView modelAndView){

        AuditedEntityDto ae = null;
        Gson gson = new Gson();

        if(id!=null) {
            ae = auditedEntityRepository.findDtoById(id);
            modelAndView.addObject("model",gson.toJson(ae));
        }

        modelAndView.addObject("lstAuditedEntityTypes",gson.toJson(auditedEntityTypeRepository.findNoObsolete()));
    }

    @Transactional
    public ResponseMessage doObsolete(Long id, ResponseMessage responseMessage) {

        List<User> lstLinkedUsers = auditedEntityRepository.findUsersByAuditedEntityId(id, new PageRequest(0,1));

        if(lstLinkedUsers!=null &&lstLinkedUsers.size()>0){
            responseMessage.setHasError(true);
            responseMessage.setMessage("El ente fiscalizado tiene usuarios asociados, no es posible eliminarlo.");
            return responseMessage;
        }

        responseMessage.setHasError(false);
        responseMessage.setMessage("Se ha eliminado la información correctamente.");
        AuditedEntity ae  = auditedEntityRepository.findOne(id);
        ae.setObsolete(true);
        auditedEntityRepository.save(ae);
        return responseMessage;
    }

    @Transactional
    public ResponseMessage save(AuditedEntityDto auditedEntityDto, ResponseMessage responseMessage) {
        responseMessage.setHasError(false);
        responseMessage.setMessage("Se ha guardado la información correctamente.");
        auditedEntityRepository.save(mergeAuditedEntity(auditedEntityDto));
        return responseMessage;
    }

    private AuditedEntity mergeAuditedEntity(AuditedEntityDto auditedEntityDto) {

        AuditedEntity ae = new AuditedEntity();
        if (auditedEntityDto.getId() != null) {
            ae = auditedEntityRepository.findOne(auditedEntityDto.getId());
        }

        ae.setName(auditedEntityDto.getName());
        ae.setResponsible(auditedEntityDto.getResponsible());
        ae.setPhone(auditedEntityDto.getPhone());
        ae.setEmail(auditedEntityDto.getEmail());
        ae.setAuditedEntityType(auditedEntityTypeRepository.findOne(auditedEntityDto.getAuditedEntityTypeId()));

        return ae;
    }

    public List<SelectList> getPossibleAssistant(String assistantStr) {
        assistantStr = "%" + assistantStr + "%";
        return auditedEntityRepository.findAssistantsByStr(assistantStr, new PageRequest(0, 10));
    }
}


