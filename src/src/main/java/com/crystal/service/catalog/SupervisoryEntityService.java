package com.crystal.service.catalog;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.catalog.dto.SupervisoryEntityDto;
import org.springframework.web.servlet.ModelAndView;

public interface SupervisoryEntityService {
    ResponseMessage save(SupervisoryEntityDto supervisoryEntityDto, ResponseMessage responseMessage);
    void upsert(Long id, ModelAndView modelAndView);
    ResponseMessage doObsolete(Long id, ResponseMessage responseMessage);
}
