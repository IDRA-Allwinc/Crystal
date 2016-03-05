package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.ObservationDto;
import org.springframework.web.servlet.ModelAndView;

public interface ObservationService {

    void upsert(Long id, Long auditId, ModelAndView modelView);

    void save(ObservationDto modelNew, ResponseMessage response);

    void doObsolete(Long id, ResponseMessage response);

    void upsertViewDocs(Long observationId, ModelAndView modelAndView);

    void showAttention(Long observationId, ModelAndView modelAndView);

    void doAttention(AttentionDto attentionDto, ResponseMessage response);

    void doDeleteUpFile(Long observationId, Long upFileId, ResponseMessage response);

    boolean findByNumber(ObservationDto observationDto, ResponseMessage response);

    public void extension(Long observationId, ModelAndView modelAndView);

    public void doDeleteExtension(Long observationId, Long extensionId, ResponseMessage response);

    void showReplication(Long observationId, ModelAndView modelAndView);

    void doReplication(AttentionDto attentionDto, ResponseMessage response);
}
