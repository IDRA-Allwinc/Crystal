package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.dto.EventDto;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;

public interface EventService {
    void upsert(Long id, Long auditId, ModelAndView modelView);

    void save(EventDto modelNew, ResponseMessage response) throws ParseException;

    void doObsolete(Long id, ResponseMessage response);

    void upsertViewDocs(Long eventId, ModelAndView modelAndView);

    void doDeleteUpFile(Long eventId, Long upFileId, ResponseMessage response);
}
