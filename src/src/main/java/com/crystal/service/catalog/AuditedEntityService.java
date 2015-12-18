package com.crystal.service.catalog;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.catalog.dto.AuditedEntityDto;

/**
 * Created by Developer on 18/12/2015.
 */
public interface AuditedEntityService {
    ResponseMessage save(AuditedEntityDto auditedEntityDto);
}
