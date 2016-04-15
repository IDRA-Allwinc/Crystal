package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.dto.AttentionDto;
import com.crystal.model.entities.audit.dto.ResponsibilityDto;
import org.springframework.web.servlet.ModelAndView;

public interface ResponsibilityService {
    void upsert(Long id, Long auditId, ModelAndView modelView);

    void save(ResponsibilityDto modelNew, ResponseMessage response);

    void doObsolete(Long id, ResponseMessage response);

    void upsertViewDocs(Long responsibilityId, ModelAndView modelAndView);

    void showAttention(Long responsibilityId, ModelAndView modelAndView);

    void doAttention(AttentionDto attentionDto, ResponseMessage response);

    void doDeleteUpFile(Long responsibilityId, Long upFileId, ResponseMessage response);

    boolean findByNumber(ResponsibilityDto responsibilityDto, ResponseMessage responseMessage);

    void extension(Long responsibilityId, ModelAndView modelAndView);

    void doDeleteExtension(Long responsibilityId, Long extensionId, ResponseMessage response);

    ResponseMessage refreshExtensionComment(Long responsibilityId, ResponseMessage responseMessage);
}
