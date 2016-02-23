package com.crystal.service.audit;


import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.RecommendationDto;
import org.springframework.web.servlet.ModelAndView;

public interface RecommendationService {

    void upsert(Long id, Long auditId, ModelAndView modelView);

    void save(RecommendationDto modelNew, ResponseMessage response);

    void doObsolete(Long id, ResponseMessage response);

    void upsertViewDocs(Long recommendationId, ModelAndView modelAndView);

    void showAttention(Long recommendationId, ModelAndView modelAndView);

    void doAttention(AttentionDto attentionDto, ResponseMessage response);

    boolean findByNumber(RecommendationDto recommendationDto, ResponseMessage responseMessage);

    void doDeleteUpFile(Long recommendationId, Long upFileId, ResponseMessage response);

}
