package com.crystal.service.catalog;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.catalog.AuditedEntity;
import com.crystal.model.entities.catalog.dto.AuditedEntityDto;
import com.crystal.repository.catalog.AuditedEntityRepository;
import com.crystal.repository.catalog.AuditedEntityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditedEntityServiceImpl implements AuditedEntityService {

    @Autowired
    AuditedEntityTypeRepository auditedEntityTypeRepository;
    @Autowired
    AuditedEntityRepository auditedEntityRepository;

    public ResponseMessage save(AuditedEntityDto auditedEntityDto) {

        ResponseMessage responseMessage = new ResponseMessage();

        if (auditedEntityDto.getId() != null) {
            responseMessage.setTitle("Editar entidad fiscalizada");
            responseMessage.setMessage("Se han actualizado los datos correctamente.");
            responseMessage.setHasError(false);
        }
        else {
            responseMessage.setTitle("Agrega entidad fiscalizada");
            responseMessage.setMessage("Se han guardado los datos correctamente.");
            responseMessage.setHasError(false);
        }

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
}


