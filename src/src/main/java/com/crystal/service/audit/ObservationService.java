package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.Observation;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.ObservationDto;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;

public interface ObservationService {

    void upsert(Long id, Long auditId, ModelAndView modelView);

    void save(ObservationDto modelNew, ResponseMessage response);

    void doObsolete(Long id, ResponseMessage response);

    void upsertViewDocs(Long observationId, ModelAndView modelAndView);

    void showAttention(Long observationId, ModelAndView modelAndView);

    void doAttention(AttentionDto attentionDto, ResponseMessage response);

    void doDeleteUpFile(Long observationId, Long upFileId, ResponseMessage response);

    boolean findByNumber(ObservationDto observationDto, ResponseMessage response);

    void extension(Long observationId, ModelAndView modelAndView);

    void doDeleteExtension(Long observationId, Long extensionId, ResponseMessage response);

    void doReplication(ObservationDto observationDto, AttentionDto attentionDto, ResponseMessage response) throws ParseException;

    void showReplication(Long id, ModelAndView modelAndView);

    ResponseMessage refreshExtensionObservation(Long observationId, ResponseMessage responseMessage);

    Observation businessValidation(ObservationDto observationDto, AttentionDto attentionDto, ResponseMessage responseMessage);

}
