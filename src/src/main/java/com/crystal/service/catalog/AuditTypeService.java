package com.crystal.service.catalog;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.catalog.dto.AuditTypeDto;
import org.springframework.web.servlet.ModelAndView;

public interface AuditTypeService {
    ResponseMessage save(AuditTypeDto supervisoryEntityDto, ResponseMessage responseMessage);
    void upsert(Long id, ModelAndView modelAndView);
    ResponseMessage doObsolete(Long id, ResponseMessage responseMessage);
}
