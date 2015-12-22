package com.crystal.service.catalog;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.catalog.dto.MeetingTypeDto;
import org.springframework.web.servlet.ModelAndView;

public interface MeetingTypeService {
    void upsert(Long id, ModelAndView modelView);
    void save(MeetingTypeDto modelNew, ResponseMessage response);
    void doObsolete(Long id, ResponseMessage response);
}
