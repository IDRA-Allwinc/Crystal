package com.crystal.service.audit;


import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.Recommendation;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.RecommendationDto;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;

public interface RecommendationService {

    void upsert(Long id, Long auditId, ModelAndView modelView);

    void save(RecommendationDto modelNew, ResponseMessage response) throws ParseException;

    void doObsolete(Long id, ResponseMessage response);

    void upsertViewDocs(Long recommendationId, ModelAndView modelAndView);

    void showAttention(Long recommendationId, ModelAndView modelAndView);

    void doAttention(AttentionDto attentionDto, ResponseMessage response);

    boolean findByNumber(RecommendationDto recommendationDto, ResponseMessage responseMessage);

    void doDeleteUpFile(Long recommendationId, Long upFileId, ResponseMessage response);

    void extension(Long recommendationId, ModelAndView modelAndView);

    void doDeleteExtension(Long recommendationId, Long extensionId, ResponseMessage response);

    void showReplication(Long recommendId, ModelAndView modelAndView);

    void doReplication(RecommendationDto recommendationDto, AttentionDto attentionDto, ResponseMessage response) throws ParseException;

    ResponseMessage refreshExtensionRecommendation(Long id, ResponseMessage responseMessage);

    Recommendation businessValidation(RecommendationDto recommendationDto, AttentionDto attentionDto, ResponseMessage responseMessage) throws ParseException;

}
