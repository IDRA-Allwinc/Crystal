package com.crystal.service.catalog;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.catalog.dto.AreaDto;
import org.springframework.web.servlet.ModelAndView;

public interface AreaService {
    ResponseMessage save(AreaDto areaDto, ResponseMessage responseMessage);
    void upsert(Long id, ModelAndView modelAndView);
    ResponseMessage doObsolete(Long id, ResponseMessage responseMessage);
}
