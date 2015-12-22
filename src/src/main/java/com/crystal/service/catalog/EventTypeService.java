package com.crystal.service.catalog;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.catalog.dto.EventTypeDto;
import org.springframework.web.servlet.ModelAndView;

public interface EventTypeService {
    void upsert(Long id, ModelAndView modelView);
    void save(EventTypeDto modelNew, ResponseMessage response);
    void doObsolete(Long id, ResponseMessage response);
}
