package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.dto.EventDto;
import org.springframework.web.servlet.ModelAndView;

public interface EventService {
    public void upsert(Long id, Long auditId, ModelAndView modelView);

    void save(EventDto modelNew, ResponseMessage response);

    void doObsolete(Long id, ResponseMessage response);

    void upsertViewDocs(Long eventId, ModelAndView modelAndView);

    public void doDeleteUpFile(Long eventId, Long upFileId, ResponseMessage response);
}
