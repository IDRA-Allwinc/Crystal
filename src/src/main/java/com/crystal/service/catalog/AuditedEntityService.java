package com.crystal.service.catalog;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.catalog.dto.AuditedEntityDto;
import com.crystal.model.shared.SelectList;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Developer on 18/12/2015.
 */
public interface AuditedEntityService {
    ResponseMessage save(AuditedEntityDto auditedEntityDto, ResponseMessage responseMessage);

    void upsert(Long id, ModelAndView modelAndView);

    ResponseMessage doObsolete(Long id, ResponseMessage responseMessage);

    List<SelectList> getPossibleAssistant(String assistantStr);
}
