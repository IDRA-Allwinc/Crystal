package com.crystal.service.audit;

import com.crystal.infrastructure.model.ResponseMessage;
import com.crystal.model.entities.audit.LetterDto;
import com.crystal.model.entities.audit.dto.AttentionDto;
import org.springframework.web.servlet.ModelAndView;

public interface LetterService {
    void upsert(Long id, ModelAndView modelView);

    void upsertAudit(Long auditId, Long id, ModelAndView modelView);

    void save(LetterDto modelNew, ResponseMessage response, Long userId, Long roleId);

    void doObsolete(Long id, Long userId, ResponseMessage response);

    Long findFileIdByLetterId(Long id);

    void upsertViewDocs(Long letterId, ModelAndView modelAndView);

    void showAttention(Long letterId, ModelAndView modelAndView);

    void doAttention(AttentionDto attentionDto, ResponseMessage response);

    public void doDeleteUpFile(Long letterId, Long upFileId, ResponseMessage response);
}
